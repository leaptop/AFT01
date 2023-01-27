package task22.helpers;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Класс для более тонкой обработки результатов выполнения тестов.
 * Закрывает вебдрайвер здесь, т.к. методы ITestListener выполняются только
 * после всех @AfterClass, @AfterMethod (если закрыть в них, то здесь
 * вебдрайвер уже будет обнулён).
 *
 * @author Алексеев Степан
 * @date 20.01.2023
 */
public class MyTestListener implements ITestListener {
    private void finishWebDriver(){
        WebDriverRunner.clearBrowserCache();
        WebDriverRunner.closeWebDriver();
    }
    @Attachment
    public byte[] saveFailureScreenShot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        System.out.println("test started");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        finishWebDriver();
        System.out.println("onTestSuccess");
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        System.out.println("onTestFailure");
        WebDriver driver = WebDriverRunner.driver().getWebDriver();
        saveFailureScreenShot(driver);
        finishWebDriver();
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        finishWebDriver();
        System.out.println("onTestSkipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        System.out.println("onTestFailedButWithinSuccessPercentage");
        finishWebDriver();
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        System.out.println("onStart");
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        System.out.println("onFinish");
        finishWebDriver();
    }
}