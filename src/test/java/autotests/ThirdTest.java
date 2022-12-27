package autotests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Вопрос: 3.3.d Можешь ли ты так же заменить
 * текст “autotests.FirstTest class started” на другой? Почему?
 * Ответ: да, могу, ситуация аналогичная: сначала вызывается @BeforeAll
 * родительского класса, поэтому можно этот метод переписать как угодно.
 * <p>
 * Если же в вопросе имелось в виду сохранение функционала класса autotests.FirstTest
 * в первоначальном виде, то нужно будет просто переопределить здесь метод
 *
 * @author Алексеев Степан
 * @BeforeAll с той же сигнатурой, что и в autotests.FirstTest.
 * Точно так же можно переопределить @BeforeEach.
 * @date 16.12.2022
 */
public class ThirdTest extends FirstTest {
    @BeforeAll
    static void beforeAll() {
        System.out.println("Overriden starting");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("beforeEachThird");
    }

    @Test
    void test8() {
        System.out.println("Test #8");
    }

    @Test
    void test9() {
        System.out.println("Test #9");
    }
}
