package pages.selenium;

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
     * @return возвращает вебЭлемент аватарки, расположенной справа вверху.
     */
    public WebElement getUpperRightCornerAvatar() {
        return chromedriver.findElement(By.xpath(
                "//span[@class='m-topbar__userpic']//div[@class='avatarCover']"));
    }

    /**
     * @return возвращает Имя пользователя из всплывающей формы после нажатия на аватарку справа сверху
     */
    public String getUserName() {
        return chromedriver.findElement(By.xpath(
                "//span[contains(@class, 'm-card-user__name')]")).getText();
    }

    /**
     * @return возвращает email пользователя из всплывающей формы после нажатия на аватарку справа сверху
     */
    public String getUserEmail() {
        return chromedriver.findElement(By.xpath(
                "//span[contains(@class, 'm-card-user__email')]")).getText();
    }
}
