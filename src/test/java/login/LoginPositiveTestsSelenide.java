package login;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pages.LoginPageSelenide;
import pages.SuccessfulLoginPageSelenide;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.url;

public class LoginPositiveTestsSelenide {
    @Test
    void checkCorrectAuthorizationInput() throws InterruptedException {
        Configuration.browserSize = "1920x1080";//maximize больше нет в селениде
        open("https://tt-testing.quality-lab.ru/login", LoginPageSelenide.class)
                .sendLogin("Авто пользователь")
                .sendPassword("12345678")
                .clickEnterButton()
        ;
        webdriver().shouldHave(url("https://tt-testing.quality-lab.ru/report/group/edit"));
        SuccessfulLoginPageSelenide slps = new SuccessfulLoginPageSelenide();
        slps.clickUpperRightCornerAvatar()
        ;
        Configuration.timeout = 10000;
        Assertions.assertEquals("Авто Пользователь", slps.getNameFromCard(), "Имя не равно ожидаемому");
        Assertions.assertEquals("124124@m.r", slps.getEmailFromCard(), "Имейл не равен ожидаемому");

        Thread.sleep(4000);
    }
}
