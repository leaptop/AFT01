package pages.selenide.calendar;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Класс для реализации объекта Page Object для страницы Тайм Трекера с
 * календарём.
 */
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
     * @return Возвращает кнопку вызова выпадающего списка работников
     */
    private SelenideElement nameDropDownMenuButton = $x("//span[@id='select2--container']");
    /**
     * Элемент списка работников, найденный по части имени
     *
     * @param namePart часть имени/фамилии работника для выбора из списка
     * @return селенид элемент с выбранным работником
     */
    private SelenideElement listChosenEmployeeByNamePart(String namePart) {
        return $x("//li[contains(text(),'" + namePart + "')]");
    }
    /**
     * Это называется метод-локатор.
     * Выбор месяца в выпадающем меню по названию в виде "Янв"
     *
     * @param month указание месяца
     * @return возвращает элемент кнопки с месяцем
     */
    private SelenideElement getMonthButton(Month month) {//надо передать в xpath в виде "Янв", "Мар" и т.д.
        return $x(String.format("//div[@class='datepicker-months']//span[contains(@class,'month') and text()='%s']",
                month.getDisplayName(TextStyle.FULL_STANDALONE, new Locale("ru")).substring(0, 3)));
    }

    /**
     * Выбирает следующий месяц в календаре
     *
     * @return
     */
    public CalendarPO chooseNextMonth() {
        Month month = getMonth();
        Year year = getYear();
        if (month.getValue() == 12) {
            chooseMonthAndYear(month.JANUARY, year.plus(1, ChronoUnit.YEARS));
        } else {
            chooseMonthAndYear(month.plus(1), year);
        }
        return this;
    }

    /**
     * @return Возвращает месяц отображённый вверху календаря
     */
    public Month getMonth() {
        String monthToParse = $x("//input[@name='filter-date']")
                .getValue().split("\\s")[0].toLowerCase().substring(0, 3);
        DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("d-MMM-yyyy");
        LocalDate localDateFromCalendar = LocalDate.parse(String.format("1-%s-1980", monthToParse), formatter3);
        return localDateFromCalendar.getMonth();
    }

    /**
     * @return возвращает год, отображённый вверху календаря
     */
    public Year getYear() {
        String yearToParse = $x("//input[@name='filter-date']")
                .getValue()
                .split("\\s")[1];
        return Year.of(Integer.parseInt(yearToParse));
    }

    /**
     * Выбирает месяц и год в календаре
     *
     * @param month месяц, который нужно выбрать
     * @param year  год, который нужно выбрать
     */

    public CalendarPO chooseMonthAndYear(Month month, Year year) {
        buttonForDateChoice.click();
        int neededYear = year.getValue();
        int currentYear = LocalDate.now().getYear();
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
        getMonthButton(month).click();
        applyButton.click();
        waitForCalendarToLoad();
        return this;
    }

    /**
     * Выбор сотрудника из списка
     *
     * @param employee часть имени или фамилии сотрудника
     * @return возвращает текущий PO для вызовов методов по цепочке.
     */
    public CalendarPO chooseEmployee(String employee) {
        nameDropDownMenuButton.click();
        listChosenEmployeeByNamePart(employee).click();
        applyButton.click();
        waitForCalendarToLoad();
        return this;
    }

    /**
     * Ждём появления сообщения о загрузке. Потом ждём его исчезновения.
     *
     * @return this
     */
    public CalendarPO waitForCalendarToLoad() {
        $x("//span[contains(@class, 'btn-primary m-loader')]")
                .should(Condition.disappear, Duration.ofSeconds(30));
        return this;
    }

    /**
     * Возвращает события расписанные в сниппете через Selenium.
     */
    private List<WebElement> getSnippetEvents() {
        return WebDriverRunner.driver().getWebDriver().findElements(By.xpath(
                "//div[contains(@class,'schedule-right-panel')" +
                        "]//div[@class='render-badge']/span"));
    }

    /**
     * Возвращает дату из сниппета
     */
    private LocalDate getSnippetDate() {//проверить работу
        return LocalDate.parse(WebDriverRunner.driver().getWebDriver().findElement(By.xpath(
                        "//div[contains(@class,'schedule-right-panel')]//h3"))
                .getText(), DateTimeFormatter.ofPattern("dd.MM.yy"));
    }

    /**
     * Здесь получаем содержимое сниппета и заносим его в переменную snippet.
     */
    public Snippet fillSnippet() {
        List<WebElement> listEv = getSnippetEvents();
        ArrayList<String> snippetEventsString = new ArrayList<>();
        for (int i = 0; i < listEv.size(); i++) {
            snippetEventsString.add(listEv.get(i).getText());
        }
        return new Snippet(getSnippetDate(), snippetEventsString);
    }

    /**
     * Данный метод сохраняет всю информацию о днях из календаря в виде
     * массива days.
     */
    public ArrayList<Day> fillTheCalendar() {
        ArrayList<Day> days = new ArrayList<>();
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
                if ((w == (aw.size() - 1) || w == (aw.size() - 2))//ищем первый день нового месяца
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
                    // строки), то это - рабочий день или день отпуска, иначе выходной или
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
                days.add(new Day(
                        awh.get(d).$x("./span").getText(),
                        LocalDate.parse(awh.get(d).getAttribute("data-date"),
                                DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        awh.get(d),
                        belongsToM,
                        row1Content,
                        eventsOfOneDay,
                        d + 1
                ));
            }
        }
        return days;
    }
}




