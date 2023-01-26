package task22.authorizedTests.calendar;

//import io.qameta.allure.selenide.AllureSelenide;

import org.testng.annotations.Test;
import task22.authorizedTests.AuthorizedTestBase;
import task22.pages.SuccessfulLoginPage;
import task22.pages.calendar.CalendarPO;

import static com.codeborne.selenide.Selenide.open;
import static properties.Properties.credentialsProperties;

/**
 * 1 Создай ветку task22
 * 2 Реализуй следующий сценарий:
 * a. Предусловие: пользователь авторизован
 * b. Открыть страницу “Графики работы” через боковое меню
 * c. Для текущего рабочего дня в боковом снипете нажать кнопку “Изменить”
 * d. Во всплывающем окне “Изменение графика работы” заполнить:
 * I. С кем согласовано: случайный пользователь из списка (именно случайны, а не первый/ последний или
 * фиксированный)
 * II. Файлы для согласования: загрузить 1 файл (картинку можешь выбрать сам)
 * e. Нажать кнопку “Сохранить”
 * f. Проверить что появилось сообщение “Расписание успешно изменено”
 * g. Не меняя текущего рабочего дня, в боковом снипете нажать кнопку “Изменить”
 * h. Во всплывающем окне “Изменение графика работы” проверить:
 * I. С кем согласовано: совпадает с введенным на шаге 4
 * a. Файлы для согласование:
 * I. содержит картинку. Картинка имеет ненулевой отображаемый размер
 * II. при наведении на картинку выводится название файла, добавленного на шаге 4 (достаточно проверить наличие
 * текста, наводить курсор и проверять отображение можно, но не обязательно)
 *
 * @author Алексеев Степан
 * @date 25.12.2022
 */
public class CalendarTest extends AuthorizedTestBase {
    public CalendarPO calendarPO;

//    @BeforeClass
//    static void setupAllureReports() {
//        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
//                .screenshots(true)
//                .savePageSource(false)
//        );
//    }

    @Test
    void testik() throws InterruptedException {
        open(credentialsProperties.urlReportEdit(), SuccessfulLoginPage.class);
        SuccessfulLoginPage slp = new SuccessfulLoginPage();
        slp.clickOnMenuItemNamed(4, "Графики работы");
        calendarPO = new CalendarPO();
        calendarPO.clickChangeSnippetButton();
        String str = "";
    }

}
