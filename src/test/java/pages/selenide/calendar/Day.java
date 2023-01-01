package pages.selenide.calendar;

import com.codeborne.selenide.SelenideElement;

import java.util.ArrayList;

/**
 * @author Алексеев Степан
 * @date 01.01.2023
 * <p>
 * Класс для сохранения содержимого одного дня
 */
public class Day {
    private String fcDayNumber;//число (день месяца)
    private String date;//дата дня
    private SelenideElement linkToClick;//ссылка для клика в календаре
    private boolean belongsToThisMonth;//принадлежность текущему месяцу
    private String row1tdContents;//содержимое первого ряда
    private ArrayList<String> events;//события дня
    private int dayOfWeek;//день недели

    public Day(String fcDayNumber, String date,
               SelenideElement linkToClick, boolean belongsToThisMonth,
               String row1tdContents, ArrayList<String> events,
               int dayOfWeek) {
        this.fcDayNumber = fcDayNumber;
        this.date = date;
        this.linkToClick = linkToClick;
        this.belongsToThisMonth = belongsToThisMonth;
        this.row1tdContents = row1tdContents;
        this.events = events;
        this.dayOfWeek = dayOfWeek;
    }

    public String getFcDayNumber() {
        return fcDayNumber;
    }

    public String getDate() {
        return date;
    }

    public SelenideElement getLinkToClick() {
        return linkToClick;
    }

    public boolean isBelongsToThisMonth() {
        return belongsToThisMonth;
    }

    public String getRow1tdContents() {
        return row1tdContents;
    }

    public ArrayList<String> getEvents() {
        return events;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }
}
