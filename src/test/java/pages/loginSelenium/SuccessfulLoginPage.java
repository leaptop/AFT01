package pages.loginSelenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Класс, реализующий паттерн Page Object для страницы, показываемой после успешного входа в систему по логину и паролю.
 */
public class SuccessfulLoginPage {
    WebDriver chromedriver;

    public SuccessfulLoginPage(WebDriver we) {
        chromedriver = we;
    }

    /**
     * xpath для поиска аватарки пользователя справа вверху.
     */
    private String upperRightCornerAvatar = "//span[@class='m-topbar__userpic']//div[@class='avatarCover']";
    /**
     * xpath для поля ввода логина
     */
    private String UserName = "//span[contains(@class, 'm-card-user__name')]";
    /**
     * xpath для поля ввода пароля
     */
    private String UserEmail = "//span[contains(@class, 'm-card-user__email')]";

    /**
     * @return возвращает вебЭлемент аватарки, расположенной справа вверху.
     */
    public WebElement getupperRightCornerAvatar() {
        return chromedriver.findElement(By.xpath(upperRightCornerAvatar));
    }

    /**
     * @return возвращает Имя пользователя из всплывающей формы после нажатия на аватарку справа сверху
     */
    public String getUserName() {
        return chromedriver.findElement(By.xpath(UserName)).getText();
    }

    /**
     * @return возвращает email пользователя из всплывающей формы после нажатия на аватарку справа сверху
     */
    public String getUserEmail() {
        return chromedriver.findElement(By.xpath(UserEmail)).getText();
    }
}
