package task22.authorizedTests.calendar;

import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.function.Executable;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import task22.authorizedTests.AuthorizedTestBase;
import task22.pages.SuccessfulLoginPage;
import task22.pages.calendar.CalendarChangeSnippetFormPO;
import task22.pages.calendar.CalendarPO;

import java.util.Random;

import static com.codeborne.selenide.Selenide.open;
import static properties.Properties.credentialsProperties;


public class CalendarTest extends AuthorizedTestBase {
    public CalendarPO calendarPO;

    /**
     * Повторяет вызов кода, пока не выполнится выброс исключения
     *
     * @param ex       код для вызова
     * @param numTimes число попыток
     */
    public void repeatNTimes(Executable ex, int numTimes) {
        for (int i = 1; i < numTimes; i++) {
            try {
                ex.execute();
            } catch (Throwable e) {
                e.printStackTrace();
                System.out.println(String.format("inside catching of Throwable, i = %d", i));
                break;
            }
        }
    }
    /**
     * Проверяю повторный вызов кода до выброса исключения
     */
    @Test
    void testooo(){
        repeatNTimes(
                ()->{
                    Random random = new Random();
                    int randomNumber = random.nextInt(30);
                    System.out.println("randomNumber = " + randomNumber);
                    if (!(randomNumber % 2 == 0 || randomNumber % 3 == 0)){
                        System.out.println("throwing oshibonka");
                        throw new RuntimeException("oshibonka ");
                    }
                }, 50
        );

    }

    /**
     * Проверить несколько раз основной тест, не будет ли проблем
     */
    @Test
    void testNTimes() {
        repeatNTimes(
                () -> {
                    authorizeViaHTTP();
                    String imageName = "pict.png";
                    open(credentialsProperties.urlReportEdit(), SuccessfulLoginPage.class);
                    SuccessfulLoginPage successfulLoginPage = new SuccessfulLoginPage();
                    successfulLoginPage.hoverOnMenuItemNamed("calendar-2").clickOnSubMenuItemNamed("Графики работы");
                    calendarPO = new CalendarPO();
                    calendarPO.clickDayOfThisMonth(13);//убрать перед коммитом
                    calendarPO.clickChangeSnippetButton();
                    CalendarChangeSnippetFormPO modalForm = new CalendarChangeSnippetFormPO();
                    String randomApproverName = modalForm
                            .clickChangeApprovingPeople()
                            .chooseRandomApprovingPerson();
                    modalForm
                            .uploadFileViaSelenide(imageName)
                            .saveScheduleButtonClick();
                    calendarPO.checkIfConfirmarionOfScheduleChangeAppeared();
                    calendarPO.clickChangeSnippetButton();
                    String existingApproverName = modalForm.getChosenApproverName();
                    SoftAssert soft = new SoftAssert();
                    soft.assertEquals(randomApproverName, existingApproverName, "Имена выбранного произвольного согласующего и "
                            + "находящегося на форме после повторного открытия не совпадают");
                    String uploadedImageName = modalForm.getUploadedImageName();
                    soft.assertEquals(imageName, uploadedImageName, "Имена загруженных файлов не совпадают");
                    int width = modalForm.getUploadedImageWidth();
                    int height = modalForm.getUploadedImageHeight();
                    soft.assertTrue(width > 0, "Ширина картинки меньше или равна нулю");
                    soft.assertTrue(height > 0, "Высота картинки меньше или равна нулю");
                    soft.assertAll();
                    WebDriverRunner.closeWebDriver();
                }
                , 50);
    }

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
    @Test
    void test() {
        String imageName = "pict.png";
        open(credentialsProperties.urlReportEdit(), SuccessfulLoginPage.class);
        SuccessfulLoginPage successfulLoginPage = new SuccessfulLoginPage();
        successfulLoginPage.hoverOnMenuItemNamed("calendar-2").clickOnSubMenuItemNamed("Графики работы");
        calendarPO = new CalendarPO();
        calendarPO.clickDayOfThisMonth(13);
        calendarPO.waitForCalendarToLoad(); //убрать перед коммитом
        calendarPO.clickChangeSnippetButton();
        CalendarChangeSnippetFormPO modalForm = new CalendarChangeSnippetFormPO();
        String randomApproverName = modalForm
                .clickChangeApprovingPeople()
                .chooseRandomApprovingPerson();
        modalForm
                .uploadFileViaSelenide(imageName)
                .saveScheduleButtonClick();
        calendarPO.checkIfConfirmarionOfScheduleChangeAppeared();
        calendarPO.clickChangeSnippetButton();
        String existingApproverName = modalForm.getChosenApproverName();
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(randomApproverName, existingApproverName, "Имена выбранного произвольного согласующего и "
                + "находящегося на форме после повторного открытия не совпадают");
        String uploadedImageName = modalForm.getUploadedImageName();
        soft.assertEquals(imageName, uploadedImageName, "Имена загруженных файлов не совпадают");
        int width = modalForm.getUploadedImageWidth();
        int height = modalForm.getUploadedImageHeight();
        soft.assertTrue(width > 0, "Ширина картинки меньше или равна нулю");
        soft.assertTrue(height > 0, "Высота картинки меньше или равна нулю");
        soft.assertAll();
    }
}
