package login;

import helpers.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
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

    /**
     * Проверка авторизации. Позитивный вариант.
     */
    @Test
    @Execution(CONCURRENT)
    @DisplayName("Тест авторизации с верными логином, паролем, проверкой имейла, имени")
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
