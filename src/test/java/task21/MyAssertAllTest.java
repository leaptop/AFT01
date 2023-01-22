package task21;

import org.junit.jupiter.api.function.Executable;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

/**
 * 1 напиши софт-ассерт для TestNG в стиле assertAll JUnit-а. Т.е. для TestNG у тебя должен быть метод,
 * который принимает список проверок и падает после выполнения всех проверок, а не после первой.
 * P.S. желательно не подглядывать в реализацию JUnit-а.
 * Пример вызова метода:
 *
 * @author Алексеев Степан
 * @date 19.01.2023
 */

interface MyAssertInterface<T> {
    void myAssertAll(T... arr) throws Throwable;
}

public class MyAssertAllTest {
    MyAssertInterface<Executable> initInterface() {
        return (Executable... executables) -> {
            SoftAssert soft = new SoftAssert();
            for (int i = 0; i < executables.length; i++) {
                executables[i].execute();
            }
            soft.assertAll();
        };
    }

    @Test
    public void testAssertAll() throws Throwable {
        SoftAssert soft = new SoftAssert();
        MyAssertInterface<Executable> operationAssert;// = initInterface();
        operationAssert = (Executable... executables) -> {
            for (int i = 0; i < executables.length; i++) {
                executables[i].execute();
            }
            soft.assertAll();
        };
        operationAssert.myAssertAll(
                () -> soft.assertEquals(1, 1),
                () -> soft.assertEquals(2, 1),
                () -> soft.assertEquals(3, 3),
                () -> soft.assertEquals(5, 4),
                () -> soft.assertTrue(55 < 4, "проверка через assertTrue")
        );
//        MyAssertAllTest MyAssertAllImpl = new MyAssertAllTest();
//        MyAssertAllImpl.myAssertAll(
//                soft -> soft.assertEquals(1, 1),
//                soft -> soft.assertEquals(2, 1),
//                soft -> soft.assertEquals(3, 1)
//        );
    }

    @Test
    void checkTestNGAsserts() {
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(2, 3);
        soft.assertTrue(true);
        soft.assertAll();
    }

//    public static void myAssertAll(Assertion a) {
//        SoftAssert softAssert = new SoftAssert();
//        Class<? extends Assert> aClass = ats.getClass();
//
//    }
}
