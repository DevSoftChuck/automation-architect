package testCases;

import Setup.DriverFactory;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

public class BaseTestCase {

    @BeforeMethod(alwaysRun = true, description = "Setting up test class")
    public void beforeTest(Method method){
        String fullClassName = this.getClass().getName();
        int index = fullClassName.lastIndexOf('.');
        String className = index == -1 ? this.getClass().getName() : fullClassName.substring(index +1);
        System.setProperty("jobName", className + "#" + method.getName());

        DriverFactory.initialize(System.getProperty("jobName"));
    }

    @AfterMethod(alwaysRun = true)
    public void afterTest(ITestResult result, Method method){
        if(result.getStatus() == ITestResult.FAILURE && DriverFactory.getDriver() != null){
            takesScreenShot();
        }
        DriverFactory.getDriver().close();
        DriverFactory.removeDriver();
    }

    @AfterSuite(alwaysRun = true, description = "Teardown Test Suite")
    public void afterSuite(){
        DriverFactory.destroyDriver();
    }

    @Attachment(value = "Scenario FAIL screenshot", type = "type/png")
    public static byte[] takesScreenShot(){
        return ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
    }
}
