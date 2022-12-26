package pages.selenide;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.fail;

public class CalendarPO {
    public CalendarPO() {

    }

    public void checkCalendarSnippetInteraction() {
       // webdriver().driver().config().timeout()

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
            formattedDateToCheck+=".";
            formattedDateToCheck+= dateToCheck.substring(5,7);
            formattedDateToCheck+=".";
            formattedDateToCheck+=dateToCheck.substring(2,4);
            if (formattedDateToCheck
                    .equals($x("//h3[@class='m-portlet__head-text right_panel_name']").getText())) {
                continue;
            } else {
                fail(String.format("В сниппете справа сверху дата должна быть %s, а фактически %s"
                        , formattedDateToCheck
                        , $x("//h3[@class='m-portlet__head-text right_panel_name']").getText()));
            }
        }
    }

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
        return this;
    }

    /**
     * Кнопка "Применить"
     */
    private SelenideElement applyButton = $x("//button[text()='Применить']");

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


    // Оставлено потому что пока не смог реализовать структурированное сохранение календаря. Не уверен, нужно ли это.
// Можно удалить когда задача 9.4 будет выполнена.
//
//    public void fillMapOfDays() {
//        List<String> dates = new ArrayList<>();
//        mapOfDays = new HashMap<>();
//        ElementsCollection ec = $$x(
//                "//td[contains(@class,'fc-day-top') and not (contains(@class,'fc-other-month'))]");
//        for (int i = 0; i < ec.size(); i++) {
//            dates.add(ec.get(i).getAttribute("data-date"));
//            mapOfDays.put(ec.get(i).getAttribute("data-date"), new ArrayList<>());
//            for (int j = 0; j <; j++) {//пройти столько раз, сколько строк в таблице (для одной недели)
//
//            }
//        }
//        String str = "y";
//    }
//
//    private String eachWeekOfMonth = "//div[@class='fc-content-skeleton']";
//    private String headersOfWeek = "//td[contains(@class,'fc-day-top') and not (contains(@class,'fc-other-month'))]";
//    private String anyPlankOfWeek = "//span[@class='fc-title']";
//
//    public void check() {//Наверное этот вариант не годится, т.к. индексы td разнятся из-за наличия в некоторых неделях
//        // выходных до субботы и воскресенья...
//        ElementsCollection allHeadersOfDaysOfCurrentMonth = $$x("//td[contains(@class,'fc-day-top') and not (contains(@class,'fc-other-month'))]");
//        for (int i = 0; i < allHeadersOfDaysOfCurrentMonth.size(); i++) {
//            allHeadersOfDaysOfCurrentMonth.get(i).click();
//            // String firstPlank =
//            int w = 0;
//            ArrayList<String> ls = new ArrayList<>();
//            while (allHeadersOfDaysOfCurrentMonth.get(i).sibling(w).exists()) {
//                ls.add(allHeadersOfDaysOfCurrentMonth.get(i).sibling(w).getAttribute("data-date"));//смотрит впереди стоящих сиблингов. В воскресенье видит 0 сиблингов
//                // в понедельник 6. Во вторник 5. Теперь надо обратиться ко всем рядам таблицы недели по нужному индексу, опираясь на w.
//                //Кстати при этом не учитывается, принадлежит день текущему месяцу или нет.
//                // При этом каждый элемент единственного дня нужно записать сюда. После окончания рядов сравнивать
//                // с боковым сниппетом полученную информацию.
//                 w++;
//            }
//            ls = null;
//
//            //Нужно пройтись по рядам данной недели:
//            ElementsCollection allCurrentWeekRows = allHeadersOfDaysOfCurrentMonth.get(i).$$x("./../../../tbody/tr");
//            for (int j = 0; j < allCurrentWeekRows.size(); j++) {
//                //  allCurrentWeekRows.get(j).$x("./td")
//            }
//            String r = "f";
//        }
//
//    }
//
////    public void goThroughWeeks() {
////        mapOfDays = new HashMap<>();
////        ElementsCollection weeks = $$x("//div[@class='fc-content-skeleton']");//сохранили все недели (таблицы)
////        for (int i = 0; i < weeks.size(); i++) {//проходимся по каждой таблице (неделе)
////            ElementsCollection headersOrDates = weeks.get(i).$$x(".//td[contains(@class,'fc-day-top')]");//заголовки(даты) конкретной недели (включая дни вне текущего месяца вроде)
////            ArrayList<ArrayList<String>> eventsOfDay = new ArrayList<>();//для каждой даты свой набор событий
////            ElementsCollection rows = weeks.get(i).$$(".//tbody/tr");//в каждой таблице (неделе) есть набор рядов
////            for (int j = 0; j < rows.size(); j++) {//проходимся по каждому ряду в таблице (неделе)
////                ElementsCollection cells = rows.get(j).$$("./td");//набор ячеек конкретного ряда
////                for (int k = 0; k < cells.size(); k++) {//в каждом ряду проходимся по ячейкам (td)
////                    //теперь можно заполнять списки событий...
////                    eventsOfDay.get(i).add(cells.get(k).$x(".//span").getValue());
////
////                }
////                mapOfDays.put(headersOrDates.get(i).getAttribute("data-date"), eventsOfDay.get(i));
////            }
////
////
////            //            for (int j = 0; j < headersOrDates.size(); j++) {
//////                ArrayList<String> eventsOfDay = new ArrayList<>();
//////                ElementsCollection planks = weeks.get(i).$$("//span[@class='fc-title']");
//////                SelenideElement tableRows = $$x(weeks)
//////                for (int k = 0; k < planks.size(); k++) {
//////
//////                }
//////                mapOfDays.put(headersOrDates.get(j).getAttribute("data-date"), eventsOfDay);
//////            }
////        }
////        String s = "f";
////    }
//
//
//    private Map<String, List<String>> mapOfDays;

}
