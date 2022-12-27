package autotests;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Класс для загрузки информации из файлов свойств
 *
 * @author Алексеев Степан
 * @date 27.12.2022
 */
public class GetProperties {
    public static String url = "";

    /**
     * Загружает информацию из файла свойств.
     */
    public static void loadMainProperties() {
        File file = new File("src/test/resources/properties/main.properties");
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(file));
        } catch (IOException eo) {

        }
        url = properties.getProperty("url");
    }

    /**
     * Выводит в консоль содержимое файла свойств
     */
    public void loadAndPrintProperties() {
        File file = new File("src/test/resources/properties/credentials.properties");
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(file));
        } catch (IOException eo) {

        }
        for (String key : properties.stringPropertyNames()) {
            System.out.println(properties.get(key));
        }
    }


}
