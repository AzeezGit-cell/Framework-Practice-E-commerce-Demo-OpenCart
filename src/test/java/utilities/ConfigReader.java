package utilities;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties p;

    static {

        try {

            p = new Properties();

            FileReader fr =
                    new FileReader("./src/test/resources/config.properties");

            p.load(fr);

            fr.close();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {

        return p.getProperty(key);
    }
}