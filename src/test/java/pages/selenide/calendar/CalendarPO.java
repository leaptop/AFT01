package pages.selenide.calendar;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.fail;

public class CalendarPO {
    public CalendarPO() {

    }

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
    private String xpathForHeadersOfAllDays = "//td[contains(@class,'fc-day-top') and not (contains(@class,'fc-other-month'))]";
    /**
     * Здесь хранятся все дни из календаря
     */
    public ArrayList<Day> days;
    /**
     * Здесь хранится текущий сниппет
     */
    public Snippet snippet;
    /**
     * xpath для даты в сниппете
     * <p>
     * Для реализации задания по использованию неявных ожиданий элементы
     * * сниппета д.б. использованы через Selenium, не через  Selenide, т.к. в
     * * Selenide нет неявных ожиданий.
     */
    private SelenideElement snippetDate = $x(
            "//div[contains(@class,'schedule-right-panel')]//h3");
    /**
     * Элементы с текстом сниппета
     * <p>
     * Для реализации задания 9.5 по использованию неявных ожиданий элементы
     * сниппета д.б. использованы через Selenium, не через  Selenide, т.к. в
     * Selenide нет неявных ожиданий.
     */
    ElementsCollection snippetEvents = $$x(
            "//div[contains(@class,'schedule-right-panel')" +
                    "]//div[@class='render-badge']/span");


    /**
     * Выбор сотрудника из списка
     */
    public CalendarPO chooseEmployee(String employee) {
        $x(String.format("//select[@name='filter-user-id']/option[contains(text(),'%s')]", employee)).click();
        applyButton.click();
        waitForCalendarToLoad();
        return this;
    }

    /**
     * Выбирает месяц и год в календаре
     *
     * @param neededDate дата в формате Mmm YYYY, например Июн 2023
     */
    public CalendarPO chooseMonthAndYear(String neededDate) {
        buttonForDateChoice.click();
        int neededYear =
                Integer.parseInt(neededDate.substring(neededDate.length() - 4));
        int currentYear =
                Integer.parseInt(getCurrentMonthAndYear().substring(getCurrentMonthAndYear().length() - 4));
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
        waitForCalendarToLoad();
        return this;
    }

    /**
     * Выбор месяца в выпадающем меню по названию в виде "Янв"
     *
     * @param mon указание месяца в виде "Янв", "Мар" и т.д.
     * @return
     */
    private SelenideElement getMonthButton(String mon) {
        return $x(String.format("//div[@class='datepicker-months']//span[contains(@class,'month') and text()='%s']", mon));
    }

    /**
     * Ждём появления сообщения о загрузке. Потом ждём его исчезновения.
     *
     * @return
     */
    public CalendarPO waitForCalendarToLoad() {
        $x("//span[contains(@class, 'btn-primary m-loader')]").should(Condition.disappear, Duration.ofSeconds(30));
        return this;
    }

    /**
     * @return Возвращает месяц и год, отображённые на календаре в виде строки. Например: "Декабрь 2022";
     */
    public String getCurrentMonthAndYear() {
        return $x("//input[@name='filter-date']").getValue();
    }

    /**
     * @return Возвращает все зелёные плашки рабочих дней.
     */
    public ElementsCollection getWorkDayLInks() {
        return $$x(xpathForDefaultDay);
    }

    /**
     * @return Возвращает плашки выходных дней.
     */
    public ElementsCollection getHolidayLinks() {
        return $$x(xpathForNoEventDay);
    }

    /**
     * Возвращает события расписанные в сниппете через Selenium.
     */
    public List<WebElement> getSnippetEvents() {
        return WebDriverRunner.driver().getWebDriver().findElements(By.xpath(
                "//div[contains(@class,'schedule-right-panel')" +
                        "]//div[@class='render-badge']/span"));
    }

    /**
     * Возвращает дату из сниппета
     */
    public String getSnippetDate() {
        return WebDriverRunner.driver().getWebDriver().findElement(By.xpath(
                        "//div[contains(@class,'schedule-right-panel')]//h3"))
                .getText();
    }

    /**
     * Здесь получаем содержимое сниппета и заносим его в переменную snippet.
     */
    public void fillSnippet() {
        List<WebElement> listEv = getSnippetEvents();
        ArrayList<String> snippetEventsString = new ArrayList<>();
        for (int i = 0; i < listEv.size(); i++) {
            snippetEventsString.add(listEv.get(i).getText());
        }
        snippet = new Snippet(getSnippetDate(), snippetEventsString);
    }

    /**
     * Данный метод сохраняет всю информацию о днях из календаря в виде
     * массива days.
     */
    public void fillTheCalendar() {
        days = new ArrayList<>();
        ElementsCollection aw = $$x(
                "//div[@class='fc-row fc-week fc-widget-content']"
        );//Недели. Обычно их 6 в этом календаре.
        boolean foundFirst = false;//найден первый день месяца
        boolean belongsToM = false;//день принадлежит текущему месяцу
        for (int w = 0; w < aw.size(); w++) {//Проход по неделям
            int n = 1;//индекс номера дня для второго и последующих рядов. Он
            // нужен на случай появления выходного дня.
            SelenideElement row1 = aw.get(w).$x(//первый ряд tr таблицы
                    "./div[@class='fc-content-skeleton']//tbody/tr[1]");
            List<String> fstRow = new ArrayList<>();//тексты td первого ряда
            ElementsCollection awh = aw.get(w).$$x(".//td[contains(@class," +
                    "'fc-day-top')]");//заголовки дней текущей недели
            for (int d = 0; d < awh.size(); d++) {//Проход по дням недели
                fstRow.add(row1.$x(String.format("./td[%d]", (d + 1))).getText());
                if ((w == 0)//ищем 1й день месяца
                        && !foundFirst
                        && (Integer.parseInt(awh.get(d).$x("./span")
                        .getText()) == 1)) {
                    foundFirst = true;
                    belongsToM = true;
                }
                if ((w == (aw.size() - 1)//ищем первый день нового месяца
                        || w == (aw.size() - 2))
                        && Integer.parseInt(awh.get(d).$x("./span").getText()) == 1) {
                    belongsToM = false;
                }
                ArrayList<String> eventsOfOneDay = new ArrayList<>();//сюда
                // помещаем информацию о всех записях для d-го дня
                ElementsCollection wRows = aw.get(w).$$x(//ряды wй недели
                        ".//div[@class='fc-content-skeleton']//tbody/tr");
                String fstRowd = fstRow.get(d);//получил событие из 1й строки
                // для d-го дня
                eventsOfOneDay.add(fstRowd);//добавил в список дня d-е событие
                for (int r = 1; r < wRows.size(); r++) {//иду по рядам
                    //Нужно вытащить инфу о всех остальных рядах недели для
                    // текущего (d) дня. Буду считать,
                    // что если первый tr содержит информацию (помимо пустой
                    // строки), то это - рабочий день, иначе выходной или
                    // день другого месяца.
                    if (belongsToM && fstRowd.length() > 0 && wRows.size() > 1) {
                        eventsOfOneDay.add(wRows.get(r).$x(String.format("./td" +
                                        "[%d]",
                                (n++))).getText());//добавляю в список события
                        // Проходимся по второму ряду (и, возможно, следующим),
                        // инкрементируя индекс только в момент добавления
                        // элемента.
                    }
                }
                String row1Content = fstRow.get(d);
                days.add(new Day(awh.get(d).$x("./span").getText(),
                        awh.get(d).getAttribute("data-date"),
                        awh.get(d),
                        belongsToM,
                        row1Content,
                        eventsOfOneDay,
                        d + 1
                ));
            }
        }
    }
}




