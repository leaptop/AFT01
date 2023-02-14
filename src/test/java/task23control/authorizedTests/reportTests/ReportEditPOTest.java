package task23control.authorizedTests.reportTests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import task23control.authorizedTests.AuthorizedTestBase;
import task23control.pages.ReportEditPO;
import task23control.pages.SuccessfulLoginPage;

import static com.codeborne.selenide.Selenide.open;
import static properties.Properties.credentialsProperties;

/**
 * Предусловие:  Авторизоваться под пользователем через АПИ
 * Шаги:
 * 1 Открыть страницу /calendar/
 * 2 Перейти в раздел Отчеты за сегодня через боковое меню (слева Отчеты - Отчеты за сегодня).
 * <p>
 * 3 Нажать на нужное настроение
 * 4 Проверить наличие всплывающего окна с надписью: “Вы хотите залогировать больше или меньше 8 часов, которые по
 * графику запланированы у вас на сегодня. “
 * 5 Нажать Отмена во всплывающем окне
 * <p>
 * Автотест можешь сделать в том же проекте что и остальную песочку, но чтобы потренироваться советую создать отдельный.
 * Автотест необходимо параметризовать: использовать двух пользователей  и три разных настроения для каждого
 * пользователя (т.е. всего будет 6 тестов)
 * Необходимо прикрутить аллюр-отчет и реализовать параллельный запуск тестов
 * Стек: Java (выше 8), Selenide и TestNG/JUnit на твой выбор, но с условием: выбираешь тот тестовый фреймворк,
 * который тебе дается сложнее или который хуже знаешь
 *
 * @author Алексеев Степан
 * @date 30.01.2023
 */
public class ReportEditPOTest extends AuthorizedTestBase {
    @DataProvider(name = "emojis", parallel = true)
    public Object[][] returnParams() {
        return new Object[][]{
                {"Авто Пользователь", "12345678", ReportEditPO.Emo.inspired},
                {"Авто Пользователь", "12345678", ReportEditPO.Emo.sad},
                {"Авто Мяу", "12345", ReportEditPO.Emo.happy},
                {"Авто Пользователь", "12345678", ReportEditPO.Emo.angry},
                {"Авто Мяу", "12345", ReportEditPO.Emo.neutral},
                {"Авто Мяу", "12345", ReportEditPO.Emo.upset}
        };
    }

    @Test(description = "Проверка отчёта за день", dataProvider = "emojis")
    void test(String name, String pass, ReportEditPO.Emo emo) {
        authorizeViaHTTP(name, pass);
        open(credentialsProperties.urlCalendar(), SuccessfulLoginPage.class);
        SuccessfulLoginPage successfulLoginPage = new SuccessfulLoginPage();
        successfulLoginPage.clickReportForTodaySubmenuItem();
        ReportEditPO reportEditPO = new ReportEditPO();
        reportEditPO.clickEmoji(emo);
        reportEditPO.checkIfModal8hoursWindowAppeared();
        reportEditPO.clickCancelModal8hours();
    }
}
