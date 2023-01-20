package login;

import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.TestBase;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pages.LoginPage;
import pages.SuccessfulLoginPage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.url;
import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;
import static properties.Properties.credentialsProperties;

/**
 * @author Алексеев Степан
 * @date 19.12.2022
 */
public class LoginPositiveTests extends TestBase {
    @BeforeAll
    static void setupAllureReports() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(false)
        );
    }
    /**
     * Проверка авторизации. Позитивный вариант.
     */
    @Execution(CONCURRENT)
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @CsvSource({"Авто Пользователь, 12345678, 124124@m.r", "Тест, Тест, 1@m.r"})
    @DisplayName("Тест авторизации с верными логином, паролем, проверкой имейла, имени")
    void checkCorrectAuthorizationInputNew(
            String name, String pass, String mail) {
        open(credentialsProperties.urltesting(), LoginPage.class)
                .sendLogin(name)
                .sendPassword(pass)
                .clickEnterButton();
        webdriver().shouldHave(url("https://tt-testing.quality-lab.ru/report/group/edit"));
        SuccessfulLoginPage slps = new SuccessfulLoginPage();
        slps.clickUpperRightCornerAvatar();
        Assertions.assertAll(
                () -> Assertions.assertEquals(name, slps.getNameTextBlock().getText(),
                        "Имя не равно ожидаемому"),
                () -> Assertions.assertEquals(mail, slps.getEmailTextBlock().getText(),
                        "Имейл не равен ожидаемому")
        );
    }
}
