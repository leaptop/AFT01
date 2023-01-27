package authorizedTests.calendar;

import authorizedTests.AuthorizedTestBase;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.calendar.CalendarPO;
import pages.calendar.Day;
import pages.calendar.Snippet;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;

import static com.codeborne.selenide.Selenide.open;
import static properties.Properties.credentialsProperties;

/**
 * Запускать на tt-testing с Авто Пользователем
 *
 * @author Алексеев Степан
 * @date 25.12.2022
 */
public class CalendarTest extends AuthorizedTestBase {
    public CalendarPO calendarPO;

    /**
     * Проверка существования выходных и рабочих дней в календаре
     */
    @Step("Проверка существования выходных и рабочих дней в календаре")
    private void holidaysAndWorkDaysExistenceCheckNew() {
        boolean foundHoliday = false;
        boolean foundWorkDay = false;
        ArrayList<LocalDate> datesToCheck = calendarPO.getDates();
        for (int i = 0; i < datesToCheck.size(); i++) {
            calendarPO.clickDayOfThisMonth(datesToCheck.get(i));
            Day day = calendarPO.getDay(datesToCheck.get(i));
            if (day.isHoliday()) {
                foundHoliday = true;
            } else if (day.isWorkDay()) {
                foundWorkDay = true;
            }
            if (foundHoliday && foundWorkDay) {
                break;
            }
        }
        boolean finalFoundWorkDay = foundWorkDay;
        boolean finalFoundHoliday = foundHoliday;
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(finalFoundWorkDay, "Не найдены рабочие дни в месяце");
        soft.assertTrue(finalFoundHoliday, "Не найдены выходные дни в месяце");
        soft.assertAll();
    }

    /**
     * Открываем календарь и ждём появления и исчезновения Progress bar.
     */
    @BeforeMethod
    public void openCalendar() {
        calendarPO = open(credentialsProperties.urlCalendar(), CalendarPO.class);
    }

    /**
     * 1 Сценарий: проверка текущего месяца
     * Проверить:
     * месяц и год совпадают с текущими
     * в месяце есть рабочие дни (зеленые)
     * в месяце есть выходные (визуально пустые, но внутри есть плашка аналогично рабочим дням, только белая)
     */
    @Test
    public void checkCurrentMonthAndYear() {
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(LocalDateTime.now().getMonth(), calendarPO.getMonth(),
                "Текущий месяц не совпадает с выведенным на календаре");
        soft.assertEquals(Year.of(LocalDateTime.now().getYear()).getValue(), calendarPO.getYear().getValue(),
                "Текущий год не совпадает с выведенным на календаре");
        soft.assertAll();
        holidaysAndWorkDaysExistenceCheckNew();
    }

    /**
     * 2 Сценарий: проверка переключения месяца
     * Выбрать следующий месяц и нажать кнопку “Применить”
     * Проверить:
     * в месяце есть рабочие дни (зеленые)
     * в месяце есть выходные (визуально пустые, но внутри есть плашка аналогично рабочим дням, только белая)
     */
    @Test
    public void checkMonthSwitch() {
        calendarPO.chooseNextMonth();
        holidaysAndWorkDaysExistenceCheckNew();
    }

    /**
     * 3 Сценарий: проверка графика другого сотрудника:
     * Выбрать любого другого сотрудника (в тест-методе использовать фиксированную фамилию) и нажать кнопку “Применить”
     * Проверить:
     * в месяце есть рабочие дни (зеленые)
     * в месяце есть выходные (визуально пустые, но внутри есть плашка аналогично рабочим дням, только белая)
     */
    @Test
    public void checkOtherEmployee() {
        calendarPO.chooseEmployee("Якина");
        holidaysAndWorkDaysExistenceCheckNew();
    }

    /**
     * 4 Сценарий: проверка переключения бокового сниппета
     * Реализуй при помощи неявных ожиданий
     * Для каждого дня в календаре выполнить:
     * Клик по дню в календаре
     * Проверить что информация в боковом снипете совпадает с информацией в дне
     */
    @Test
    public void checkSideSnippetSwitchNew() {
        ArrayList<LocalDate> datesToCheck = calendarPO.getDates();
        for (int i = 0; i < datesToCheck.size(); i++) {
            calendarPO.clickDayOfThisMonth(datesToCheck.get(i));
            Day day = calendarPO.getDay(datesToCheck.get(i));
            Snippet snippet = calendarPO.getSnippet();
            if (day.isHoliday()) {
                SoftAssert soft = new SoftAssert();
                soft.assertEquals("", snippet.getEvents().get(0));
                soft.assertEquals("Выходной", snippet.getEvents().get(1));
                soft.assertAll();
            } else if (day.isWorkDay() || day.isVacationDay()) {
                ArrayList<String> dayEvents = day.getEvents();
                ArrayList<String> snippetEvents = snippet.getEvents();
                for (int j = 0; j < dayEvents.size(); j++) {
                    Assert.assertEquals(dayEvents.get(j), snippetEvents.get(j * 2));
                }
            }
        }
    }
}
