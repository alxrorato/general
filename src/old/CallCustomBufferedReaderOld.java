package old;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CallCustomBufferedReaderOld {
    public static void main(String[] args) {
        try {
            File file = new File("D:\\dev\\tmp\\fileToRead.txt");
            FileReader fileReader = new FileReader(file);
            CustomBufferedReaderOld customReader = new CustomBufferedReaderOld(fileReader);

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
