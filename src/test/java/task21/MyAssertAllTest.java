package task21;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

/**
 * @author Алексеев Степан
 * @date 19.01.2023
 */
public class MyAssertAllTest {
    public static void myAssertAll() {
        SoftAssert softAssert = new SoftAssert();

    }

    @Test
    public void testAssertAll() {
        MyAssertAllTest MyAssertAllImpl = new MyAssertAllTest();
        MyAssertAllImpl.myAssertAll(
            soft -> soft.assertEquals(1, 1),
                    soft -> soft.assertEquals(2, 1),
                    soft -> soft.assertEquals(3, 1));
    }
}
