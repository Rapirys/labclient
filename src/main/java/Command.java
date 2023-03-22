import java.util.HashMap;
import java.util.Map;

public class Command {
    long time;
    Map<String, Byte> map = new HashMap<String, Byte>();
    {
        map.put("W", (byte) 87);
        map.put("A", (byte) 65);
        map.put("S", (byte) 83);
        map.put("D", (byte) 68);
        map.put("TurnUp", (byte) 38);
        map.put("TurnDown", (byte) 40);
        map.put("TurnLeft", (byte) 37);
        map.put("TurnRight", (byte) 39);
        map.put("Who", (byte) 1);
        map.put("Exit", (byte) 0);
        map.put("AddPolygon", (byte) 100);
        // main branch cahnges

    }
    private byte length;
//    private List<Byte> commands;
    String word;
    Command (String word) {
        time = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        String[] headerAndBody = word.split("\\|");
        parseHeader(headerAndBody[0], sb);
        sb.append("|");
        if (headerAndBody.length == 2)
            parseBody(headerAndBody[1], sb);
        this.word = sb.toString();
    }

    private void parseHeader(String word, StringBuilder sb) {
        String[] splitedWord = word.split(" +");
        length = Byte.parseByte(splitedWord[0]);
        sb.append(length).append(" ");
        for (int i = 1; i<splitedWord.length; i++) {
            if (map.containsKey(splitedWord[i])) {
                sb.append(map.get(splitedWord[i])).append(" ");
            }
            else throw new IllegalArgumentException(splitedWord[i] +" в позиції " + i);
        }
    }

    private void parseBody(String word, StringBuilder sb) {
        String[] splitedWord = word.split(" +");
        sb.append(" ");
        for (int i = 1; i<splitedWord.length; i++) {
            sb.append(splitedWord[i]).append(" ");
        }
    }

    @Override
    public String toString() {
        return "Command{" +
                "duration=" + (System.currentTimeMillis() - time) +
                ", length=" + length +
                ", word='" + word + '\'' +
                '}';
    }
}
