package authorizedTests;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import pages.selenide.CalendarPO;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.*;

/*
Создать тест-класс CalendarTest и реализовать в нем тест-методы по следующим сценариям:
Предусловие (для каждого сценария одинаковое):
пользователь авторизован в системе
открыта страница “Графики работы” (https://tt-develop.quality-lab.ru/calendar/)
Дождаться загрузки календаря на текущий месяц

1Сценарий: проверка текущего месяца
Проверить:
месяц и год совпадают с текущими
в месяце есть рабочие дни (зеленые)
в месяце есть выходные (визуально пустые, но внутри есть плашка аналогично рабочим дням, только белая)

2Сценарий: проверка переключения месяца
Выбрать следующий месяц и нажать кнопку “Применить”
Проверить:
в месяце есть рабочие дни (зеленые)
в месяце есть выходные (визуально пустые, но внутри есть плашка аналогично рабочим дням, только белая)

3Сценарий: проверка графика другого сотрудника:
Выбрать любого другого сотрудника (в тест-методе использовать фиксированную фамилию) и нажать кнопку “Применить”
Проверить:
в месяце есть рабочие дни (зеленые)
в месяце есть выходные (визуально пустые, но внутри есть плашка аналогично рабочим дням, только белая)

4Сценарий: проверка переключения бокового сниппета
Реализуй при помощи неявных ожиданий
Для каждого дня в календаре выполнить:
Клик по дню в календаре
Проверить что информация в боковом снипете совпадает с информацией в дне

5 Выясни какие значения таймаутов стоят по-умолчанию для разных неявных ожиданий
(т.е. не только для поиска элементов, а так же ожидание загрузки страницы и т.п.)

Выясни работает ли неявное ожидание при использовании метода findElements? Почему?


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
//    /**
//     * Обнуляем ссылку, чтобы в каждом тесте работать с новым объектом.
//     *
//     */
//    @AfterEach
//    public void afterEach(){
//        calendarPO = null;
//    }

    /**
     * 1Сценарий: проверка текущего месяца
     * Проверить:
     * месяц и год совпадают с текущими
     * в месяце есть рабочие дни (зеленые)
     * в месяце есть выходные (визуально пустые, но внутри есть плашка аналогично рабочим дням, только белая)
     */
    @Test
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
     * 2Сценарий: проверка переключения месяца
     * Выбрать следующий месяц и нажать кнопку “Применить”
     * Проверить:
     * в месяце есть рабочие дни (зеленые)
     * в месяце есть выходные (визуально пустые, но внутри есть плашка аналогично рабочим дням, только белая)
     */
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
     * 3Сценарий: проверка графика другого сотрудника:
     * Выбрать любого другого сотрудника (в тест-методе использовать фиксированную фамилию) и нажать кнопку “Применить”
     * Проверить:
     * в месяце есть рабочие дни (зеленые)
     * в месяце есть выходные (визуально пустые, но внутри есть плашка аналогично рабочим дням, только белая)
     */
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
     *
     *
     */
    @Test
    public void checkSideSnippetSwitch() {
        calendarPO.checkCalendarSnippetInteraction();
        String str = "f";
    }
}
