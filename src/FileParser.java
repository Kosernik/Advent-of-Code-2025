import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileParser {

    public static List<String> parseFile(String fileName) {
        List<String> parsed = new ArrayList<>();
        String line;

        try (BufferedReader reader = new BufferedReader(new FileReader("src/%s.txt".formatted(fileName)))) {
            while ((line = reader.readLine()) != null) {
                parsed.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return parsed;
    }
}
