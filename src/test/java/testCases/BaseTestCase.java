package testCases;

import org.testng.annotations.AfterSuite;
import setup.DriverFactory;
import io.qameta.allure.Attachment;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import setup.TestEnvironment;

import java.lang.reflect.Method;

public class BaseTestCase {

    @AfterSuite
    public void afterFinish(){
        TestEnvironment.allureWriteProperties();
    }

    @BeforeMethod(alwaysRun = true, description = "Setting up test class")
    public void beforeTest(Method method){
        String fullClassName = this.getClass().getName();
        int index = fullClassName.lastIndexOf('.');
        String className = index == -1 ? this.getClass().getName() : fullClassName.substring(index + 1);
        DriverFactory.initialize(className + "#" + method.getName());
    }

    @AfterMethod(alwaysRun = true)
    public void afterTest(ITestResult result, Method method){
        switch (result.getStatus()){
            case ITestResult.FAILURE -> {
                if (DriverFactory.getDriver() != null){
                    try { takesScreenShot(); }catch (NoSuchWindowException ignore){}
                }
                TestEnvironment.failedTestsAmount += 1;
                messageFailTest(result);
            }
            case ITestResult.SUCCESS -> {
                TestEnvironment.passedTestsAmount += 1;
                messageSuccessTest(result);
            }
        }
        DriverFactory.removeDriver();
    }

    @Attachment(value = "Scenario FAIL screenshot", type = "type/png")
    public static byte[] takesScreenShot(){
        return ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    public void messageSuccessTest(ITestResult iTestResult) {
        TestEnvironment.logger.info(TestEnvironment.ANSI_BLUE + "TEST NAME: " +
                iTestResult.getMethod().getDescription().toUpperCase() + TestEnvironment.ANSI_RESET +
                " FINISHED WITH " + TestEnvironment.ANSI_GREEN + "SUCCESS STATUS " + TestEnvironment.ANSI_RESET);
    }

    public void messageFailTest(ITestResult iTestResult) {
        TestEnvironment.logger.error(TestEnvironment.ANSI_BLUE + "TEST NAME: " +
                iTestResult.getMethod().getDescription().toUpperCase() + TestEnvironment.ANSI_RESET +
                " FINISHED WITH " + TestEnvironment.ANSI_RED + "FAILED STATUS " + TestEnvironment.ANSI_RESET);
    }
}
