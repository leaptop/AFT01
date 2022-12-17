import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class LoginTest {
    public WebDriver chromedriver;

    /**
     * Инициализируем вебдрайвер, настраиваем его перед запуском каждого теста.
     */
    @BeforeEach
    void initTests() {
        System.setProperty("webdriver.chrome.driver", System.getenv("CHROME_DRIVER"));
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--window-size=500,500");
        chromedriver = new ChromeDriver(options);
        chromedriver.manage().window().maximize();
    }

    /**
     * Метод завершает работу вебдрайвера после выполнения каждого теста.
     */
    @AfterEach
    void finishTest() throws InterruptedException {
        Thread.sleep(3000);
        chromedriver.quit();
    }

    /**
     * https://tt-develop.quality-lab.ru это вроде не подойдёт
     * https://tt-testing.quality-lab.ru/login а это вроде подойдёт
     * Ввести в поле “Имя пользователя” значение “TestUser”. Искать поле по атрибуту name
     * Ввести в поле “Пароль” значение “Password”. Искать поле по атрибуту id
     * Проверить, что на странице нет текста “Invalid credentials.”.
     * Он появится после нажатия кнопки (Войти). Искать блок при помощи xpath (ищем блок содержащий текст)
     * Нажать кнопку “Войти”. Искать кнопку по атрибуту value
     * Проверить что сайт выдал текст “Invalid credentials.”.
     * Запустить тест
     * Выложить изменения в репозиторий
     */
    @Test
    void incorrectUserNameAndPassword() {
        String xpathForInvalidCredentialsText = "//div[contains(text(), 'Invalid credentials.')]";
        chromedriver.get("https://tt-testing.quality-lab.ru/login");
        chromedriver.findElement(By.name("_username")).sendKeys("TestUser");
        chromedriver.findElement((By.id("password"))).sendKeys("Password");
        Assertions.assertTrue(chromedriver.findElements(By.xpath(
                        "//div[contains(text(), 'Invalid credentials.')]")).size() == 0
                , "Надпись найдена");
        chromedriver.findElement(By.xpath("//*[@value='Войти']")).click();
        Assertions.assertTrue(chromedriver.findElements(By.xpath(
                        "//div[contains(text(), 'Invalid credentials.')]")).size() > 0
                , "Надпись не найдена"
        );
    }
}
