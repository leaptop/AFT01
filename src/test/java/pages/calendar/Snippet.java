package pages.calendar;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author Алексеев Степан
 * @date 01.01.2023
 * <p>
 * Класс для сохранения содержимого сниппета справа сверху
 */
@Data
@AllArgsConstructor
public class Snippet {

    /**
     * дата дня
     */
    private LocalDate date;
    /**
     * события дня
     */
    private ArrayList<String> events;
}
