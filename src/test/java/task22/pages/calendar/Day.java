package task22.pages.calendar;

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
     * Подтверждение существования дня в календаре
     */
    private boolean dayExistsInCalendar = false;
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
     * дата дня
     */
    private LocalDate date;
    /**
     * события дня
     */
    private ArrayList<String> events;
    /**
     * номер дня недели
     */
    private int dayOfWeek;
}
