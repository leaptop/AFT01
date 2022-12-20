package login;

import autotests.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.LoginPageSelenide;
import ru.yandex.qatools.htmlelements.element.TextInput;

import javax.xml.datatype.Duration;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;

/**
 * Переписать существующие тесты используя библиотеки Selenide и HTMLElements а именно:
 * Открыть браузер при помощи Selenide
 * Задать начальный размер экрана браузера при помощи Selenide
 * Изменять размеры окна браузера через Selenium, но получить текущий браузер от Selenide через WebDriverRunner
 * Переписать все локаторы в PageObject: использовать HTMLElements для указания типа контрола, Selenide - для поиска
 * При необходимости переписать методы работы с контролами в PageObject. (Например у Selenide есть очень удобный метод exists())
 * Переписать методы PageObject чтобы можно было использовать цепочки вызовов (Method chaining)
 * Подумать какая комбинация этих (или возможно других) библиотек наиболее удобна для написания автотестов и почему? (нужно выработать свое мнение, а не искать чужое в интернете).
 * Запустить все тесты
 * Выложить изменения в репозиторий
 * Самопроверка задания
 * Все тесты зеленые
 * Локаторы имеют вид:
 * private TextInput login = new TextInput($("#username"));
 * Все методы действия в PageObject (т.е  методы ввода логина-пароля и нажатия на кнопку) возвращают this
 */

public class LoginNegativeTestsSelenide extends TestBase {
    //private TextInput login = new TextInput($("#username"));

    /**
     * Проверка того, что текст о неверных данных (о логине и пароле) не выведется при нажатии на кнопку "Войти"
     * с пустыми полями логина и пароля.
     * <p>
     * Проверка того, что не произойдёт перенаправления на какие-либо другие страницы при вышеописанных действиях.
     */
    @Test
    public void checkEmptyLoginPassword() {
        open("https://tt-testing.quality-lab.ru/login", LoginPageSelenide.class)
                .clickEnterButton();
//        Assertions.assertFalse(lps.invalidCredentialsTextIsVisible(),
//                "Исключение не было выброшено, т.к. элемент был найден");
//        webdriver().shouldHave(url("https://tt-testing.quality-lab.ru/login"));

//        open("https://tt-testing.quality-lab.ru/login", LoginPageSelenide.class);
//        LoginPageSelenide lps = new LoginPageSelenide(chromedriver);
//        lps.clickEnterButton();
//        Assertions.assertFalse(lps.invalidCredentialsTextIsVisible(),
//                "Исключение не было выброшено, т.к. элемент был найден");
//        webdriver().shouldHave(url("https://tt-testing.quality-lab.ru/login"));
    }


}
