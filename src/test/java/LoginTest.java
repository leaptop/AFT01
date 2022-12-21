import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginTest {
    /**
     * В данном тесте нужно дождаться загрузки страницы и потом максимизировать её. За факт загрузки страницы
     * я решил принять видимость кнопки с надписью "Войти". Понимаю, что использование искспаса в тесте не допустимо
     * , но здесь руководствуюсь правилом KISS.
     */
    @Test
    void initWebDriver() {
        System.setProperty("webdriver.chrome.driver", System.getenv("CHROME_DRIVER"));
        ChromeOptions options = new ChromeOptions();
        Dimension newDimension = new Dimension(200, 100);

        options.addArguments("--window-size=500,500");
        WebDriver chromedriver = new ChromeDriver(options);
        chromedriver.get("https://tt-develop.quality-lab.ru");

        chromedriver.manage().window().setSize(newDimension);
        WebDriverWait wait = new WebDriverWait(chromedriver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@value=\"Войти\"]")));

        chromedriver.manage().window().maximize();
        chromedriver.quit();
    }
}
