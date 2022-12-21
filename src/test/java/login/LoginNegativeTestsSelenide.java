package login;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import pages.LoginPageSelenide;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;

public class LoginNegativeTestsSelenide {
    /**
     * Проверка того, что текст о неверных данных (о логине и пароле) не выведется при нажатии на кнопку "Войти"
     * с пустыми полями логина и пароля.
     * <p>
     * Проверка того, что не произойдёт перенаправления на какие-либо другие страницы при вышеописанных действиях.
     */
    @Test
    public void checkEmptyLoginPassword() throws InterruptedException {
        Configuration.browserSize = "1000x1000";
        open("https://tt-testing.quality-lab.ru/login"
                , LoginPageSelenide.class).clickEnterButton();
        $x("//div[contains(text(), 'Invalid credentials.')]").shouldNotBe(Condition.visible);
        webdriver().shouldHave(url("https://tt-testing.quality-lab.ru/login"));


        Thread.sleep(4000);
    }
}
