package authorizedTests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import pages.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static properties.Properties.credentialsProperties;

public class AuthorizedTestBase {
    /**
     * Инициализируем перед запуском каждого теста.
     * Здесь дублируется прописывание разрешения экрана, как и в TestBase потому, что в заданиях до работы
     * с календарём (в CalendarTests) как раз и нужно было использовать разные логины и пароли.
     */
    @BeforeEach
    void initTests() {
        Configuration.browserSize = "1500x800";
        open("https://tt.quality-lab.ru/login", LoginPage.class)
                .sendLogin(credentialsProperties.name())
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
