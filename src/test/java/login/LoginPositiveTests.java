package login;

import autotests.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import pages.SuccessfulLoginPage;

/**
 * Класс, содержащий позитивные тесты
 */
public class LoginPositiveTests extends TestBase {
    /**
     * Проверка реакции сайта на ввод корректных логина и пароля.
     * Проверка правильности имени и электронной почты пользователя.
     * <p>
     * Задание:
     * Написать тест-метод в новом классе по следующему сценарию (использовать данные своего аккаунта)
     * Открыть страницу ТТ
     * Ввести существующий логин
     * Ввести пароль к выбранному логину
     * Нажать кнопку “Войти”
     * Проверить:
     * Произошел редирект на страницу https://tt-develop.quality-lab.ru/report/group/edit
     * Вход произошел под нужным пользователем:
     * Нажать на аватар в верхнем правом углу
     * Фамилия пользователя соответствует ожидаемой
     * Email пользователя равен: “fake+ИД@quality-lab.ru”
     */
    @Test
    void checkCorrectAuthorizationInput() {
        chromedriver.get("https://tt-testing.quality-lab.ru/login");
        LoginPage lp = new LoginPage(chromedriver);
        lp.sendTextToUserInput("Авто Пользователь");
        lp.sendTextToPasswordInput("12345678");
        lp.clickEnterButton();
        SuccessfulLoginPage slp = new SuccessfulLoginPage(chromedriver);
        Assertions.assertAll(
                ()->Assertions.assertEquals("https://tt-testing.quality-lab.ru/report/group/edit",
                        chromedriver.getCurrentUrl(), "Редирект произошёл не на тот адрес"),
                ()-> slp.getUpperRightCornerAvatar().click(),
                () -> Assertions.assertEquals("Авто Пользователь", slp.getUserName(),
                        "Имя пользователя некорректно"),
                () -> Assertions.assertEquals("124124@m.r", slp.getUserEmail(),
                        "email некорректен")
        );
    }
}
