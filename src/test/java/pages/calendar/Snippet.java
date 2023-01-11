package pages.calendar;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author Алексеев Степан
 * @date 01.01.2023
 * <p>
 * Класс для сохранения содержимого сниппета справа сверху
 */
public class Snippet {

    private LocalDate date;//дата дня
    private ArrayList<String> events;//события дня

    public Snippet(LocalDate date, ArrayList<String> events) {
        this.date = date;
        this.events = events;
    }

    public ArrayList<String> getEvents() {
        return events;
    }

    public LocalDate getDate() {
        return date;
    }
}
