package task22.pages.calendar;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

import static com.codeborne.selenide.Condition.visible;
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
     * Всплывающее окошко справа сверху о том, что расписание успешно сохранено после работы на форме по кнопке
     * "Изменить".
     */
    private String confirmationOfScheduleChangeXPath =
            "//div[@class='toast-message' and text()='Расписание успешно изменено']";
    /**
     * заголовки с датами текущего месяца
     */
    private String currentMonthHeadersXPath = "//thead//td[not(contains(@class,'fc-other-month'))and @data-date]";
    /**
     * часть календаря: номер дня вверху ячейки дня для клика. Определяется по дате %s
     */
    private String dayNumberXPath = "//div[@class='fc-content-skeleton']//td[@data-date='%s']";
    /**
     * первый ряд tr таблицы
     */
    private String firstRow = "./div[@class='fc-content-skeleton']//tbody/tr[1]";
    /**
     * заголовки дней текущей недели
     */
    private String currentWeekHeaders = ".//td[contains(@class,'fc-day-top')]";
    /**
     * Недели. Обычно их 6 в этом календаре
     */
    private String weeks = "//div[@class='fc-row fc-week fc-widget-content']";
    /**
     * ряды w-й недели
     */
    private String rowsOfwWeek = ".//div[@class='fc-content-skeleton']//tbody/tr";
    /**
     * Дата в сниппете
     */
    private String snippetDateXPath = "//div[contains(@class,'schedule-right-panel')]//h3";
    /**
     * Кнопка месяца
     */
    private String monthButton = "//div[@class='datepicker-months']//span[contains(@class,'month') and text()='%s']";
    /**
     * Элементы с текстом в сниппете
     */
    private String snippetTextsXPath = "//div[contains(@class,'schedule-right-panel')]//div[@class='render-badge']/span";
    /**
     * Строка над календарём с месяцем и годом
     */
    private String calendarDateXPath = "//input[@name='filter-date']";
    /**
     * Progress bar загрузки календаря
     */
    private String calendarProgressBarXPath = "//span[contains(@class, 'btn-primary m-loader')]";
    /**
     * Кнопка "Изменить" в боковом сниппете
     */
    private String changeSnippetButtonXPath = "//button[@id='popup-change-schedule-button']";
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
     * Проверка того, что расписание успешно сохранено после работы на форме по кнопке "Изменить".
     *
     * @return
     */
    public CalendarPO checkIfConfirmarionOfScheduleChangeAppeared() {
         $x(confirmationOfScheduleChangeXPath).shouldBe(visible);
         return this;
    }

    /**
     * Элемент списка работников, найденный по части имени
     *
     * @param namePart часть имени/фамилии работника для выбора из списка
     * @return селенид элемент с выбранным работником
     */
    @Step("Получаем элемент списка с работником, содержащим \"{namePart}\" в имени ")
    private SelenideElement listChosenEmployeeByNamePart(String namePart) {
        return $x("//li[contains(text(),'" + namePart + "')]");
    }

    public CalendarPO clickChangeSnippetButton() {
        $x(changeSnippetButtonXPath).shouldBe(visible, Duration.ofSeconds(10)).click();
        return this;
    }

    /**
     * Это называется метод-локатор.
     * Выбор месяца в выпадающем меню по названию в виде "Янв"
     *
     * @param month указание месяца
     * @return возвращает элемент кнопки с месяцем
     */
    @Step("Получаем месяц по названию \"{month}\"")
    private SelenideElement getMonthButton(Month month) {//надо передать в xpath в виде "Янв", "Мар" и т.д.
        return $x(String.format(monthButton,
                month.getDisplayName(TextStyle.FULL_STANDALONE, new Locale("ru")).substring(0, 3)));
    }

    /**
     * Нажимает на день текущего месяца по его номеру
     *
     * @param number
     */
    @Step("Кликаем день текущего месяца по номеру \"{number}\"")
    public void clickDayOfThisMonth(int number) {
        $x(String.format("//td[not(contains(@class,'fc-other-month'))]/span[@class='fc-day-number' and text()='%d']",
                number)).click();
    }

    /**
     * Кликает день по дате
     *
     * @param date дата для клика
     */
    @Step("Кликаем день с датой \"{date}\"")
    public CalendarPO clickDayOfThisMonth(LocalDate date) {
        String dateStr = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        $x(String.format(dayNumberXPath, dateStr)).click();
        return this;
    }

    /**
     * Выбирает следующий месяц в календаре
     *
     * @return
     */
    @Step("Выбираем следующий месяц в календаре")
    public CalendarPO chooseNextMonth() {
        Month month = getMonth();
        Year year = getYear();
        YearMonth yearMonth = YearMonth.of(year.getValue(), month).plusMonths(1);
        chooseMonthAndYear(yearMonth.getMonth(), Year.of(yearMonth.getYear()));
        return this;
    }

    /**
     * @return Возвращает месяц отображённый вверху календаря
     */
    @Step("Получаем месяц отображённый вверху календаря")
    public Month getMonth() {
        String monthToParse = $x(calendarDateXPath)
                .getValue().split("\\s")[0].toLowerCase().substring(0, 3);
        DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("d-MMM-yyyy");
        LocalDate localDateFromCalendar = LocalDate.parse(String.format("1-%s-1980", monthToParse), formatter3);
        return localDateFromCalendar.getMonth();
    }

    /**
     * @return возвращает год, отображённый вверху календаря
     */
    @Step("Получаем год, отображённый вверху календаря")
    public Year getYear() {
        String yearToParse = $x(calendarDateXPath)
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
    @Step("Выбираем месяц \"{month}\" и год \"{year}\"")
    public CalendarPO chooseMonthAndYear(Month month, Year year) {
        buttonForDateChoice.click();
        int neededYear = year.getValue();
        int currentYear = getYear().getValue();
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
    @Step("Выбираем сотрудника по части имени \"{employee}\"")
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
    @Step("Ждём появления сообщения о загрузке. Потом ждём его исчезновения")
    public CalendarPO waitForCalendarToLoad() {
        // $x(calendarProgressBarXPath).shouldBe(visible);
        $x(calendarProgressBarXPath).shouldNotBe(visible, Duration.ofSeconds(10));
        return this;
    }

    /**
     * Возвращает события расписанные в сниппете
     */
    @Step("Получаем события, расписанные в сниппете")
    private ElementsCollection getSnippetEvents() {
        return $$x(snippetTextsXPath);
    }

    /**
     * Возвращает дату из сниппета
     */
    @Step("Получаем дату из сниппета")
    private LocalDate getSnippetDate() {
        return LocalDate
                .parse($x(snippetDateXPath)
                        .getText(), DateTimeFormatter
                        .ofPattern("dd.MM.yy"));
    }

    /**
     * @return возвращает все даты текущего месяца
     */
    @Step("Получаем все даты текущего месяца")
    public ArrayList<LocalDate> getDates() {
        ArrayList<LocalDate> dates = new ArrayList<>();
        ElementsCollection datesSelenide =
                $$x(currentMonthHeadersXPath);
        for (int i = 0; i < datesSelenide.size(); i++) {
            dates.add(LocalDate.parse(datesSelenide.get(i).getAttribute("data-date"), DateTimeFormatter
                    .ofPattern("yyyy-MM-dd")));
        }
        return dates;
    }

    /**
     * Возвращает объект сниппета
     */
    @Step("Получаем объект сниппета")
    public Snippet getSnippet() {
        ElementsCollection listEv = getSnippetEvents();
        ArrayList<String> snippetEventsString = new ArrayList<>();
        for (int i = 0; i < listEv.size(); i++) {
            snippetEventsString.add(listEv.get(i).getText());
        }
        return new Snippet(getSnippetDate(), snippetEventsString);
    }

    /**
     * @param dateOfDay дата для создания объекта Day
     * @return возвращает объекты дня по дате
     */
    @Step("Получаем объект дня по дате \"{dateOfDay}\"")
    public Day getDay(LocalDate dateOfDay) {
        Day day = new Day(false,
                false,
                false,
                false,//"Здесь уже никто не работает. Всё делают роботы."
                LocalDate.of(3023, 01, 13),
                new ArrayList<>(),
                1
        );
        ElementsCollection aw = $$x(weeks);
        for (int w = 0; w < aw.size(); w++) {//Проход по неделям
            SelenideElement row1 = aw.get(w).$x(firstRow);//первый ряд tr таблицы
            ElementsCollection awh = aw.get(w).$$x(currentWeekHeaders);
            for (int dayOfWeek = 0; dayOfWeek < awh.size(); dayOfWeek++) {//Проход по дням недели
                LocalDate localDate = LocalDate.parse(awh.get(dayOfWeek).getAttribute("data-date"),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                if (localDate.equals(dateOfDay)) {
                    boolean isWorkDay = false;//рабочий день
                    boolean isHoliday = false;//выходной день
                    boolean isVacationDay = false;//день отпуска
                    String aClassAttribute = row1.$x(String.format("./td[%d]//a", (dayOfWeek + 1))).getAttribute("class");
                    if (aClassAttribute.contains("schedule-badge--default")) {
                        isWorkDay = true;
                    } else if (aClassAttribute.contains("schedule-badge--no-event")) {
                        isHoliday = true;
                    } else if (aClassAttribute.contains("schedule-badge--vacation")) {
                        isVacationDay = true;
                    }
                    ArrayList<String> eventsOfOneDay = new ArrayList<>();
                    ElementsCollection wRows = aw.get(w).$$x(rowsOfwWeek);
                    for (int r = 0; r < wRows.size(); r++) {//иду по рядам событий дня
                        if (!isHoliday) {
                            eventsOfOneDay.add(wRows.get(r).$x(String.format("./td[%d]", (r + 1))).getText());
                        }
                    }
                    day = new Day(true,
                            isWorkDay,
                            isHoliday,
                            isVacationDay,
                            dateOfDay,
                            eventsOfOneDay,
                            dayOfWeek + 1
                    );
                    break;
                }
            }
        }
        return day;
    }
}




