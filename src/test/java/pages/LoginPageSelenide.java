package pages;

import com.codeborne.selenide.selector.ByAttribute;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.TextInput;

import static com.codeborne.selenide.Selectors.by;
import static com.codeborne.selenide.Selectors.byAttribute;
import static com.codeborne.selenide.Selenide.*;

public class LoginPageSelenide {
    /**
     * xpath для текста, появляющегося при неверном логине и/или пароле
     */
    private String xpathForInvalidCredentialsText = "//div[contains(text(), 'Invalid credentials.')]";
    /**
     * xpath для кнопки "Войти"
     */
    private String xpathForEnterButton = "//*[@value='Войти']";
    public boolean invalidCredentialsTextIsVisible() {
        return $x(xpathForInvalidCredentialsText).exists();
    }

    public LoginPageSelenide clickEnterButton() {
        //$(By.xpath(xpathForEnterButton)).click();
        enterButton.click();
        return this;
    }

    /**
     * Яндексовский элемент для сохранения кнопки. Находит кнопку "Войти".
     */
    private Button enterButton = new Button($(By.xpath(xpathForEnterButton)));

    /**
     * Яндексовский элемент для работы с инпутами. Находит поле ввода логина.
     */
    private TextInput inputLogin = new TextInput(webdriver().object().findElement(By.name("_username")));

    /**
     * Шлю логин с помощью объекта TextInput
     *
     * @param login логин
     * @return
     */
    public LoginPageSelenide sendLogin(String login) {
        inputLogin.sendKeys(login);
        return this;
    }

    /**
     * Яндексовский элемент для работы с инпутами. Находит поле ввода пароля.
     */
    private TextInput inputPassword = new TextInput(webdriver().object().findElement(By.id("password")));

    /**
     * Шлю пароль с помощью объекта TextInput
     *
     * @param password пароль
     * @return возвращаем текущую страницу для возможности вызова других методов по цепочке.
     */
    public LoginPageSelenide sendPassword(String password) {
        inputPassword.sendKeys(password);
        return this;
    }
    /**
     * Универсальный метод для передачи текста с помощью TextInput из библиотеки яндекса.
     * <p>
     * Кажется, что его можно и нужно сократить до возможности работать с...
     * <p>
     * Вообще вроде как надо создать TextInput для обоих полей - пароля и логина...
     * Меня смущает, что они инициализируются через new... Где их инициализировать? В конструкторе?
     *
     * @param element
     * @param value
     * @return
     */
    protected LoginPageSelenide fillInput(WebElement element, String value) {
        TextInput input = new TextInput(element);
        input.sendKeys(input.getClearCharSequence() + value);
        return this;
    }
    /**
     * Заполняю логин с помощью селениумовского By. Для селенида похоже нет селенидовского метода поиска по атрибутам.
     * //TextInput inpa = new TextInput(ByAttribute("name", "_username")); не сработает. Это для CSS вроде
     *
     * @param name логин
     * @return возвращаем текущую страницу для возможности вызова других методов по цепочке.
     */
    public LoginPageSelenide fillName(String name) {
        fillInput(webdriver().object().findElement(By.name("_username")), name);
        return this;
    }

    /**
     * Заполняю пароль с помощью селениумовского By. Для селенида похоже нет селенидовского метода поиска по атрибутам.
     *
     * @param pass пароль
     * @return возвращаем текущую страницу для возможности вызова других методов по цепочке.
     */
    public LoginPageSelenide fillPassword(String pass) {
        fillInput(webdriver().object().findElement(By.id("password")), pass);
        return this;
    }
}
