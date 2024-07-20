package old;

import java.io.*;

public class CustomBufferedReaderOld extends BufferedReader {
    private static final int MAX_LINE_LENGTH = 100;
    private static final int MAX_LINES = 100000;
    private char[] buffer;
    private int bufferPos;
    private int bufferEnd;
    private int lineCount;

    public CustomBufferedReaderOld(Reader in) {
        super(in);
        buffer = new char[MAX_LINE_LENGTH];
        bufferPos = 0;
        bufferEnd = 0;
        lineCount = 0;
    }

    @Override
    public String readLine() throws IOException {
        if (lineCount >= MAX_LINES) {
            throw new IOException("File contains too many lines");
        }

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

        lineCount++;
        return line.toString();
    }
}
