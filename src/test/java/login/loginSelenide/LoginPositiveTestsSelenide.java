package login.loginSelenide;

import com.codeborne.selenide.Configuration;
import helpers.TestBaseSelenide;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.selenide.LoginPageSelenide;
import pages.selenide.SuccessfulLoginPageSelenide;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.url;

/**
 * @author Алексеев Степан
 * @date 19.12.2022
 */
public class LoginPositiveTestsSelenide extends TestBaseSelenide {
    /**
     * Проверка авторизации. Позитивный вариант.
     */
    @Test
    void checkCorrectAuthorizationInput() {
        open("https://tt-testing.quality-lab.ru/login", LoginPageSelenide.class)
                .sendLogin("Авто пользователь")
                .sendPassword("12345678")
                .clickEnterButton();
        webdriver().shouldHave(url("https://tt-testing.quality-lab.ru/report/group/edit"));
        SuccessfulLoginPageSelenide slps = new SuccessfulLoginPageSelenide();
        slps.clickUpperRightCornerAvatar();
        Configuration.timeout = 10000;
        SoftAssert soft = new SoftAssert();
        soft.assertEquals("Авто Пользователь", slps.getNameFromCard(), "Имя не равно ожидаемому");
        soft.assertEquals("124124@m.r", slps.getEmailFromCard(), "Имейл не равен ожидаемому");
        soft.assertAll();
    }
}
