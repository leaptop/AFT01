package helpers;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * Класс для реализации базового функционала для всех тестов, написанных с использованием Selenium.
 *
 * @author Алексеев Степан
 * @date 20.12.2022
 */
public class TestBase {
    /**
     * Настраиваем тесты перед запуском.
     *
     * Здесь дублируется прописывание разрешения экрана, как и в AuthorizedTestBase потому, что в заданиях до работы
     * с календарём (в CalendarTests) как раз и нужно было использовать разные логины и пароли.
     */
    @BeforeEach
    public void before() {
        Configuration.browserSize = "1500x800";
    }

    /**
     * Закрываем вебдрайвер в конце каждого теста
     */
    @AfterEach
    public void after() {
        WebDriverRunner.closeWebDriver();
    }
}
