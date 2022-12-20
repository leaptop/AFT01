package login;

import autotests.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import pages.LoginPageSelenide;
import pages.SuccessfulLoginPage;

import static com.codeborne.selenide.Selenide.open;

public class LoginPositiveTestsSelenide extends TestBase {
    @Test
    void checkCorrectAuthorizationInput() {
      //  chromedriver.get("https://tt-testing.quality-lab.ru/login");
        open("https://tt-testing.quality-lab.ru/login", LoginPageSelenide.class);
      //  LoginPage lp = new LoginPage(chromedriver);
//        lp.sendTextToUserInput("Авто Пользователь");
//        lp.sendTextToPasswordInput("12345678");
//        lp.clickEnterButton();
//        Assertions.assertEquals("https://tt-testing.quality-lab.ru/report/group/edit",
//                chromedriver.getCurrentUrl(), "Редирект произошёл не на тот адрес");
//        SuccessfulLoginPage slp = new SuccessfulLoginPage(chromedriver);
//        slp.getupperRightCornerAvatar().click();
//        Assertions.assertEquals("Авто Пользователь", slp.getUserName(), "Имя пользователя некорректно");
//        Assertions.assertEquals("124124@m.r", slp.getUserEmail(), "email некорректен");
    }
}
