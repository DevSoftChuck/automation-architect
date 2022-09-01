package testCases;

import models.csvData.CheckoutData;
import models.csvData.CsvBean;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.*;
import setup.DriverFactory;
import io.qameta.allure.Attachment;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import setup.TestEnvironment;
import utils.CommandExecutor;
import utils.Utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BaseTestCase {

    /* --------------------------------------------------- HOOKS ---------------------------------------------------- */

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
                messageFailTest(result);
            }
            case ITestResult.SUCCESS -> {
                messageSuccessTest(result);
            }
        }
        DriverFactory.removeDriver();
    }

    /* ------------------------------------------------ UTIL METHODS ------------------------------------------------ */

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

    /* ----------------------------------------------- DATA PROVIDERS ----------------------------------------------- */

    @DataProvider(name = "checkout")
    protected Object[][] checkoutData(){
        Path path = Paths.get("src/test/resources/csv/checkoutData.csv");
        //for each row within our csv file, we will create a POJO class of it and save it into an array
        List<CsvBean> lista = Utils.injectCsvFileToPojo(path, CheckoutData.class);

        // In order to fill our test method with this data, lets put each row from our array into another but accepted
        // testng array
        Object[][] data = new Object[lista.size()][1];
        for (int i = 0; i < lista.size(); i++) {
            data[i] = new Object[] {lista.get(i)};
        }
        return data;
    }
}
