package helpers;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class TestBaseSelenide {
    /**
     * Настраиваем тесты перед запуском.
     */
    @BeforeEach
    public void before() {
        Configuration.browserSize = "1920x1080";//maximize больше нет в селениде

    }

    @AfterEach
    public void after() throws InterruptedException {
        Thread.sleep(4000);
        WebDriverRunner.closeWebDriver();
    }
}
