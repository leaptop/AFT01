package autotests;

import org.junit.jupiter.api.*;


public class FirstTest {
    @BeforeAll
    static void beforeAll() {
        System.out.println("autotests.FirstTest class started (works for all descendands)");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("Тест запущен");
    }

    @AfterEach
    void afterEach() {
        System.out.println("Тест остановлен");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("All tests in autotests.FirstTest finished");
    }

    @Test
    void myTest() {
        System.out.println("My first autotest running");
    }

    @Test
    void test1() {
        System.out.println("Test № 1");
    }

    @Test
    void test2() {
        System.out.println("Test № 2");
    }

    @Test
    void test3() {
        System.out.println("Test № 3");
    }

    @Test
    void test4() {
        System.out.println("Test № 4");
    }

    @Test
    void test5() {
        System.out.println("Test № 5");
    }
}
