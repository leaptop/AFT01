import org.junit.jupiter.api.*;

/**
 * Задание 3.4.c : методы @BeforeEach выводятся в том порядке, в котором
 * я их определил.
 * Задание 3.4.e поставил определение before1 после before3. В итоге текст
 * вывелся в таком же порядке, как в 3.4.c до перемещения... Видимо, их порядок
 * устанавливается изначально и дальше может меняться (а может и не меняться)
 * на усмотрение JVM.
 * Задание 3.4.g впечатление, что JVM решает запускать тесты, сначала
 * отсортировав их по названию в лексикографическом порядке. На самом деле судя
 * по документации там по-другому сделано: https://github.com/junit-team/junit5/issues/2990#issuecomment-1207248587
 */
public class FourthTest {

    static void beforeD() {
        System.out.println("выполнения ");
    }

    static void beforeE() {
        System.out.println("нескольких ");
    }

    /**
     * Порядок вызова методов важен!
     */
    @BeforeAll
    static void beforeB() {
        System.out.println("Порядок");
        beforeD();
        beforeE();
        beforeA();
        beforeF();
        beforeC();
    }

    static void beforeA() {
        System.out.println("Before ");
    }

    static void beforeF() {
        System.out.println("методов ");
    }

    static void beforeC() {
        System.out.println("недетерминирован");
    }

    @Test
    void test10() {
        System.out.print("");
    }
}
