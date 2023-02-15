package task23control.helpers;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

/**
 * Класс для реализации базового функционала для всех тестов, написанных с использованием Selenium.
 *
 * @author Алексеев Степан
 * @date 20.12.2022
 */
@Listeners(MyTestListener.class)
public class TestBase {
    public WebDriver chromedriver;

    /**
     * Инициализируем вебдрайвер, настраиваем его перед запуском каждого теста.
     */
    @BeforeMethod
    @Step("Устанавливаем разрешение экрана")
    public void before() {
        Configuration.browserSize = "1500x800";
        Configuration.pageLoadTimeout = 60000;
    }

    /**
     * Метод завершает работу вебдрайвера после выполнения каждого теста.
     * <p>
     * Его необходимо отключить на время использования MyTestListener, т.к.
     * иначе нельзя получить доступ к вебдрайверу (он закрывается здесь до
     * запуска методов из MyTestListener).
     */
    // @AfterMethod
    void finishTest() {
        WebDriverRunner.closeWebDriver();
    }
}
