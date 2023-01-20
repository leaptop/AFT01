package helpers;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import com.codeborne.selenide.WebDriverRunner;
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
    @Step("Устанавливаем разрешение экрана")
    public void before() {
        Configuration.browserSize = "1500x800";
    }

    /**
     * Метод завершает работу вебдрайвера после выполнения каждого теста.
     *
     * Его необходимо отключить на время использования MyTestWatcher, т.к.
     * иначе нельзя получить доступ к вебдрайверу (он закрывается здесь до
     * запуска методов из MyTestWatcher).
     */
   // @AfterEach
    void finishTest() {
        WebDriverRunner.closeWebDriver();
    }
}
