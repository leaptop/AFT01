package login;

import helpers.TestBase;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;
import pages.SuccessfulLoginPage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.url;
import static properties.Properties.credentialsProperties;

/**
 * Запускать на tt-testing
 *
 * @author Алексеев Степан
 * @date 19.12.2022
 */
public class LoginPositiveTests extends TestBase {

    /**
     * Проверка авторизации. Позитивный и негативный варианты.
     */
    @Test
    @Parameters({"name", "pass", "mail"})
    void checkCorrectAuthorizationInputNew(
            String name, String pass, String mail) {
        open(credentialsProperties.url(), LoginPage.class)
                .sendLogin(name)
                .sendPassword(pass)
                .clickEnterButton();
        webdriver().shouldHave(url(credentialsProperties.urlReportEdit()));
        SuccessfulLoginPage slps = new SuccessfulLoginPage();
        slps.clickUpperRightCornerAvatar();
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(name, slps.getNameTextBlock().getText(), "Имя не равно ожидаемому");
        soft.assertEquals(mail, slps.getEmailTextBlock().getText(), "Имейл не равен ожидаемому");
        soft.assertAll();
    }
}
