package autotests;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * @author Алексеев Степан
 * @date 10.01.2023
 */
public class PlayWithDates {
    @Test
    void playWithDates() {
        LocalDate now = LocalDate.now();//2023-01-11
        String nativen = now.format(DateTimeFormatter.ofPattern("MMM")); //янв
        String m = now.getMonth().getDisplayName(TextStyle.FULL, new Locale("ru"));//января
        String m1 = now.getMonth().getDisplayName(TextStyle.SHORT_STANDALONE, new Locale("ru"));//Янв.
        String mx = LocalDate.of(2023, 07, 13).getMonth().getDisplayName
                (TextStyle.SHORT_STANDALONE, new Locale("ru"));//Июль
        String m2 = now.getMonth().getDisplayName(TextStyle.FULL_STANDALONE, new Locale("ru"));//Январь
        String m3 = now.getMonth().getDisplayName(TextStyle.NARROW, new Locale("ru"));//Я
        String m4 = now.getMonth().getDisplayName(TextStyle.SHORT, new Locale("ru"));//янв
        String french = now.format(DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.FRANCE)); // 2023-01-11
        // Month mEnteredRussian = Month.from()
        //Month mo = CalendarPO.getCurrentMonth("Январь");
       // LocalDate mi = LocalDate.parse("JANUARY", DateTimeFormatter.ofPattern("MMM"));
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM, yyyy", Locale.FRENCH);
        final String month = LocalDate.now().format(formatter);//mercredi, 11 janvier, 2023

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("MMMM d, yyyy HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse("июня 5, 2018 12:10:56", formatter1);
        System.out.println(localDateTime);//2018-06-05T12:10:56

        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        LocalDate localDate = LocalDate.parse("декабря 5, 2018", formatter2);//2018-12-05
        System.out.println(localDate);
        System.out.println(localDate.getMonth().getValue());
        DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("d-MMM-yyyy");
        LocalDate localDateNominative = LocalDate.parse("1-июн-1995", formatter3);

        Month mNext = Month.DECEMBER.plus(1);//January
        //Year yearPlus1month = Year.of(2015).plus(1, ChronoUnit.MONTHS);//gives:
        //(UnsupportedTemporalTypeException: Unsupported unit: Months)

        String s = "abcdefgh".substring(0,2);//ab
        String s1 = "abcdefgh".substring(1,2);//b
        String s2 = "abcdefgh".substring(0,3);//abc

        String d = "";
    }
}
