package pages;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import ru.yandex.qatools.htmlelements.element.Image;
import ru.yandex.qatools.htmlelements.element.TextBlock;

import static com.codeborne.selenide.Selenide.*;

/**
 * Класс, реализующий паттерн Page Object для страницы, показываемой после успешного входа в систему по логину и паролю.
 */
public class SuccessfulLoginPage {
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
     * Текстовое поле с именем и фамилией пользователя на карточке после нажатия на аватарку справа сверху
     */
    private TextBlock nameBlock = new TextBlock($x(xpathForUserNameOnCard));
    /**
     * Аватарка справа сверху
     */
    private Image upperRightCornereAvatar = new Image($x(xpathForUpperRightCornerAvatar));

    /**
     * @return возвращает текстовый блок с имейлом пользователя с карточки после нажатия на аватарку справа сверху
     */
    @Step
    public TextBlock getEmailTextBlock() {
        $x(xpathForUserEmailOnCard).shouldBe(Condition.visible);
        return emailBlock;
    }

    /**
     * @return Возвращает текстовый блок имени и фамилии с карточки после нажатия на аватарку справа сверху
     */
    @Step
    public TextBlock getNameTextBlock() {
        $x(xpathForUserNameOnCard).shouldBe(Condition.visible);
        return nameBlock;
    }

    /**
     * Кликает по аватарке в правом верхнем углу экрана.
     *
     * @return возвращает текущую страницу для возможности запуска других методов по цепочке.
     */
    @Step
    public SuccessfulLoginPage clickUpperRightCornerAvatar() {
        upperRightCornereAvatar.click();
        return this;
    }
}
