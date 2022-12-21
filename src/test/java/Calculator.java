import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Задание 4 АФТ
 */
public class Calculator {
    public int sum(int a, int b) {
        return a + b;
    }

    int Addend1 = 2, Addend2 = 2, correctSum = 4, WrongSum = 5;

    @Test
    void test1() {
        // Assertions.assertEquals(correctSum, sum(Addend1, Addend2), "Сумма чисел " + Addend1 + " и " + Addend2 + " не равна числу " + correctSum);
        Assertions.assertEquals(correctSum, sum(Addend1, Addend2), String.format("Сумма чисел %d и %d не равна числу %d", Addend1, Addend2, correctSum));

    }

    @Test
    void test2() {//результат в виде org.opentest4j.AssertionFailedError
        Assertions.assertEquals(WrongSum, sum(Addend1, Addend2), String.format("Сумма чисел %d и %d не равна числу %d", Addend1, Addend2, WrongSum));
    }

    @Test
    void test3() {
        assertTrue(sum(Addend1, Addend2) == correctSum, String.format("Сумма чисел %d и %d не равна числу %d", Addend1, Addend2, correctSum));
    }

    @Test
    void test4() {
        assertTrue(sum(Addend1, Addend2) == WrongSum, String.format("Сумма чисел %d и %d не равна числу %d", Addend1, Addend2, WrongSum));
    }

    /**
     * В документации JUnit Jupiter это не называется Soft Assert,
     * но работает, проверяя каждое выражение.
     */
    @Test
    void test5() {
        Assertions.assertAll(
                () -> assertEquals(correctSum, sum(Addend1, Addend2), String.format("Сумма чисел %d и %d не равна числу %d", Addend1, Addend2, correctSum)),
                () -> assertEquals(WrongSum, sum(Addend1, Addend2), String.format("Сумма чисел %d и %d не равна числу %d", Addend1, Addend2, WrongSum)),
                () -> assertTrue(sum(Addend1, Addend2) == correctSum, String.format("Сумма чисел %d и %d не равна числу %d", Addend1, Addend2, correctSum)),
                () -> assertTrue(sum(Addend1, Addend2) == WrongSum, String.format("Сумма чисел %d и %d не равна числу %d", Addend1, Addend2, WrongSum))
        );
    }

    /**
     * Т.е. отличие между test2 & myTest в том, что в первом случае срабатывает обычный
     * провал теста, а здесь выбрасывается исключение, которое в принципе не позволяет дойти
     * до проверки на истинность.
     */
    @Test
    void myTest() {//результат в виде java.lang.ArithmeticException: / by zero
        try {
            Assertions.assertTrue(1 / 0 == 1);
        } catch (ArithmeticException ae) {
            if (ae.getMessage().equals("/ by zero")) {
                fail("Исключение: деление на ноль");
            }
        }
    }
}
