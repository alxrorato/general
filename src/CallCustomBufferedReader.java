import util.CustomBufferedReader;

import java.io.*;

public class CallCustomBufferedReader {
    public static void main(String[] args) {
        try {
            File file = new File("D:\\dev\\tmp\\fileToRead.txt");
            FileReader fileReader = new FileReader(file);
            // Use the default maximum line length and maximum lines
            //util.CustomBufferedReader customReader = new util.CustomBufferedReader(fileReader);
            // Or specify custom values for maximum line length and maximum lines
            CustomBufferedReader customReader = new CustomBufferedReader(fileReader, 100, 50);

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
