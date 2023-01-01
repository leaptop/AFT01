package pages.selenide.calendar;

import java.util.ArrayList;

/**
 * @author Алексеев Степан
 * @date 01.01.2023
 * <p>
 * Класс для сохранения содержимого сниппета справа сверху
 */
public class Snippet {

    private String date;//дата дня
    private ArrayList<String> events;//события дня

    public Snippet(String date, ArrayList<String> events) {
        this.date = date;
        this.events = events;
    }

    public ArrayList<String> getEvents() {
        return events;
    }

    public String getDate() {
        return date;
    }
}
