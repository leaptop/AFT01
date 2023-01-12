package login.loginSelenium;

import helpers.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pages.selenium.LoginPage;
import pages.selenium.SuccessfulLoginPage;

import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;

/**
 * Класс, содержащий позитивные тесты
 *
 * @author Алексеев Степан
 * @date 18.12.2022
 */

public class LoginPositiveTests extends TestBase {

    /**
     * Задание 13: параметризация тестов
     * Проверка реакции сайта на ввод корректных логина и пароля.
     * Проверка правильности имени и электронной почты пользователя.
     * Создать ветку task13-15 от ветки task10-12 и переключиться на нее
     * Реализовать автотест по следующему сценарию:
     * Открыть страницу https://tt-develop.quality-lab.ru/login
     * Ввести логин “Тест”
     * Ввести пароль “Тест”
     * Нажать кнопку “Войти”
     * Проверить что авторизация успешна:
     * Произошел редирект на страницу https://tt-develop.quality-lab.ru/report/stats/project
     * Вход произошел под нужным пользователем:
     * Нажать на аватар в верхнем правом углу
     * Фамилия пользователя соответствует ожидаемой
     * Он упадет и это нормально
     * Реализовать сбор следующей информации в случае падения автотеста:
     * Скриншот страницы во время падения
     * Текст ошибки на русском.
     * StackTrace
     * Запустить новый тест
     * Сформировать отчет allure
     * Выложить изменения в репозиторий
     * Самопроверка задания
     * Тест из LoginPositiveTests не продублирован с новыми значениями, а параметризован
     * При запуске теста в результатах выводятся два запуска с разными параметрами
     * Новый тест красный
     * В отчете allure прицеплен скриншот и выведены текст ошибки и stackTrace
     *
     * @param name логин
     * @param pass пароль
     */
    @Execution(CONCURRENT)
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @CsvSource({"Авто Пользователь, 12345678, 124124@m.r", "Тест, Тест, 1@m.r"})
    void checkCorrectAuthorizationInputViaParameters
    (String name, String pass, String mail) {
        chromedriver.get("https://tt-testing.quality-lab.ru/login");
        LoginPage lp = new LoginPage(chromedriver);
        lp.sendTextToUserInput(name);
        lp.sendTextToPasswordInput(pass);
        lp.clickEnterButton();
        SuccessfulLoginPage slp = new SuccessfulLoginPage(chromedriver);
        Assertions.assertAll(
                () -> Assertions.assertEquals("https://tt-testing.quality-lab.ru/report/group/edit",
                        chromedriver.getCurrentUrl(), "Редирект произошёл не на тот адрес"),
                () -> slp.getUpperRightCornerAvatar().click(),
                () -> Assertions.assertEquals(name, slp.getUserName(),
                        "Имя пользователя некорректно"),
                () -> Assertions.assertEquals(mail, slp.getUserEmail(),
                        "email некорректен")
        );
    }
}
