package task23control.authorizedTests.calendar;

import org.testng.annotations.Test;
import task23control.authorizedTests.AuthorizedTestBase;
import task23control.pages.calendar.CalendarPO;

import static com.codeborne.selenide.Selenide.open;
import static properties.Properties.credentialsProperties;

/**
 * Предусловие:  Авторизоваться под пользователем через АПИ
 * Шаги:
 * 1 Открыть страницу /calendar/
 * 2 Перейти в раздел Отчеты за сегодня через боковое меню (слева Отчеты - Отчеты за сегодня).
 *
 * 3 Нажать на нужное настроение
 * 4 Проверить наличие всплывающего окна с надписью: “Вы хотите залогировать больше или меньше 8 часов, которые по
 * графику запланированы у вас на сегодня. “
 * 5 Нажать Отмена во всплывающем окне
 * <p>
 * Автотест можешь сделать в том же проекте что и остальную песочку, но чтобы потренироваться советую создать отдельный.
 * Автотест необходимо параметризовать: использовать двух пользователей  и три разных настроения для каждого пользователя (т.е. всего будет 6 тестов)
 * Необходимо прикрутить аллюр-отчет и реализовать параллельный запуск тестов
 * Стек: Java (выше 8), Selenide и TestNG/JUnit на твой выбор, но с условием: выбираешь тот тестовый фреймворк, который тебе дается сложнее или который хуже знаешь
 *
 * @author Алексеев Степан
 * @date 30.01.2023
 */
public class ReportEditTest extends AuthorizedTestBase {
    @Test

    void test() {
        open(credentialsProperties.urlCalendar(), CalendarPO.class);
        CalendarPO calendarPO = new CalendarPO();
        calendarPO.clickSubmenuOfReportMenuNamed("Отчет за сегодня");


    }
}
