package helpers;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.util.Optional;

/**
 * Класс для более тонкой обработки результатов выполнения тестов.
 * Закрывает вебдрайвер здесь, т.к. методы TestWatcher выполняются только
 * после всех @AfterAll, @AfterEach (если закрыть в них, то здесь
 * вебдрайвер уже будет обнулён).
 *
 * @author Алексеев Степан
 * @date 28.12.2022
 */
public class MyTestWatcher implements TestWatcher {
    WebDriver driver;

    /**
     * Получает ссылку на вебдрайвер из базового для тестового класса.
     */
    private void initWebDriver(ExtensionContext extensionContext) {
        Object test = extensionContext.getRequiredTestInstance();
        Field field = null;
        try {
            field = test
                    .getClass()
                    .getSuperclass()
                    .getDeclaredField("chromedriver");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        field.setAccessible(true);

        try {
            driver = (WebDriver) field.get(test);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Вызывается если тест прерывается
     * @param extensionContext
     * @param throwable
     */
    @Override
    public void testAborted(ExtensionContext extensionContext, Throwable throwable) {
        initWebDriver(extensionContext);
        System.out.println("MyTestWatcher.testAborted");
        System.out.println("extensionContext = " + extensionContext);
        System.out.println("throwable = " + throwable);
        if (driver != null)
            driver.quit();
    }

    /**
     * Вызывается если тест сломан
     * @param extensionContext
     * @param optional
     */
    @Override
    public void testDisabled(ExtensionContext extensionContext, Optional<String> optional) {
        initWebDriver(extensionContext);
        System.out.println("MyTestWatcher.testDisabled");
        System.out.println("extensionContext = " + extensionContext);
        System.out.println("optional = " + optional);
        if (driver != null)
            driver.quit();
    }

    /**
     * Вызывается если тест упал
     * @param extensionContext
     * @param throwable
     */
    @Override
    public void testFailed(ExtensionContext extensionContext, Throwable throwable) {
        initWebDriver(extensionContext);
        System.out.println("Тест провален. Вызван метод MyTestWatcher" +
                ".testFailed()");
        System.out.println("extensionContext = " + extensionContext);
        System.out.println("throwable = " + throwable);
        Allure.addAttachment("Тест упал",
                new ByteArrayInputStream(((TakesScreenshot) driver)
                        .getScreenshotAs(OutputType.BYTES)));
        if (driver != null)
            driver.quit();
    }

    /**
     * Вызывается если тест успешен.
     * @param extensionContext
     */
    @Override
    public void testSuccessful(ExtensionContext extensionContext) {
        initWebDriver(extensionContext);
        System.out.println("MyTestWatcher.testSuccessful");
        System.out.println("extensionContext = " + extensionContext);
        if (driver != null)
            driver.quit();
    }
}