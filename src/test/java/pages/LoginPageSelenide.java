package pages;

import com.codeborne.selenide.selector.ByAttribute;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.htmlelements.element.TextInput;

import static com.codeborne.selenide.Selectors.by;
import static com.codeborne.selenide.Selectors.byAttribute;
import static com.codeborne.selenide.Selenide.*;

public class LoginPageSelenide {
//    WebDriver chromedriver;
//
//    public LoginPageSelenide(WebDriver we) {
//        chromedriver = we;
//    }
    /**
     * xpath для текста, появляющегося при неверном логине и/или пароле
     */
    private String xpathForInvalidCredentialsText = "//div[contains(text(), 'Invalid credentials.')]";
    /**
     * xpath для кнопки "Войти"
     */
    private String xpathForEnterButton = "//*[@value='Войти']";
    public LoginPageSelenide clickEnterButton(){
        $(By.xpath(xpathForEnterButton)).click();
        return this;
    }
    //protected LoginPageSelenide fillInput(WebElement element, String value) {
    protected LoginPageSelenide fillInput(WebElement element, String value) {
        TextInput input = new TextInput(element);
        input.sendKeys(input.getClearCharSequence() + value);
        return this;
    }
    public LoginPageSelenide fillName(String name){
        //  fillInput((WebElement) by("name","_username"), name);
        fillInput(webdriver().object().findElement(By.name("_username")), name);
        return this;
    }
    public boolean invalidCredentialsTextIsVisible() {
        return $x(xpathForInvalidCredentialsText).exists();
    }
}
