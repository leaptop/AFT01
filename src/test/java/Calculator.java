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

    int x = 2, y = 2, z = 4, t = 5;

    @Test
    void test1() {
        Assertions.assertEquals(sum(x, y), z, "Сумма чисел " + x + " и " + y + " не равна числу " + z);
    }

    @Test
    void test2() {//результат в виде org.opentest4j.AssertionFailedError
        Assertions.assertEquals(sum(x, y), t, "Сумма чисел " + x + " и " + y + " не равна числу " + t);
    }

    @Test
    void test3() {
        assertTrue(sum(x, y) == z, "Сумма чисел " + x + " и " + y + " не равна числу " + z);
    }

    @Test
    void test4() {
        assertTrue(sum(x, y) == t, "Сумма чисел " + x + " и " + y + " не равна числу " + t);
    }

    /**
     * В документации JUnit Jupiter это не называется Soft Assert,
     * но работает, проверяя каждое выражение.
     */
    @Test
    void test5() {
        Assertions.assertAll(
                () -> assertEquals(sum(x, y), z, "Сумма чисел " + x + " и " + y + " не равна числу " + z),
                () -> assertEquals(sum(x, y), t, "Сумма чисел " + x + " и " + y + " не равна числу " + t),
                () -> assertTrue(sum(x, y) == z, "Сумма чисел " + x + " и " + y + " не равна числу " + z),
                () -> assertTrue(sum(x, y) == t, "Сумма чисел " + x + " и " + y + " не равна числу " + t)
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
