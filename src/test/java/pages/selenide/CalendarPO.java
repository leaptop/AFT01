package pages.selenide;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.fail;

/**
 * @author Алексеев Степан
 * @date 23.12.2022
 */
public class CalendarPO {
    /**
     * Плашка дня с событием (зелёное событие работы)
     */
    private String xpathForDefaultDay =
            "//td[@class='fc-event-container']/a[contains(@class, 'schedule-badge--default schedule-badge')]";
    /**
     * Плашка без событий
     */
    private String xpathForNoEventDay =
            "//td[@class='fc-event-container']/a[contains(@class,'schedule-badge--no-event schedule-badge')]";
    private String xpathForHeadersOfAllDays =
            "//td[contains(@class,'fc-day-top') and not (contains(@class,'fc-other-month'))]";
    /**
     * Кнопка "Применить"
     */
    private SelenideElement applyButton = $x("//button[text()='Применить']");
    /**
     * Кнопка переключения по годам назад
     */
    private SelenideElement buttonSwitchingYearsPrev =
            $x("//div[@class='datepicker-months']//th[@class='prev']");
    /**
     * Кнопка переключения по годам вперёд
     */
    private SelenideElement buttonSwitchingYearsNext =
            $x("//div[@class='datepicker-months']//th[@class='next']");
    /**
     * Кнопка для открытия выпадающего меню выбора даты
     */
    private SelenideElement buttonForDateChoice =
            $x("//div[@class='input-group date filter_input_date']//i");

    public CalendarPO() {

    }

    /**
     * XXX - код неправильный, но работает
     * Проверяет взаимодействие календаря и сниппета справа сверху.
     */
    @Step("Проверка взаимодействия календаря и сниппета справа сверху")
    public void checkCalendarSnippetInteraction() {
        ElementsCollection workDaysPlanks = $$x(xpathForDefaultDay);
        for (int i = 0; i < workDaysPlanks.size(); i++) {
            String info = workDaysPlanks.get(i).$x("./div/span").getText();
            workDaysPlanks.get(i).click();
            ElementsCollection snippetHoursSet = $$x(
                    "//div[@class='schedule-badges horizontal-split']/div[@class='render-badge']/span[1]");
            for (int j = 0; j < snippetHoursSet.size(); j++) {
                if (snippetHoursSet.get(j).getText().equals(info)) {
                    break;
                }
                if (j == (snippetHoursSet.size() - 1))
                    fail(String.format("В сниппете справа сверху текста \"%s\" нет", info));
            }
        }
        ElementsCollection holidays = $$x(xpathForNoEventDay);
        for (int i = 0; i < holidays.size(); i++) {
            holidays.get(i).click();
            if ($x("//div[@class='schedule-badges horizontal-split']/div[@class='render-badge']/span[2]")
                    .getText().equals("Выходной")) {
                continue;
            } else {
                fail("В сниппете справа сверху нет слова \"Выходной\"");
            }
        }
        ElementsCollection dateHeaders = $$x(xpathForHeadersOfAllDays);
        for (int i = 0; i < dateHeaders.size(); i++) {
            dateHeaders.get(i).click();
            StringBuilder st = new StringBuilder();
            String dateToCheck = dateHeaders.get(i).getAttribute("data-date");
            String formattedDateToCheck = "";
            formattedDateToCheck += dateToCheck.substring(8);
            formattedDateToCheck += ".";
            formattedDateToCheck += dateToCheck.substring(5, 7);
            formattedDateToCheck += ".";
            formattedDateToCheck += dateToCheck.substring(2, 4);
            if (formattedDateToCheck
                    .equals($x("//h3[@class='m-portlet__head-text right_panel_name']").getText())) {
                continue;
            } else {
                fail(String.format("В сниппете справа сверху дата должна быть" +
                                " %s, а фактически %s", formattedDateToCheck,
                        $x("//h3[@class='m-portlet__head-text right_panel_name']").getText()));
            }
        }
    }

    /**
     * @param employee часть имени или фамилии сотрудника для поиска
     * @return возвращает CalendarPO для вызовов по цепочке
     */
    @Step("Выбор сотрудника из списка, имя/фамилия которого " +
            "содержат \"{employee}\"")
    public CalendarPO chooseEmployee(String employee) {
        $x(String.format("//select[@name='filter-user-id']/option[contains(text(),'%s')]",
                employee)).click();
        applyButton.click();
        waitForCalendarToLoad();
        return this;
    }

    /**
     * Выбирает месяц и год в календаре
     *
     * @param neededDate дата в формате Mmm YYYY, например Июн 2023
     */
    @Step("Выбирает месяц и год в календаре: {neededDate}")
    public CalendarPO chooseMonthAndYear(String neededDate) {
        buttonForDateChoice.click();
        int neededYear =
                Integer.parseInt(neededDate.substring(
                        neededDate.length() - 4));
        int currentYear =
                Integer.parseInt(getCurrentMonthAndYear().substring(
                        getCurrentMonthAndYear().length() - 4));
        int diffInYears = neededYear - currentYear;
        if (diffInYears > 0) {
            for (int i = 0; i < diffInYears; i++) {
                buttonSwitchingYearsNext.click();
            }
        } else if (diffInYears < 0) {
            for (int i = 0; i < Math.abs(diffInYears); i++) {
                buttonSwitchingYearsPrev.click();
            }
        }
        getMonthButton(neededDate.substring(0, 3)).click();
        applyButton.click();
        return this;
    }


    /**
     * Выбор месяца в выпадающем меню по названию в виде "Янв"
     *
     * @param mon указание месяца в виде "Янв", "Мар" и т.д.
     * @return
     */
    @Step("Получает кнопку месяца с названием \"{mon}\"")
    private SelenideElement getMonthButton(String mon) {
        return $x(String.format(
                "//div[@class='datepicker-months']//span[contains(@class,'month') and text()='%s']",
                mon));
    }

    /**
     * Ждём появления сообщения о загрузке. Потом ждём его исчезновения.
     *
     * @return возвращает текущую страницу для продолжения вызовов методов по цепочке
     */
    @Step("Ждёт загрузку календаря")
    public CalendarPO waitForCalendarToLoad() {
        $x("//span[contains(@class, 'btn-primary m-loader')]")
                .should(Condition.disappear, Duration.ofSeconds(30));
        return this;
    }

    /**
     * @return Возвращает месяц и год, отображённые на календаре в виде строки.
     * Например: "Декабрь 2022";
     */
    @Step("Получает текущие месяц и год, отображённые в календаре")
    public String getCurrentMonthAndYear() {
        return $x("//input[@name='filter-date']").getValue();
    }

    /**
     * @return Возвращает все зелёные плашки рабочих дней.
     */
    @Step("Извлекает все зелёные плашки рабочих дней в коллекцию для " +
            "дальнейшей обработки.")
    public ElementsCollection getWorkDayLInks() {
        return $$x(xpathForDefaultDay);
    }

    /**
     * @return Возвращает плашки выходных дней.
     */
    @Step("Извлекает все плашки выходных дней в коллекцию для дальнейшей " +
            "обработки")
    public ElementsCollection getHolidayLinks() {
        return $$x(xpathForNoEventDay);
    }
}
