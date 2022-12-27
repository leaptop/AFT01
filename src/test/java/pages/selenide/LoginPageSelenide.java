package pages.selenide;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.TextInput;

import static com.codeborne.selenide.Selenide.*;

/**
 * @author Алексеев Степан
 * @date 19.12.2022
 */
public class LoginPageSelenide {
    /**
     * Яндексовский элемент для работы с инпутами. Находит поле ввода логина.
     */
    private TextInput inputLogin = new TextInput(webdriver().object().findElement(By.name("_username")));
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

    /**
     * @return возвращает текст из поля ввода пароля
     */
    public String getPasswordInputText() {
        inputPassword = new TextInput(webdriver().object().findElement(By.id("password")));
        return inputPassword.getText();
    }
    /**
     * Яндексовский элемент для работы с инпутами. Находит поле ввода пароля.
     */
    private TextInput inputPassword = new TextInput(webdriver().object().findElement(By.id("password")));
    /**
     * Яндексовский элемент для сохранения кнопки. Находит кнопку "Войти".
     */
    private Button enterButton = new Button($(By.xpath(xpathForEnterButton)));

    /**
     * @return возвращает текст из поля ввода логина
     */
    public String getLoginInputText() {
        inputLogin = new TextInput(webdriver().object().findElement(By.name("_username")));
        return inputLogin.getText();
    }

    /**
     * Нажимаю кнопку ввода
     * @return
     */
    public LoginPageSelenide clickEnterButton() {
        enterButton.click();
        return this;
    }

    /**
     * Шлю логин с помощью объекта TextInput
     *
     * @param login логин
     * @return возвращаем текущую страницу для возможности вызова других методов по цепочке.
     */
    public LoginPageSelenide sendLogin(String login) {
        inputLogin.sendKeys(login);
        return this;
    }

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
     * Универсальный метод для передачи текста с помощью TextInput
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
