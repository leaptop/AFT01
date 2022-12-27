package authorizedTests;

import properties.GetMainProperties;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import pages.selenide.LoginPageSelenide;

import static com.codeborne.selenide.Selenide.open;
import static properties.AeonBitsProperties.credentialsProperties;

/**
 * @author Алексеев Степан
 * @date 25.12.2022
 */
public class AuthorizedTestBaseSelenide {
    /**
     * Инициализируем
     * перед запуском каждого теста.
     */
    @BeforeEach
    void initTests() {
        Configuration.browserSize = "1500x800";     //maximize больше нет в селениде
        //open("https://tt.quality-lab.ru/login", LoginPageSelenide.class)
        GetMainProperties.loadMainProperties();
        String username = GetMainProperties
                .getPropertyFromEnvironmentVariable("USERNAME2");
        open(GetMainProperties.url, LoginPageSelenide.class)
                .sendLogin(username)
                .sendPassword(credentialsProperties.password())
                .clickEnterButton()
        ;
    }

    /**
     * Метод завершает работу вебдрайвера после выполнения каждого теста.
     */
    @AfterEach
    public void after() {
        WebDriverRunner.closeWebDriver();
    }
}
