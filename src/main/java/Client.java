import java.io.*;
import java.net.Socket;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Client {

    Logger logger = Logger.getLogger(Client.class.getName());

    static {
        try {
            FileHandler fh = new FileHandler("log.log");
            fh.setFormatter(new SimpleFormatter());
            fh.setLevel(Level.FINE);
            logger.addHandler(fh);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.setLevel(Level.FINE);
    }

    public static void main(String[] args) throws InterruptedException {
        clientStuff();
        System.out.println("branch commit2");
    }
    // branch commit
    private static void clientStuff() throws InterruptedException {
        try (Socket clientSocket = new Socket("localhost", 4004);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                try {
                    System.out.println("Введіть команду:");
                    String word = reader.readLine();
                    Command c = new Command(word);
                    out.write(c.word + "\n");
                    out.flush();
                    String serverWord = in.readLine();
                    logger.log(Level.FINE, "command: " + c + "answer: " + serverWord);
                    System.out.println(serverWord);
                    if (serverWord != null && serverWord.contains("Завершення роботи"))
                        break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Невірна команда");
                }
            }
            System.out.println("Кліент був закрит...");
        } catch (IOException e) {
            System.out.println("Приєднання до сервера...");
            Thread.sleep(1000);
            clientStuff();
            System.out.println("коммит в мастере");
            //коммит в масетре
        }
    }
}
