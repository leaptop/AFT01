package authorizedTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.selenide.CalendarPO;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.open;

/*
5 Выясни какие значения таймаутов стоят по-умолчанию для разных неявных ожиданий
(т.е. не только для поиска элементов, а так же ожидание загрузки страницы и т.п.)
Ответ:
для implicit wait 0 секунд, для PageLoad 300 секунд, для script 30 секунд

Выясни работает ли неявное ожидание при использовании метода findElements? Почему?
Ответ: Если будет найден хотя бы один элемент, то ждать появления остальных этот метод не будет, просто проверит
DOM-дерево, найдёт все элементы, поместит их в коллекцию и завершится. Если же ни одного элемента не будет найдено, то
implicitWait будет работать (ждать заданное время) до появления первого элемента. */

/**
 * @author Алексеев Степан
 * @date 25.12.2022
 */
public class CalendarTest extends AuthorizedTestBaseSelenide {
    public CalendarPO calendarPO;

    /**
     * Открываем календарь и ждём появления сообщения о загрузке. Потом ждём его исчезновения.
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
    @DisplayName("1 Сценарий: проверка текущего месяца")
    @Test()
    public void checkCurrentMonthAndYear() {
        Date date = Date.from(Instant.now());
        SimpleDateFormat newDateFormat = new SimpleDateFormat("LLLL yyyy", Locale.getDefault());
        String result = newDateFormat.format(date);
        Assertions.assertAll(
                () -> Assertions.assertEquals(result, calendarPO.getCurrentMonthAndYear(),
                        "Текущие месяц и год не совпадают с выведенными на сайте"),
                () -> Assertions.assertTrue(calendarPO.getWorkDayLInks().size() > 0,
                        "Не найдены рабочие дни в месяце"),
                () -> Assertions.assertTrue(calendarPO.getHolidayLinks().size() > 0,
                        "Не найдены выходные дни в месяце")
        );
    }

    /**
     * 2 Сценарий: проверка переключения месяца
     * Выбрать следующий месяц и нажать кнопку “Применить”
     * Проверить:
     * в месяце есть рабочие дни (зеленые)
     * в месяце есть выходные (визуально пустые, но внутри есть плашка аналогично рабочим дням, только белая)
     */
    @DisplayName("2 Сценарий: проверка переключения месяца")
    @Test
    public void checkMonthSwitch() {
        calendarPO.chooseMonthAndYear("Янв 2023");
        calendarPO.waitForCalendarToLoad();
        Assertions.assertAll(
                () -> Assertions.assertTrue(calendarPO.getWorkDayLInks().size() > 0,
                        "Не найдены рабочие дни в месяце"),
                () -> Assertions.assertTrue(calendarPO.getHolidayLinks().size() > 0,
                        "Не найдены выходные дни в месяце")
        );
    }

    /**
     * 3 Сценарий: проверка графика другого сотрудника:
     * Выбрать любого другого сотрудника (в тест-методе использовать фиксированную фамилию) и нажать кнопку “Применить”
     * Проверить:
     * в месяце есть рабочие дни (зеленые)
     * в месяце есть выходные (визуально пустые, но внутри есть плашка аналогично рабочим дням, только белая)
     */
    @DisplayName("3 Сценарий: проверка графика другого сотрудника")
    @Test
    public void checkOtherEmployee() {
        calendarPO.chooseEmployee("Якина");
        Assertions.assertAll(
                () -> Assertions.assertTrue(calendarPO.getWorkDayLInks().size() > 0,
                        "Не найдены рабочие дни в месяце"),
                () -> Assertions.assertTrue(calendarPO.getHolidayLinks().size() > 0,
                        "Не найдены выходные дни в месяце")
        );
    }

    /**
     * 4 Сценарий: проверка переключения бокового сниппета
     * Реализуй при помощи неявных ожиданий
     * Для каждого дня в календаре выполнить:
     * Клик по дню в календаре
     * Проверить что информация в боковом снипете совпадает с информацией в дне
     * <p>
     * Для этого нужно хранить таблицу в виде вроде List<Map<int, String>>,т.к. элементы одного дня хранятся в разных
     * строках. Непонятно как их организовать...Да это и не нужно... Вроде... Хотя дату надо тоже проверить
     * наверное... Так что, вероятно, придётся хранить очень структурированно...
     */
    @DisplayName("4 Сценарий: проверка переключения бокового сниппета")
    @Test
    public void checkSideSnippetSwitch() {
        calendarPO.checkCalendarSnippetInteraction();
    }
}
