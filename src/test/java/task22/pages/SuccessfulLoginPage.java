package task22.pages;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.Image;
import ru.yandex.qatools.htmlelements.element.TextBlock;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.actions;

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
    private String xpathForRightMenuCalendarButton =
            "//li[@class='m-menu__item m-menu__item--submenu']/a/i[@class='m-menu__link-icon flaticon-calendar-2']";
    private String xpathForMainMenuButton =
            "//a[@id='m_aside_left_minimize_toggle']";

    private TextBlock emailBlock = new TextBlock($x(xpathForUserEmailOnCard));
    /**
     * Текстовое поле с именем и фамилией пользователя на карточке после нажатия на аватарку справа сверху
     */
    private TextBlock nameBlock = new TextBlock($x(xpathForUserNameOnCard));
    /**
     * Аватарка справа сверху
     */
    private Image upperRightCornereAvatar = new Image($x(xpathForUpperRightCornerAvatar));

    private Button rightMenuCalendarButton = new Button($x(xpathForMainMenuButton));

    @Step
    public SuccessfulLoginPage hoverOnMenuItemNamed(String name) throws InterruptedException {
        $x(xpathForMainMenuButton).hover();
//        SelenideElement element = $x(String.format(//"//ul[@class='m-menu__subnav']" +
//                "//span[text()='%s']", name));
        actions().moveByOffset(0, 300)
                .perform();
        actions().moveToElement($x("//span[@class='m-menu__link-text' and text()='Графики работы']"))
                .perform();
        //  actions().moveToElement(element).click(element).perform();
        $x(String.format(//"//ul[@class='m-menu__subnav']" +
                "//span[text()='%s']", name))
                //.shouldBe(Condition.visible)
                .hover();
        return this;
    }

    @Step("Кликаем на кнопку главного меню")
    public SuccessfulLoginPage clickMainMenuButton() {
        $x(xpathForMainMenuButton)
                .shouldBe(Condition.visible)
                .click();
        return this;
    }

    /**
     * @return возвращает текстовый блок с имейлом пользователя с карточки после нажатия на аватарку справа сверху
     */
    @Step("Получаем объект с текстом имейла")
    public TextBlock getEmailTextBlock() {
        $x(xpathForUserEmailOnCard).shouldBe(Condition.visible);
        return emailBlock;
    }

    /**
     * @return Возвращает текстовый блок имени и фамилии с карточки после нажатия на аватарку справа сверху
     */
    @Step("Получаем объект с текстом имени")
    public TextBlock getNameTextBlock() {
        $x(xpathForUserNameOnCard).shouldBe(Condition.visible);
        return nameBlock;
    }

    /**
     * Кликает по аватарке в правом верхнем углу экрана.
     *
     * @return возвращает текущую страницу для возможности запуска других методов по цепочке.
     */
    @Step("Кликаем по аватарке справа сверху")
    public SuccessfulLoginPage clickUpperRightCornerAvatar() {
        upperRightCornereAvatar.click();
        return this;
    }
}
