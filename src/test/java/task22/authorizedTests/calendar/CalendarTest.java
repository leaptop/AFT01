package task22.authorizedTests.calendar;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import task22.authorizedTests.AuthorizedTestBase;
import task22.pages.SuccessfulLoginPage;
import task22.pages.calendar.CalendarChangeSnippetFormPO;
import task22.pages.calendar.CalendarPO;

import static com.codeborne.selenide.Selenide.open;
import static properties.Properties.credentialsProperties;

/**
 * Запускать на tt-testing
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
 * <p>
 * g. Не меняя текущего рабочего дня, в боковом снипете нажать кнопку “Изменить”
 * h. Во всплывающем окне “Изменение графика работы” проверить:
 * I. С кем согласовано: совпадает с введенным на шаге 4
 * <p>
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

    @Test
    void test() {
        String imageName = "pict.png";
        open(credentialsProperties.urlReportEdit(), SuccessfulLoginPage.class);
        SuccessfulLoginPage slp = new SuccessfulLoginPage();
        slp.hoverOnMenuItemNumber(4).clickOnSubMenuItemNamed("Графики работы");
        calendarPO = new CalendarPO();
        calendarPO.clickChangeSnippetButton();
        CalendarChangeSnippetFormPO ccsfpo = new CalendarChangeSnippetFormPO();
        String randomApproverName = ccsfpo
                .clickChangeApprovingPeople()
                .chooseRandomApprovingPerson();
        ccsfpo
                .uploadFileViaSelenide(imageName)
                .saveScheduleButtonClick();
        calendarPO.checkIfConfirmarionOfScheduleChangeAppeared();
        calendarPO.clickChangeSnippetButton();
        String existingApproverName = ccsfpo.getChosenApproverName();
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(randomApproverName, existingApproverName, "Имена выбранного произвольного согласующего и "
                + "находящегося на форме после повторного открытия не совпадают");
        String uploadedImageName = ccsfpo.getUploadedImageName();
        soft.assertEquals(imageName, uploadedImageName, "Имена загруженных файлов не совпадают");
        int wi = ccsfpo.getUploadedImageWidth();
        int he = ccsfpo.getUploadedImageHeight();
        soft.assertTrue(wi > 0, "Ширина картинки меньше или равна нулю");
        soft.assertTrue(he > 0, "Высота картинки меньше или равна нулю");
        soft.assertAll();
    }
}
