package login;

import helpers.TestBase;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.url;
import static properties.Properties.credentialsProperties;

/**
 * Класс, содержащий негативные тесты
 *
 * @author Алексеев Степан
 * @date 19.12.2022
 */
public class LoginNegativeTests extends TestBase {

    /**
     * Проверка того, что текст о неверных данных (о логине и пароле) не выведется при нажатии на кнопку "Войти"
     * с пустыми полями логина и пароля.
     * <p>
     * Проверка того, что не произойдёт перенаправления на какие-либо другие страницы при вышеописанных действиях.
     */
    @Test
    public void checkEmptyLoginPassword() {
        LoginPage lps = open(credentialsProperties.url()
                , LoginPage.class).clickEnterButton();
        Assert.assertFalse(lps.invalidCredentialsTextIsVisible());
        webdriver().shouldHave(url(credentialsProperties.url()));
    }

    /**
     * Проверка того, что при вводе неверных логина и пароля будет выведено сообщение "Invalid Credentials".
     * Также проверка того, что введённое ранее имя пользователя сохранилось в поле ввода, а пароль исчез.
     */
    @Test
    void incorrectUserNameAndPasswordNew() {
        LoginPage lps = open(credentialsProperties.url(), LoginPage.class)
                .sendLogin(credentialsProperties.incorrectUserName())
                .sendPassword(credentialsProperties.incorrectPassword())
                .clickEnterButton();
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(lps.invalidCredentialsTextIsVisible(),"Надпись Invalid Credentials не появилась");
        soft.assertEquals(credentialsProperties.incorrectUserName(), lps.getInputLogin().getText(),
                "Введённое ранее имя пользователя не сохранилось");
        soft.assertEquals("", lps.getInputPassword().getText(),
                "В поле \"пароль\" есть какой-то текст");
        soft.assertAll();
    }
}
