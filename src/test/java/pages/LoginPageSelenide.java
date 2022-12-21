package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.htmlelements.element.TextInput;

import static com.codeborne.selenide.Selectors.byAttribute;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

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
//    private TextInput login; //= new TextInput($("#username"));//By.name("_username")
//    private TextInput login2 ;
//    public LoginPageSelenide insertText(String textToSend){
//        //login2 = new TextInput($(byAttribute("name", "_username")));
////        login = new TextInput($("#username"));//By.name("_username")
////        login.click();
////        return this;
//    }
    public boolean invalidCredentialsTextIsVisible() {
        return $x(xpathForInvalidCredentialsText).exists();
    }
}
