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
     */
    @Step("Авторизуемся в системе")
    @BeforeEach
    void authenticate() {
        open(credentialsProperties.url(), LoginPage.class)
                .sendLogin(credentialsProperties.username())
                .sendPassword(credentialsProperties.password())
                .clickEnterButton()
        ;
    }
}
