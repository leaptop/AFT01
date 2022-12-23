package login.loginSelenium;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import helpers.TestBase;
import pages.selenium.LoginPage;

/**
 * Класс, содержащий негативные тесты
 */
public class LoginNegativeTests extends TestBase {
    /**
     * Проверка того, что текст о неверных данных (о логине и пароле) не выведется при нажатии на кнопку "Войти"
     * с пустыми полями логина и пароля.
     * <p>
     * Проверка того, что не произойдёт перенаправления на какие-либо другие страницы при вышеописанных действиях.
     */
    @Test
    void task7point2() {
        chromedriver.get("https://tt-testing.quality-lab.ru/login");
        LoginPage lp = new LoginPage(chromedriver);
        lp.clickEnterButton();
        Assertions.assertFalse(lp.invalidCredentialsTextIsVisible(),
                "Исключение не было выброшено, т.к. элемент был найден");
        Assertions.assertEquals("https://tt-testing.quality-lab.ru/login", chromedriver.getCurrentUrl()
                , "URL-адрес изменился");
    }
}
