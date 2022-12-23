package pages.selenide;

import com.codeborne.selenide.Condition;
import ru.yandex.qatools.htmlelements.element.Image;
import ru.yandex.qatools.htmlelements.element.TextBlock;

import static com.codeborne.selenide.Selenide.*;

/**
 * Класс, реализующий паттерн Page Object для страницы, показываемой после успешного входа в систему по логину и паролю.
 */
public class SuccessfulLoginPageSelenide {

    /**
     * xpath для поиска аватарки пользователя справа вверху.
     */
    private String xpathForUpperRightCornerAvatar = "//span[@class='m-topbar__userpic']//div[@class='avatarCover']";
    /**
     * xpath для поля логина
     */
    private String xpathForUserNameOnCard = "//span[contains(@class, 'm-card-user__name')]";
    /**
     * xpath для поля пароля
     */
    private String xpathForUserEmailOnCard = "//span[contains(@class, 'm-card-user__email')]";
    /**
     * Текстовое поле с имейлом пользователя на карточке после нажатия на аватарку справа сверху
     */
    private TextBlock emailBlock = new TextBlock($x(xpathForUserEmailOnCard));

    /**
     * @return возвращает имейл пользователя с карточки после нажатия на аватарку справа сверху
     */
    public String getEmailFromCard() {
        $x(xpathForUserEmailOnCard).shouldBe(Condition.visible);
        return emailBlock.getText();
    }

    /**
     * Текстовое поле с именем и фамилией пользователя на карточке после нажатия на аватарку справа сверху
     */
    private TextBlock nameBlock = new TextBlock($x(xpathForUserNameOnCard));

    /**
     * @return Возвращает имя и фамилию с карточки после нажатия на аватарку справа сверху
     */
    public String getNameFromCard() {
        $x(xpathForUserNameOnCard).shouldBe(Condition.visible);
        return nameBlock.getText();
    }

    /**
     * Аватарка справа сверху
     */
    private Image upperRightCornereAvatar = new Image($x(xpathForUpperRightCornerAvatar));

    /**
     * Кликает по аватарке в правом верхнем углу экрана.
     *
     * @return возвращает текущую страницу для возможности запуска других методов по цепочке.
     */
    public SuccessfulLoginPageSelenide clickUpperRightCornerAvatar() {
        upperRightCornereAvatar.click();
        return this;
    }
}
