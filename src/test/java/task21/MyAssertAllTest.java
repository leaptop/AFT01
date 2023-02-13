package task21;

import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.function.Executable;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static com.codeborne.selenide.Selenide.open;
import static org.testng.Assert.*;

/**
 * Пример вызова метода:
 *
 * @author Алексеев Степан
 * @date 19.01.2023
 */

public class MyAssertAllTest {
    /**
     * 1 напиши софт-ассерт для TestNG в стиле assertAll JUnit-а. Т.е. для TestNG у тебя должен быть метод,
     * который принимает список проверок и падает после выполнения всех проверок, а не после первой.
     * P.S. желательно не подглядывать в реализацию JUnit-а.
     */
    public void mySoftAssert(Executable... exs) {
        ArrayList<Throwable> e0 = new ArrayList<>();
        for (int i = 0; i < exs.length; i++) {
            try {
                exs[i].execute();
            } catch (Throwable e1) {
                System.out.println(e1.getLocalizedMessage());
                e0.add(e1);
            }
            if (i == (exs.length - 1) && !e0.isEmpty()) {
                fail();
            }
        }
    }

    @Test
    public void testAssertAll() {
        int a = 1, aa = 1, b = 2, c = 3, cc = 3, d = 4, e = 5, f = 55;
        mySoftAssert(
                () -> assertEquals(a, aa, "числа не равны:"),
                () -> assertEquals(b, a, "числа не равны:"),
                () -> assertEquals(c, cc, "числа не равны:"),
                () -> assertEquals(e, d, "числа не равны:"),
                () -> assertTrue(f < d, String.format("неравенство неверно, %d не меньше, чем %d", f, d))
        );
    }

    /**
     * 2 напиши метод, который выполняет переданное действие указанное число раз с паузой в 1 секунду
     * (размер паузы можно тоже параметризировать). Т.е. чтобы метод можно было вызвать следующим образом:
     * repeatNTimes(() -> element.click(), 5). P.S. это пример метода, который является не очень хорошей
     * практикой с точки зрения автоматизации (когда мы повторяем какое-то действие если оно не
     * выполнилось успешно по какой-то причине). Но, к сожалению, иногда без таких методов - никуда.
     * На нашем проекте мы просто согласуем командой каждое использование такого метода и пишем в коде
     * комент почему так сделано.
     * <p>
     * 3 модифицируй метод из подзадания 2 так, чтобы повтор шел только в случае возникновения ошибки.
     * Т.е. сценарий следующий: у нас есть некий нестабильный метод, который то работает то нет.
     * Мы пробуем выполнить его 3 раза, но если он прошел с 1-2 раза, то последующие уже выполнять не нужно.
     */
    public void repeatNTimes(Executable ex, int numTimes) {
        for (int i = 0; i < numTimes; i++) {
            try {
                ex.execute();
                Thread.sleep(1000);
                break;
            } catch (Throwable e) {
                System.out.println("inside catching of Throwable");
            }
        }
    }

    /**
     * Подзадание 2
     * написанный тобой метод можно вызвать следующим образом:
     * repeatNTimes(() -> element.click(), 4);
     * repeatNTimes(() -> element.sendKeys(“aaa”), 10);
     * repeatNTimes(() -> { element.click(); element.sendKeys(“aaa”);}, 2);
     * Поздадание 3
     * Для проверки напиши метод, который выбрасывает исключение в случайном порядке.
     * Для случайности можно либо использовать рандомизатор, либо использовать текущее время
     * (например выбрасывать исключение по четным миллисекундам)
     */
    @Test
    void testRepeatNTimes() {
        repeatNTimes(() -> {
                    open("https://www.google.com/");
                    int rando = java.time.LocalDateTime.now().getSecond();
                    System.out.println("rando = " + rando);
                    if (rando % 2 == 0 || rando % 3 == 0) {
                        WebDriverRunner.closeWebDriver();
                        System.out.println("assertion is thrown");
                        throw new AssertionError();
                    }
                },
                20);
    }
     /*
    4 в указанных заданиях ты скорее всего использовал Consumer<>.
    4.1 Изучи какие классы используются для:
    лямбды, возвращающей значение
    Ответ: BiFunction<T, U, R>, BinaryOperator<T>, BiPredicate<T, U>, BooleanSupplier, DoubleBinaryOperator,
    DoubleFunction<R>, DoublePredicate, DoubleSupplier, DoubleToIntFunction, DoubleToLongFunction, DoubleUnaryOperator,
    Function<T, R>, IntBinaryOperator, IntFunction<R>, IntPredicate, IntSupplier, IntToDoubleFunction, IntToLongFunction,
    IntUnaryOperator, LongBinaryOperator, LongConsumer, LongFunction<R>, LongPredicate, LongSupplier,
    LongToDoubleFunction, LongToIntFunction, LongUnaryOperator, Predicate<T>, Supplier<T>, ToDoubleBiFunction<T, U>,
    ToDoubleFunction<T>, ToIntBiFunction<T, U>, ToIntFunction<T>, oLongBiFunction<T, U>, ToLongFunction<T>,
    UnaryOperator<T>

    4.2 лямбды, возвращающей логическое значение,
    Ответ: BiPredicate<T, U>, BooleanSupplier, DoublePredicate, IntPredicate, LongPredicate, Predicate<T>,

    4.3 для каждого из случаев выше: какой класс использовать если нужно чтобы лямбда принимала не один аргумент,
    а два-три?
    Ответ: Вообще любой типизированный интерфейс может принимать массив аргументов, например BiConsumer<T, U>,
    BiFunction<T, U, R>, BinaryOperator<T>, BiPredicate<T, U>, Consumer<> и т.д.
     */
}
