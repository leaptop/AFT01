package pages.selenide;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.impl.CollectionElement;
import org.openqa.selenium.WebElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class CalendarPO {
    public CalendarPO() {

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
        } else if (diffInYears < 0){
            for (int i = 0; i < Math.abs(diffInYears); i++) {
                buttonSwitchingYearsPrev.click();
            }
        }
        getMonthButton(neededDate.substring(0, 3)).click();
        applyButton.click();
        String str = "g";
        return this;
    }
    private SelenideElement applyButton = $x("//button[text()='Применить']");
    private SelenideElement getMonthButton (String mon){
        return $x(String.format("//div[@class='datepicker-months']//span[contains(@class,'month') and text()='%s']", mon));
    }
private ElementsCollection buttonOfMonth=
        $$x("//div[@class='datepicker-months']//span[contains(@class,'month')]");
    private SelenideElement buttonSwitchingYearsPrev =
            $x("//div[@class='datepicker-months']//th[@class='prev']");
    private SelenideElement buttonSwitchingYearsNext =
            $x("//div[@class='datepicker-months']//th[@class='next']");
    private SelenideElement buttonForDateChoice =
            $x("//div[@class='input-group date filter_input_date']//i");

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

}
