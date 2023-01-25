package login;

import helpers.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import pages.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.url;
import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;
import static properties.Properties.credentialsProperties;

/**
 * Класс, содержащий негативные тесты
 *
 * @author Алексеев Степан
 * @date 19.12.2022
 */
@Execution(CONCURRENT)
public class LoginNegativeTests extends TestBase {

    /**
     * Проверка того, что текст о неверных данных (о логине и пароле) не выведется при нажатии на кнопку "Войти"
     * с пустыми полями логина и пароля.
     * <p>
     * Проверка того, что не произойдёт перенаправления на какие-либо другие страницы при вышеописанных действиях.
     */
    @Test
    @DisplayName("Тест авторизации с пустыми полями логина, пароля")
    public void checkEmptyLoginPassword() {
        LoginPage lps = open(credentialsProperties.url()
                , LoginPage.class).clickEnterButton();
        Assertions.assertFalse(lps.invalidCredentialsTextIsVisible());
        webdriver().shouldHave(url(credentialsProperties.url()));
    }

    /**
     * Проверка того, что при вводе неверных логина и пароля будет выведено сообщение "Invalid Credentials".
     * Также проверка того, что введённое ранее имя пользователя сохранилось в поле ввода, а пароль исчез.
     */
    @Test
    @DisplayName("Тест авторизации с неверными логином, паролем")
    void incorrectUserNameAndPasswordNew() {
        LoginPage lps = open(credentialsProperties.url(), LoginPage.class)
                .sendLogin(credentialsProperties.incorrectUserName())
                .sendPassword(credentialsProperties.incorrectPassword())
                .clickEnterButton();
        Assertions.assertAll(
                () -> Assertions.assertTrue(lps.invalidCredentialsTextIsVisible(),
                        "Надпись Invalid Credentials не появилась"),
                () -> Assertions.assertEquals(credentialsProperties.incorrectUserName(), lps.getInputLogin().getText(),
                        "Введённое ранее имя пользователя не сохранилось"),
                () -> Assertions.assertEquals("", lps.getInputPassword().getText(),
                        "В поле \"пароль\" есть какой-то текст")
        );
    }
}
