package pages.calendar;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author Алексеев Степан
 * @date 01.01.2023
 * <p>
 * Класс для сохранения содержимого одного дня
 */
@Data
@AllArgsConstructor
public class Day {
    /**
     * это рабочий день
     */
    private boolean isWorkDay = false;
    /**
     * это выходной день
     */
    private boolean isHoliday = false;
    /**
     * это день отпуска
     */
    private boolean isVacationDay = false;
    /**
     * число (день месяца)
     */
    private String fcDayNumber;
    /**
     * дата дня
     */
    private LocalDate date;
    /**
     * принадлежность текущему месяцу
     */
    private boolean belongsToThisMonth;
    /**
     * содержимое первого ряда в таблице недели (обычно если там пусто, то и в остальных рядах пусто)
     */
    private String row1tdContents;
    /**
     * события дня
     */
    private ArrayList<String> events;
    /**
     * номер дня недели
     */
    private int dayOfWeek;
}
