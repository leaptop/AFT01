package login;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import autotests.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import pages.LoginPage;

/**
 * Класс, содержащий негативные тесты
 */
public class LoginNegativeTests extends TestBase {
    /**
     * Задание 6. Проверяю реакцию сайта на ввод неверных данных в поля логина и пароля.
     * <p>
     * В конце добавлены проверки из задания 7:
     * Проверка того, что введённое ранее имя пользователя сохранилось в поле ввода, а пароль исчез.
     */
    @Test
    void incorrectUserNameAndPassword() {
        chromedriver.get("https://tt-testing.quality-lab.ru/login");
        LoginPage lp = new LoginPage(chromedriver);
        lp.sendTextToUserInput("TestUser");
        lp.sendTextToPasswordInput("Password");
        Assertions.assertThrows(NoSuchElementException.class,
                () -> chromedriver.findElement(By.xpath(lp.xpathForInvalidCredentialsText)),
                "Текст с надписью \"Invalid credentials.\" появился, этого не должно было произойти"
        );
        lp.clickEnterButton();
        Assertions.assertEquals("Invalid credentials.",
                chromedriver.findElement(By.xpath(lp.xpathForInvalidCredentialsText)).getText(),
                "Текст с надписью \"Invalid credentials.\" должен был появиться, но не появился");
        String str = lp.getTextFromUserNameInput();
        Assertions.assertTrue(!str.isEmpty(), "Введённое ранее имя пользователя не сохранилось в поле ввода");
        String strPass = lp.getTextFromPasswordInput();
        Assertions.assertTrue(strPass.isEmpty(), "В поле пароль есть какой-то текст, его там не должно быть");
    }

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
                "Текст с надписью \"Invalid credentials.\" появился, чего не должно было произойти");
        Assertions.assertEquals("https://tt-testing.quality-lab.ru/login", chromedriver.getCurrentUrl()
                , "URL-адрес изменился, чего не должно было произойти");
    }
}
