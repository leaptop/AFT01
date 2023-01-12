package authorizedTests.calendar;

import authorizedTests.AuthorizedTestBase;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.calendar.CalendarPO;
import pages.calendar.Day;
import pages.calendar.Snippet;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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
    private void holidaysAndWorkDaysExistenceCheck() {
        ArrayList<Day> days = calendarPO.fillTheCalendar();
        boolean foundHoliday = false;
        boolean foundWorkDay = false;
        for (Day day : days) {
            if (day.isWorkDay()) {
                foundWorkDay = true;
            } else if (day.isHoliday()) {
                foundHoliday = true;
            }
            if (foundHoliday && foundWorkDay) {
                break;
            }
        }
        boolean finalFoundWorkDay = foundWorkDay;
        boolean finalFoundHoliday = foundHoliday;
        Assertions.assertAll(
                () -> Assertions.assertTrue(finalFoundWorkDay, "Не найдены рабочие дни в месяце"),
                () -> Assertions.assertTrue(finalFoundHoliday, "Не найдены выходные дни в месяце")
        );
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
        holidaysAndWorkDaysExistenceCheck();
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
        holidaysAndWorkDaysExistenceCheck();
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
        holidaysAndWorkDaysExistenceCheck();
    }

    /**
     * 4 Сценарий: проверка переключения бокового сниппета
     * Реализуй при помощи неявных ожиданий
     * Для каждого дня в календаре выполнить:
     * Клик по дню в календаре
     * Проверить что информация в боковом снипете совпадает с информацией в дне
     */
    @Test
    public void checkSideSnippetSwitch() {
        WebDriverRunner.driver().getWebDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        ArrayList<Day> days = calendarPO.fillTheCalendar();
        for (Day day : days) {
            if (day.isBelongsToThisMonth()) {
                calendarPO.clickDayOfThisMonthByNumber(Integer.parseInt(day.getFcDayNumber()));
                Snippet snippet = calendarPO.fillSnippet();
                if (!day.getDate().equals(snippet.getDate())) {//проверка даты дня
                    fail(String.format("В сниппете справа сверху дата должна быть %s, а фактически %s"
                                    , day.getDate().toString()
                                    , snippet.getDate().toString()
                            )
                    );
                }
                if (day.getEvents().get(0).equals("")) {//проверка выходных
                    assertEquals("Выходной",
                            snippet.getEvents().get(1), String.format(
                                    "В сниппете неверная информация о дне %s, он д.б. подписан как \"Выходной\".",
                                    day.getDate().toString()));
                } else {//проверка рабочих дней:
                    for (int i = 0; i < day.getEvents().size(); i++) {
                        assertEquals(day.getEvents().get(i), snippet.getEvents().get(i * 2),
                                "В сниппете неверная информация о дне " + day.getDate().toString());
                    }
                }
            }
        }
    }
}
