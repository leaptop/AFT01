package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.TextInput;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static io.qameta.allure.Allure.step;

public class LoginPage {
    /**
     * @return true если текст о неверных логине/пароле выведен
     */
    public boolean invalidCredentialsTextIsVisible() {
        return $x(xpathForInvalidCredentialsText).exists();
    }

    /**
     * xpath для текста, появляющегося при неверном логине и/или пароле
     */
    private String xpathForInvalidCredentialsText = "//div[contains(text(), 'Invalid credentials.')]";
    /**
     * xpath для кнопки "Войти"
     */
    private String xpathForEnterButton = "//*[@value='Войти']";

    /**
     * Яндексовский элемент для работы с инпутами. Находит поле ввода логина.
     * Эти элементы м.б. публичными, т.к. они для этого и сделаны.
     */
    private TextInput inputLogin = new TextInput($("input[name='_username']"));

    /**
     * Яндексовский элемент для работы с инпутами. Находит поле ввода пароля.
     */
    private TextInput inputPassword = new TextInput($("input[id='password']"));

    /**
     * Яндексовский элемент для сохранения кнопки. Находит кнопку "Войти".
     */
    private Button enterButton = new Button($(By.xpath(xpathForEnterButton)));

    /**
     * Шлёт логин в поле логина
     *
     * @param name логин
     * @return возвращает текущую страницу для продолжения вызова методов по цепочке
     */
    public LoginPage sendLogin(String name) {
        step("Ввод логина", () -> {} ) ;
        inputLogin.sendKeys(name);
        return this;
    }

    /**
     * Шлёт пароль в поле пароля
     *
     * @param password пароль
     * @return возвращает текущую страницу для продолжения вызова методов по цепочке
     */
    public LoginPage sendPassword(String password) {
        step("Ввод пароля", () -> {} ) ;
        inputPassword.sendKeys(password);
        return this;
    }

    /**
     * Кликает по кнопке ввода "Войти"
     *
     * @return возвращает текущую страницу для продолжения вызова методов по цепочке
     */
    @Step("Жмём кнопку \"Войти\"")
    public LoginPage clickEnterButton() {
        enterButton.click();
        return this;
    }

    /**
     * @return возвращает текстовое поле для ввода логина
     */
    @Step("Получаем текстовое поле для ввода логина")
    public TextInput getInputLogin() {
        return inputLogin;
    }

    /**
     * @return возвращает текстовое поле для ввода пароля
     */
    @Step("Получаем текстовое поле для ввода пароля")
    public TextInput getInputPassword() {
        return inputPassword;
    }
}
