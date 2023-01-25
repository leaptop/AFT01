package login;

import helpers.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
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
@Execution(CONCURRENT)
public class LoginTests extends TestBase {
    /**
     * Проверка авторизации. Негативный и позитивный тесты
     */
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @CsvSource({"Авто Пользователь, 12345678, 124124@m.r", "Тест, Тест, 1@m.r"})
    @DisplayName("Тест авторизации сначала с верными, потом с неверными логином, паролем, проверкой имейла, имени")
    void checkParameterizedAuthorizationInput(
            String name, String pass, String mail) {
        open(credentialsProperties.urltesting(), LoginPage.class)
                .sendLogin(name)
                .sendPassword(pass)
                .clickEnterButton();
        webdriver().shouldHave(url(credentialsProperties.urlTestingEdit()));
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
