package old;

import java.io.*;

class CustomBufferedReader02 extends BufferedReader {
    private static final int MAX_LINE_LENGTH = 100;
    private char[] buffer;
    private int bufferPos;
    private int bufferEnd;

    public CustomBufferedReader02(Reader in) {
        super(in);
        buffer = new char[MAX_LINE_LENGTH];
        bufferPos = 0;
        bufferEnd = 0;
    }

    @Override
    public String readLine() throws IOException {
        StringBuilder line = new StringBuilder();
        int charCount = 0;
        boolean endOfLine = false;

        while (charCount < MAX_LINE_LENGTH && !endOfLine) {
            if (bufferPos >= bufferEnd) {
                bufferEnd = super.read(buffer, 0, MAX_LINE_LENGTH);
                bufferPos = 0;

                if (bufferEnd == -1) {
                    break;
                }
            }

            char c = buffer[bufferPos++];
            if (c == '\n') {
                endOfLine = true;
            } else if (c != '\r') {
                line.append(c);
                charCount++;
            }
        }

        // Skip remaining characters in the current line if they exceed MAX_LINE_LENGTH
        while (!endOfLine && bufferEnd != -1) {
            if (bufferPos >= bufferEnd) {
                bufferEnd = super.read(buffer, 0, MAX_LINE_LENGTH);
                bufferPos = 0;

                if (bufferEnd == -1) {
                    break;
                }
            }

            char c = buffer[bufferPos++];
            if (c == '\n') {
                endOfLine = true;
            }
        }

        if (charCount == 0 && bufferEnd == -1) {
            return null;
        }

        return line.toString();
    }

    public static void main(String[] args) {
        try {
            File file = new File("D:\\dev\\tmp\\fileToRead.txt");
            FileReader fileReader = new FileReader(file);
            CustomBufferedReader02 customReader = new CustomBufferedReader02(fileReader);

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
