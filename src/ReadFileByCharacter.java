import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReadFileByCharacter {

    public static void main(String[] args) {

        String pathName = "D:\\dev\\tmp\\";
        String fileName = "fileToRead.txt";

        try {
            final Path path = Paths.get(pathName);
            String line;
            int count = 0;

            final DirectoryStream<Path> stream = Files.newDirectoryStream(path, "fileToRead*.txt");

            for (final Path file : stream) {

                System.out.println("Reading file [" + file.getFileName() +"]");

                final BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8);

                int maxLineSize = 3000;
                StringBuilder strBuilder = new StringBuilder(maxLineSize);
                int caracter;

                while ((caracter = reader.read()) != -1) {
                    char c = (char) caracter;
                    if (c =='\n') {
                        line = strBuilder.toString();
                        strBuilder.setLength(0);

                        System.out.println("Line [" + count + 1 + "]: " + line );

                        count++;
                        continue;
                    }
                    // Avoiding Denial of Service vulnerability
                    if (strBuilder.length() >= maxLineSize) {
                        System.out.println("==> ATTENTION: Line size limit reached!!! Invalid file!!!");
                        strBuilder.setLength(0);
                        continue;
                    }
                    strBuilder.append(c);
                }

                reader.close();
            }

        } catch (final Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
