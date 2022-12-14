import java.io.*;
import java.net.Socket;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Client {

    static Logger logger = Logger.getLogger(Client.class.getName());

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
//        System.out.println("Формат повідомлення: 'кількість команд '{'команда'} \n" +
//                "наприклад: '3 W TurnRight S'" +
//                "  Список команд: \n" +
//                "    Who\n" +
//                "    W,A,S,D - команди для переміщення\n" +
//                "    TurnUp,TurnDown,TurnLeft,TurnRight - команди керування камерою\n" +
//                "    Exit");
        clientStuff();
    }

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
        }
    }
}
