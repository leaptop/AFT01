package login.loginSelenide;

import com.codeborne.selenide.Configuration;
import helpers.TestBaseSelenide;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pages.selenide.LoginPageSelenide;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;

public class LoginNegativeTestsSelenide extends TestBaseSelenide {
    /**
     * Проверка того, что текст о неверных данных (о логине и пароле) не выведется при нажатии на кнопку "Войти"
     * с пустыми полями логина и пароля.
     * <p>
     * Проверка того, что не произойдёт перенаправления на какие-либо другие страницы при вышеописанных действиях.
     */
    @Test
    public void checkEmptyLoginPassword() {
        Configuration.browserSize = "1920x1080";
        LoginPageSelenide lps = open("https://tt-testing.quality-lab.ru/login"
                , LoginPageSelenide.class).clickEnterButton();
        Assertions.assertFalse(lps.invalidCredentialsTextIsVisible());
        webdriver().shouldHave(url("https://tt-testing.quality-lab.ru/login"));
    }

    /**
     * Проверка того, что при вводе неверных логина и пароля будет выведено сообщение "Invalid Credentials".
     * Также проверка того, что введённое ранее имя пользователя сохранилось в поле ввода, а пароль исчез.
     */
    @Test
    void incorrectUserNameAndPassword() {
        LoginPageSelenide lps = open("https://tt-testing.quality-lab.ru/login", LoginPageSelenide.class)
                .sendLogin("TestUser")
                .sendPassword("Password")
                .clickEnterButton();
        Assertions.assertAll(
                () -> Assertions.assertTrue(lps.invalidCredentialsTextIsVisible(), "Надпись Invalid Credentials не появилась"),
                () -> Assertions.assertEquals("TestUser", lps.getLoginInputText(), "Введённое ранее имя пользователя не сохранилось"),
                () -> Assertions.assertEquals("", lps.getPasswordInputText(), "В поле пароль есть какой-то текст")
        );
    }
}
