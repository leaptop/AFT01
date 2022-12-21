import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
/**
 * Вопрос: 3.3.d Можешь ли ты так же заменить
 * текст “FirstTest class started” на другой? Почему?
 * Ответ: да, могу, ситуация аналогичная: сначала вызывается @BeforeAll
 * родительского класса, поэтому можно этот метод переписать как угодно.
 *
 * Если же в вопросе имелось в виду сохранение функционала класса FirstTest
 * в первоначальном виде, то нужно будет просто переопределить здесь метод
 * @BeforeAll с той же сигнатурой, что и в FirstTest.
 * Точно так же можно переопределить @BeforeEach.
 */
public class ThirdTest extends FirstTest{
    @BeforeAll
    static void beforeAll() {
        System.out.println("Overriden starting");
    }
    @BeforeEach
    void beforeEach(){
        System.out.println("beforeEachThird");
    }
    @Test
    void test8(){
        System.out.println("Test #8");
    }
    @Test
    void test9(){
        System.out.println("Test #9");
    }
}
