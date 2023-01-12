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
    private boolean isWorkDay = false;//рабочий день
    private boolean isHoliday = false;//выходной день
    private boolean isVacationDay = false;//день отпуска
    private String fcDayNumber;//число (день месяца)
    private LocalDate date;//дата дня
    private boolean belongsToThisMonth;//принадлежность текущему месяцу
    private String row1tdContents;//содержимое первого ряда
    private ArrayList<String> events;//события дня
    private int dayOfWeek;//день недели
}
