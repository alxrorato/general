import java.io.*;

public class CustomBufferedReader extends BufferedReader {
    private static final int MAX_LINE_LENGTH = 100;

    public CustomBufferedReader(Reader in) {
        super(in);
    }

    @Override
    public String readLine() throws IOException {
        StringBuilder line = new StringBuilder(100);
        String lineStr;
        int charRead;
        int charCount = 0;
        while ((charRead = super.read()) != -1) {
            char c = (char) charRead;
            if (c == '\n') {
                break;
            }
            if (charCount < MAX_LINE_LENGTH) {
                line.append(c);
                charCount++;
            } else {
                System.out.println("Size maximun length reached!!!");
                lineStr = line.toString();
                line.setLength(0);
                break;
            }
        }
        if (charRead == -1 && line.length() == 0 && charCount < MAX_LINE_LENGTH) {
            return null;
        }
        return line.toString();
    }

    public static void main(String[] args) {
        try {
            File file = new File("D:\\dev\\tmp\\fileToRead.txt");
            FileReader fileReader = new FileReader(file);
            CustomBufferedReader customReader = new CustomBufferedReader(fileReader);

            String line;
            while ((line = customReader.readLine()) != null) {
                System.out.println(line);
            }

            customReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
