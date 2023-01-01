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
    public String fcDayNumber;//число (день месяца)
    public String date;//дата дня
    public SelenideElement linkToClick;//ссылка для клика в календаре
    public boolean belongsToThisMonth;//принадлежность текущему месяцу
    public String row1tdContents;//содержимое первого ряда
    public ArrayList<String> events;//события дня
    public int dayOfWeek;//день недели

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
}
