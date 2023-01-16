package authorizedTests.calendar;

import authorizedTests.AuthorizedTestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.calendar.CalendarPO;
import pages.calendar.Day;
import pages.calendar.Snippet;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

/*
5 Выясни какие значения таймаутов стоят по-умолчанию для разных неявных ожиданий
(т.е. не только для поиска элементов, а так же ожидание загрузки страницы и т.п.)
Ответ:
для implicit wait 0 секунд, для PageLoad 300 секунд, для script 30 секунд

Выясни работает ли неявное ожидание при использовании метода findElements? Почему?
Ответ: Если будет найден хотя бы один элемент, то ждать появления остальных этот метод не будет, просто проверит
DOM-дерево, найдёт все элементы, поместит их в коллекцию и завершится. Если же ни одного элемента не будет найдено, то
implicitWait будет работать (ждать заданное время) до появления первого элемента.
 */
public class CalendarTest extends AuthorizedTestBase {
    public CalendarPO calendarPO;

    /**
     * Проверка существования выходных и рабочих дней в календаре
     */
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
                boolean finalFoundWorkDay = foundWorkDay;
                boolean finalFoundHoliday = foundHoliday;
                Assertions.assertAll(
                        () -> Assertions.assertTrue(finalFoundWorkDay, "Не найдены рабочие дни в месяце"),
                        () -> Assertions.assertTrue(finalFoundHoliday, "Не найдены выходные дни в месяце")
                );
                break;
            }
        }
    }

    /**
     * Открываем календарь и ждём исчезновения Progress bar.
     */
    @BeforeEach
    public void openCalendar() {
        calendarPO = open("https://tt.quality-lab.ru/calendar/", CalendarPO.class)
                .waitForCalendarToLoad();
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
        Assertions.assertAll(
                () -> assertEquals(LocalDateTime.now().getMonth(), calendarPO.getMonth(),
                        "Текущий месяц не совпадает с выведенным на календаре"),
                () -> assertEquals(Year.of(LocalDateTime.now().getYear()), calendarPO.getYear(),
                        "Текущий год не совпадает с выведенным на календаре")
        );
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
                Assertions.assertAll(
                        () -> Assertions.assertEquals("", snippet.getEvents().get(0)),
                        () -> Assertions.assertEquals("Выходной", snippet.getEvents().get(1))
                );
            } else if (day.isWorkDay() || day.isVacationDay()) {
                ArrayList<String> dayEvents = day.getEvents();
                ArrayList<String> snippetEvents = snippet.getEvents();
                for (int j = 0; j < dayEvents.size(); j++) {
                    Assertions.assertEquals(dayEvents.get(j), snippetEvents.get(j * 2));
                }
            }
        }
    }
}
