package authorizedTests;

import helpers.TestBase;
import io.qameta.allure.Step;
import org.junit.jupiter.api.BeforeEach;
import pages.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static properties.Properties.credentialsProperties;

/**
 * @author Алексеев Степан
 * @date 25.12.2022
 */
public class AuthorizedTestBase extends TestBase {
    /**
     * Инициализируем перед запуском каждого теста.
     * Здесь дублируется прописывание разрешения экрана, как и в TestBase потому, что в заданиях до работы
     * с календарём (в CalendarTests) как раз и нужно было использовать разные логины и пароли.
     */
    @Step
    @BeforeEach
    void authenticate() {
        open("https://tt.quality-lab.ru/login", LoginPage.class)
                .sendLogin(credentialsProperties.username())
                .sendPassword(credentialsProperties.password())
                .clickEnterButton()
        ;
    }
}
