package task23control.pages;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

/**
 * Класс, реализующий паттерн Page Object для страницы, показываемой после успешного входа в систему по логину и паролю.
 */
public class SuccessfulLoginPage {
    /**
     * Открывает доступ к подменю по имени вместо %s, например "Отчет за сегодня"
     */
    private String subMenuItem = "./ancestor::li//span[contains(text(),'%s')]";

    /**
     * Иконка кнопки меню с графиком. Открывает подменю с отчётами.
     */
    private String xpathForReportIconMenuItem =
            "//span[@class='m-menu__item-here']/following-sibling::i[contains(@class, 'flaticon-line-graph')]";

    /**
     * Наводит мышь на пункт меню слева с картинкой с графиком (Отчёты) и нажимает пункт подменю по имени
     *
     * @param submenuItem имя пункта подменю
     * @return
     */
    @Step("Наводит мышь на пункт меню \"Отчёты\", жмёт на {submenuItem}")
    private void clickSubmenuOfReportMenuNamed(String submenuItem) {
        $x(xpathForReportIconMenuItem).hover();
        $x(xpathForReportIconMenuItem).$x(String.format(subMenuItem, submenuItem)).hover().click();
    }

    /**
     * Жмёт пункт "Отчет за сегодня" всплывающего меню из пункта главного меню "Отчёты"
     *
     * @return
     */
    @Step("Жмёт пункт \"Отчет за сегодня\" всплывающего меню")
    public SuccessfulLoginPage clickReportForTodaySubmenuItem() {
        clickSubmenuOfReportMenuNamed("Отчет за сегодня");
        return this;
    }
}
