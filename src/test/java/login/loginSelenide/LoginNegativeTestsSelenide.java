package login.loginSelenide;

import com.codeborne.selenide.Configuration;
import helpers.TestBaseSelenide;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.selenide.LoginPageSelenide;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.url;
import static org.testng.Assert.assertFalse;

/**
 * @author Алексеев Степан
 * @date 19.12.2022
 */
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
        LoginPageSelenide lps = open("https://tt-testing.quality-lab.ru/login",
                LoginPageSelenide.class).clickEnterButton();
        SoftAssert soft = new SoftAssert();
        assertFalse(lps.invalidCredentialsTextIsVisible());
        webdriver().shouldHave(url("https://tt-testing.quality-lab.ru/login"));
    }

    /**
     * Проверка того, что при вводе неверных логина и пароля будет выведено сообщение "Invalid Credentials".
     * Также проверка того, что введённое ранее имя пользователя сохранилось в поле ввода, а пароль исчез.
     */
    @Test
    public void incorrectUserNameAndPassword() {
        LoginPageSelenide lps = open("https://tt-testing.quality-lab.ru/login", LoginPageSelenide.class)
                .sendLogin("TestUser")
                .sendPassword("Password")
                .clickEnterButton();
        SoftAssert soft = new SoftAssert();

        soft.assertTrue(lps.invalidCredentialsTextIsVisible(), "Надпись Invalid Credentials не появилась");
        soft.assertEquals("TestUser", lps.getLoginInputText(), "Введённое ранее имя пользователя не сохранилось");
        soft.assertEquals("", lps.getPasswordInputText(), "В поле пароль есть какой-то текст");
        soft.assertAll();
    }
}
