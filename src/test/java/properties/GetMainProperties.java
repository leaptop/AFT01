package properties;

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
public class GetMainProperties {
    public static String url = "";

    /**
     * Возвращает значение переменной среды по её имени
     *
     * @param environmentVariableName имя переменной среды
     * @return Возвращает значение переменной среды
     */
    public static String getPropertyFromEnvironmentVariable(String environmentVariableName) {
        return System.getenv(environmentVariableName);
    }

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
