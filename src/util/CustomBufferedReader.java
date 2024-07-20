package util;

import java.io.*;

public class CustomBufferedReader extends BufferedReader {
    private static final int DEFAULT_MAX_LINE_LENGTH = 100;
    private static final int DEFAULT_MAX_LINES = 1000000;
    private char[] buffer;
    private int bufferPos;
    private int bufferEnd;
    private int lineCount;
    private int maxLineLength;
    private int maxLines;

    public CustomBufferedReader(Reader in) {
        this(in, DEFAULT_MAX_LINE_LENGTH, DEFAULT_MAX_LINES);
    }

    public CustomBufferedReader(Reader in, int maxLineLength, int maxLines) {
        super(in);
        this.maxLineLength = maxLineLength;
        this.maxLines = maxLines;
        buffer = new char[maxLineLength];
        bufferPos = 0;
        bufferEnd = 0;
        lineCount = 0;
    }

    @Override
    public String readLine() throws IOException {
        if (lineCount >= maxLines) {
            throw new IOException("File contains too many lines");
        }

        StringBuilder line = new StringBuilder();
        int charCount = 0;
        boolean endOfLine = false;

        while (charCount < maxLineLength && !endOfLine) {
            if (bufferPos >= bufferEnd) {
                bufferEnd = super.read(buffer, 0, maxLineLength);
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

        // Skip remaining characters in the current line if they exceed maxLineLength
        while (!endOfLine && bufferEnd != -1) {
            if (bufferPos >= bufferEnd) {
                bufferEnd = super.read(buffer, 0, maxLineLength);
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
