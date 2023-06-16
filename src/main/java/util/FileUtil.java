package util;

import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {

    public static void writeToFile(String fileName, String content) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName);
        fileWriter.write(content);
        fileWriter.close();
    }
}
