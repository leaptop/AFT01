package pages.selenide;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.impl.CollectionElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class CalendarPO {
    public CalendarPO() {

    }

    /**
     * Ждём появления сообщения о загрузке. Потом ждём его исчезновения.
     *
     * @return
     */
    public CalendarPO waitForCalendarToLoad() {
        $x("//span[contains(@class, 'btn-primary m-loader')]").shouldBe(Condition.visible);
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
     *
     * @return Возвращает все зелёные плашки рабочих дней.
     */
    public ElementsCollection getWorkDayLInks(){
        return $$x(xpathForDefaultDay);
    }

    /**
     *
     * @return Возвращает плашки выходных дней.
     */
    public ElementsCollection getHolidayLinks(){
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

}
