package testCases;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import setup.DriverFactory;
import io.qameta.allure.Attachment;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import setup.TestEnvironment;
import utils.CommandExecutor;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class BaseTestCase {

    @AfterSuite(alwaysRun = true)
    protected void afterFinish() throws InterruptedException {
        TestEnvironment.allureWriteProperties();
        CommandExecutor.executeCommand("allure generate allure-results --clean")
                .waitFor(5, TimeUnit.SECONDS);
    }

    @BeforeSuite(alwaysRun = true)
    protected void beforeStart(){
        deleteFolder("allure-report");
        deleteFolder("allure-results");
    }

    @BeforeMethod(alwaysRun = true, description = "Setting up test class")
    protected void beforeTest(Method method){
        System.out.println("Before method within BaseTestCase");

        String fullClassName = this.getClass().getName();
        int index = fullClassName.lastIndexOf('.');
        String className = index == -1 ? this.getClass().getName() : fullClassName.substring(index + 1);
        DriverFactory.initialize(className + "#" + method.getName());
    }

    @AfterMethod(alwaysRun = true)
    protected void afterTest(ITestResult result, Method method){
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
    protected static byte[] takesScreenShot(){
        return ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    protected static String getCurrentPath() {
        return Paths.get(".").toAbsolutePath().normalize().toString();
    }

    protected void deleteFolder(String folderName) {
        try {
            FileUtils.deleteDirectory(new File(getCurrentPath()
                    + File.separator
                    + folderName));
        } catch (IOException e) {
            TestEnvironment.logger.error("Failed to delete " + folderName + " directory!", e);
        }
    }

    protected void messageSuccessTest(ITestResult iTestResult) {
        TestEnvironment.logger.info(TestEnvironment.ANSI_BLUE + "TEST NAME: " +
                iTestResult.getMethod().getDescription().toUpperCase() + TestEnvironment.ANSI_RESET +
                " FINISHED WITH " + TestEnvironment.ANSI_GREEN + "SUCCESS STATUS " + TestEnvironment.ANSI_RESET);
    }

    protected void messageFailTest(ITestResult iTestResult) {
        TestEnvironment.logger.error(TestEnvironment.ANSI_BLUE + "TEST NAME: " +
                iTestResult.getMethod().getDescription().toUpperCase() + TestEnvironment.ANSI_RESET +
                " FINISHED WITH " + TestEnvironment.ANSI_RED + "FAILED STATUS " + TestEnvironment.ANSI_RESET);
    }
}
