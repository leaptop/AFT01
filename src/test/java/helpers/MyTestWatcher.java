package helpers;

import com.codeborne.selenide.WebDriverRunner;
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
     * Включать в начало методов ниже, если используется Селениум.
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
     * Если используется Селениум, то включать вызов этого метода в конце каждого из: testAborted, testDisabled и т.д.
     */
    public void closeWebDriver() {
        if (driver != null)
            driver.quit();
    }

    /**
     * Вызывается если тест прерывается
     *
     * @param extensionContext
     * @param throwable
     */
    @Override
    public void testAborted(ExtensionContext extensionContext, Throwable throwable) {
        System.out.println("MyTestWatcher.testAborted");
        System.out.println("extensionContext = " + extensionContext);
        System.out.println("throwable = " + throwable);
        WebDriverRunner.closeWebDriver();
    }

    /**
     * Вызывается если тест сломан
     *
     * @param extensionContext
     * @param optional
     */
    @Override
    public void testDisabled(ExtensionContext extensionContext, Optional<String> optional) {
        System.out.println("MyTestWatcher.testDisabled");
        System.out.println("extensionContext = " + extensionContext);
        System.out.println("optional = " + optional);
        WebDriverRunner.closeWebDriver();
    }

    /**
     * Вызывается если тест упал
     *
     * @param extensionContext
     * @param throwable
     */
    @Override
    public void testFailed(ExtensionContext extensionContext, Throwable throwable) {
        System.out.println("Тест провален. Вызван метод MyTestWatcher" +
                ".testFailed()");
        System.out.println("extensionContext = " + extensionContext);
        System.out.println("throwable = " + throwable);
        Allure.addAttachment("Тест упал",//это добавление скриншота работает при использовании Селениума
                new ByteArrayInputStream(((TakesScreenshot) driver)
                        .getScreenshotAs(OutputType.BYTES)));
        WebDriverRunner.closeWebDriver();
    }

    /**
     * Вызывается если тест успешен.
     *
     * @param extensionContext
     */
    @Override
    public void testSuccessful(ExtensionContext extensionContext) {
        System.out.println("MyTestWatcher.testSuccessful");
        System.out.println("extensionContext = " + extensionContext);
        WebDriverRunner.closeWebDriver();
    }
}