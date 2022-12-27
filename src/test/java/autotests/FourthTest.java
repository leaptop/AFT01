package autotests;

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
 *
 * @author Алексеев Степан
 * @date 16.12.2022
 */
public class FourthTest {

    // @BeforeEach
    void beforeD() {
        System.out.println("выполнения ");
    }

    @BeforeEach
    void beforeE() {
        beforeD();
        System.out.println("нескольких ");
    }

    @BeforeAll
    static void beforeB() {
        System.out.println("Порядок");
    }

    @AfterEach
    void beforeA() {
        System.out.println("Before ");
        beforeF();
    }

    //@BeforeEach
    void beforeF() {
        System.out.println("методов ");
    }

    @AfterAll
    static void beforeC() {
        System.out.println("недетерминирован");
    }

    @Test
    void test10() {
        System.out.print("");
    }
}
