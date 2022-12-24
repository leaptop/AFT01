package authorizedTests;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import pages.selenide.CalendarPO;

import java.text.SimpleDateFormat;
import java.time.Instant;
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

Запустить новый тест-класс
Отправить изменения в репозиторий
Создать Merge (Pull) Request для вливания ветки  task8-9 в ветку master.
Сообщить куратору песочки о готовности задания к проверке
Самопроверка задания
Все тесты зеленые
CalendarTest наследует TestBase
В CalendarTest есть метод @BeforeEach который создает предусловия для тестов
В CalendarTest написано 4 тест-метода
В CalendarTest реализован вспомогательный метод для проверки наличия рабочих и выходных дней в графике
Реализован новый PageObject для страницы графиков работы.
Помимо методов работы с контролами в нем также содержится метод ожидания загрузки календаря
Реализован вспомогательный класс для авторизации

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
        calendarPO.chooseMonthAndYear("Июн 2023");
        calendarPO.waitForCalendarToLoad();
        Assertions.assertAll(
                () -> Assertions.assertTrue(calendarPO.getWorkDayLInks().size() > 0,
                        "Не найдены рабочие дни в месяце"),
                () -> Assertions.assertTrue(calendarPO.getHolidayLinks().size() > 0,
                        "Не найдены выходные дни в месяце")
        );
    }
}
