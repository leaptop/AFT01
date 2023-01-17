package login;

import helpers.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import pages.SuccessfulLoginPage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.url;
import static properties.Properties.credentialsProperties;

/**
 * Класс, содержащий позитивные тесты
 */
public class LoginPositiveTests extends TestBase {

    /**
     * Проверка авторизации. Позитивный вариант.
     */
    @Test
    void checkCorrectAuthorizationInputNew() {
        open(credentialsProperties.url(), LoginPage.class)
                .sendLogin(credentialsProperties.autoUser())
                .sendPassword(credentialsProperties.autoUserPassword())
                .clickEnterButton();
        webdriver().shouldHave(url("https://tt-testing.quality-lab.ru/report/group/edit"));
        SuccessfulLoginPage slps = new SuccessfulLoginPage();
        slps.clickUpperRightCornerAvatar();
        Assertions.assertAll(
                () -> Assertions.assertEquals(credentialsProperties.autoUser(), slps.getNameTextBlock().getText(),
                        "Имя не равно ожидаемому"),
                () -> Assertions.assertEquals("124124@m.r", slps.getEmailTextBlock().getText(),
                        "Имейл не равен ожидаемому")
        );
    }
}
