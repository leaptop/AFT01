package login.loginSelenium;

import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.TestBase;
//import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pages.selenium.LoginPage;
import pages.selenium.SuccessfulLoginPage;

/**
 * Класс, содержащий позитивные тесты
 *
 * @author Алексеев Степан
 * @date 18.12.2022
 */

public class LoginPositiveTests extends TestBase {

//    /** Удалить когда включу снятие скриншотов по-другому
//     * add listener to Selenide:
//     */
//    public void initScreensInSelenide(){
//        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
//                .screenshots(true)
//                .savePageSource(false));
//    }

    /**
     * Задание 13: параметризация тестов
     * @param name
     * @param pass
     */
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @CsvSource({"Тест, Тест"})
    void checkCorrectAuthorizationInputViaParameters(String name, String pass) {
        chromedriver.get("https://tt-testing.quality-lab.ru/login");
        LoginPage lp = new LoginPage(chromedriver);
        lp.sendTextToUserInput(name);
        lp.sendTextToPasswordInput(pass);
        lp.clickEnterButton();
        Assertions.assertEquals("https://tt-testing.quality-lab.ru/report/group/edit",
                chromedriver.getCurrentUrl(), "Редирект произошёл не на тот адрес");
        SuccessfulLoginPage slp = new SuccessfulLoginPage(chromedriver);
        slp.getupperRightCornerAvatar().click();
        Assertions.assertEquals(name, slp.getUserName(), "Имя пользователя некорректно");
        Assertions.assertEquals("124124@m.r", slp.getUserEmail(), "email некорректен");
    }

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
        Assertions.assertEquals("https://tt-testing.quality-lab.ru/report/group/edit",
                chromedriver.getCurrentUrl(), "Редирект произошёл не на тот адрес");
        SuccessfulLoginPage slp = new SuccessfulLoginPage(chromedriver);
        slp.getupperRightCornerAvatar().click();
        Assertions.assertEquals("Авто Пользователь", slp.getUserName(), "Имя пользователя некорректно");
        Assertions.assertEquals("124124@m.r", slp.getUserEmail(), "email некорректен");
    }


}
