package login;

import helpers.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import pages.SuccessfulLoginPage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.url;

/**
 * Класс, содержащий позитивные тесты
 */
public class LoginPositiveTests extends TestBase {

    /**
     * Проверка авторизации. Позитивный вариант.
     */
    @Test
    void checkCorrectAuthorizationInputNew() {
        open("https://tt-testing.quality-lab.ru/login", LoginPage.class)
                .sendLogin("Авто пользователь")
                .sendPassword("12345678")
                .clickEnterButton();
        webdriver().shouldHave(url("https://tt-testing.quality-lab.ru/report/group/edit"));
        SuccessfulLoginPage slps = new SuccessfulLoginPage();
        slps.clickUpperRightCornerAvatar();
        Assertions.assertAll(
                () -> Assertions.assertEquals("Авто Пользователь", slps.getNameTextBlock().getText(),
                        "Имя не равно ожидаемому"),
                () -> Assertions.assertEquals("124124@m.r", slps.getEmailTextBlock().getText(),
                        "Имейл не равен ожидаемому")
        );
    }
}
