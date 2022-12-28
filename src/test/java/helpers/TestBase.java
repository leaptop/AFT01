package helpers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Класс для реализации базового функционала для всех тестов, написанных с использованием Selenium.
 *
 * @author Алексеев Степан
 * @date 20.12.2022
 */
@ExtendWith(MyTestWatcher.class)
public class TestBase {
    public WebDriver chromedriver;

    /**
     * Инициализируем вебдрайвер, настраиваем его перед запуском каждого теста.
     */
    @BeforeEach
    void initTests() {
        System.setProperty("webdriver.chrome.driver", System.getenv("CHROME_DRIVER"));
        ChromeOptions options = new ChromeOptions();
        chromedriver = new ChromeDriver(options);
        chromedriver.manage().window().maximize();
    }

    /**
     * Метод завершает работу вебдрайвера после выполнения каждого теста.
     */
    @AfterEach
    void finishTest() {
        if (chromedriver != null)
            chromedriver.quit();
    }
}
