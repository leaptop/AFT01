//package autotests;
//
//import org.junit.jupiter.api.*;
//
///**
// * На примере данного класса можно сделать вывод о вызовах методов:
// * @BeforeAll родителя
// * @BeforeAll потомка
// *
// * @BeforeEach родителя
// * @BeforeEach потомка
// * @Test
// * @AfterEach потомка
// * @AfterEach родителя
// *
// * @AfterAll потомка
// * @AfterAll родителя
// */
//public class SecondTest extends FirstTest {
//    @BeforeAll
//    static void beforeAllSecond() {
//        System.out.println("SecondTest start");
//    }
//
//    @BeforeEach
//    void beforeEachSecond() {
//        System.out.println("Тест запущен");
//    }
//
//    @AfterEach
//    void afterEachSecond() {
//        System.out.println("Тест остановлен");
//    }
//
//
//    @AfterAll
//    static void afterAllSecond() {
//        System.out.println("SecondTest stop");
//    }
//
//    @Test
//    void test6() {
//        System.out.println("Test 6");
//    }
//
//    @Test
//    void test7() {
//        System.out.println("Test 7");
//    }
//}
