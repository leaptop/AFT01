package login.loginSelenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.selenium.LoginPage;

import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;

/**
 * @author Алексеев Степан
 * @date 17.12.2022
 */
public class LoginTest {
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
    void finishTest() throws InterruptedException {
        Thread.sleep(3000);
        if (!(chromedriver == null))
            chromedriver.quit();
    }

    /**
     * Задание 6. Проверяю реакцию сайта на ввод неверных данных в поля логина и пароля.
     * <p>
     * В конце добавлены проверки из задания 7:
     * Проверка того, что введённое ранее имя пользователя сохранилось в поле ввода, а пароль исчез.
     */
    @Execution(CONCURRENT)
    @Test
    void incorrectUserNameAndPassword() {
        chromedriver.get("https://tt-testing.quality-lab.ru/login");
        chromedriver.findElement(By.name("_username")).sendKeys("TestUser");
        chromedriver.findElement((By.id("password"))).sendKeys("Password");
        String xpathForInvalidCredentialsText = "//div[contains(text(), 'Invalid credentials.')]";
        Assertions.assertThrows(NoSuchElementException.class,
                () -> chromedriver.findElement(By.xpath(xpathForInvalidCredentialsText)),
                "Исключение не было выброшено, т.к. элемент был найден");
        chromedriver.findElement(By.xpath("//*[@value='Войти']")).click();
        WebElement we = chromedriver.findElement(By.xpath(xpathForInvalidCredentialsText));
        Assertions.assertTrue(!(we == null), "Текст не был найден ");

        LoginPage lp = new LoginPage(chromedriver);
        String str = lp.getTextFromUserNameInput();
        Assertions.assertTrue(!str.isEmpty(), "Введённое ранее имя пользователя не сохранилось");
        String strPass = lp.getTextFromPasswordInput();
        Assertions.assertTrue(strPass.isEmpty(), "В поле пароль есть какой-то текст");
    }
}
