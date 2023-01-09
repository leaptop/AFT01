package pages.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

/**
 * Page Object для страницы с вводом логина и пароля.
 */
public class LoginPage {
    WebDriver chromedriver;

    public LoginPage(WebDriver we) {
        chromedriver = we;
    }

    /**
     * @return Возвращает текст Invalid credentials.
     */
    public String getInvalidCredentialsText() {
        return chromedriver.findElement(By.xpath("//div[contains(text(), 'Invalid credentials.')]")).getText();
    }

    /**
     * Нажимает кнопку "Войти"
     */
    public void clickEnterButton() {
        chromedriver.findElement(By.xpath("//*[@value='Войти']")).click();
    }

    /**
     * @return возвращает текст из поля ввода логина
     */
    public String getTextFromUserNameInput() {
        return chromedriver.findElement(By.name("_username")).getAttribute("value");
    }

    /**
     * @return возвращает текст из поля ввода пароля
     */
    public String getTextFromPasswordInput() {
        return chromedriver.findElement(By.id("password")).getAttribute("value");
    }

    /**
     * Прописывает текст в поле ввода логина
     *
     * @param textToSend логин
     */
    public void sendTextToUserInput(String textToSend) {
        chromedriver.findElement(By.name("_username")).sendKeys(textToSend);
    }

    /**
     * Прописывает текст в поле ввода пароля
     *
     * @param textToSend пароль
     */
    public void sendTextToPasswordInput(String textToSend) {
        chromedriver.findElement(By.id("password")).sendKeys(textToSend);
    }

    /**
     * @return true, если надпись о неверном логине и/или пароле появилась.
     */
    public boolean invalidCredentialsTextIsVisible() {
        try {
            chromedriver.findElement(By.xpath("//div[contains(text(), 'Invalid credentials.')]"));
        } catch (NoSuchElementException nsee) {
            return false;
        }
        return true;
    }
}
