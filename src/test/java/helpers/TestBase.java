package helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * Класс для реализации базового функционала для всех тестов, написанных с использованием Selenium.
 *
 * @author Алексеев Степан
 * @date 20.12.2022
 */
public class TestBase {
    public WebDriver chromedriver;

    /**
     * Инициализируем вебдрайвер, настраиваем его перед запуском каждого теста.
     */
    @BeforeMethod
    void initTests() {
        System.setProperty("webdriver.chrome.driver", System.getenv("CHROME_DRIVER"));
        ChromeOptions options = new ChromeOptions();
        chromedriver = new ChromeDriver(options);
        chromedriver.manage().window().maximize();
    }

    /**
     * Метод завершает работу вебдрайвера после выполнения каждого теста.
     *
     * Его необходимо отключить на время использования MyTestWatcher, т.к.
     * иначе нельзя получить доступ к вебдрайверу (он закрывается здесь до
     * запуска методов из MyTestWatcher).
     */
     @AfterMethod
    void finishTest() {
        if (chromedriver != null)
            chromedriver.quit();
    }
}
