package helpers;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * @author Алексеев Степан
 * @date 23.12.2022
 */
public class TestBaseSelenide {
    /**
     * Настраиваем тесты перед запуском.
     */
    @BeforeEach
    public void before() {
        Configuration.browserSize = "1500x800";     //maximize больше нет в селениде
    }

    @AfterEach
    public void after() {
        WebDriverRunner.closeWebDriver();
    }
}
