package Setup.Listeners;

import Setup.*;
import io.cucumber.java.Scenario;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.ArrayList;
import java.util.List;

public class TestNGListener extends TestEnvironment implements ITestListener {

    public static List<String> passedTests = new ArrayList<>();
    public static List<String> failedTests = new ArrayList<>();

    private final SlackLogger slackLogger = new SlackLogger();
    private final MessageBuilder messageBuilder = new MessageBuilder();

    /**
     * For TestNG tests
     */
    @Override
    public synchronized void onStart(ITestContext iTestContext) {
        deleteOldLogs();
        messageBuilder.messageStartSuite(iTestContext);
    }

    @Override
    public synchronized void onFinish(ITestContext iTestContext) {
        messageBuilder.messageEndSuite(iTestContext);
        allureWriteExecutors();
        allureWriteProperties();
//        ContextInjection.passedTestsAmount = passedTests.size();
//        ContextInjection.failedTestsAmount = failedTests.size();
        slackLogger.sendTestExecutionStatusToSlack(iTestContext);
        suiteResultsCleaner();
    }

    @Override
    public synchronized void onTestStart(ITestResult iTestResult) {
        messageBuilder.messageStartTest(iTestResult);
    }

    @Override
    public synchronized void onTestSuccess(ITestResult iTestResult) {
        messageBuilder.messageSuccessTest();
        passedTests.add(MessageBuilder.getTestDescription(iTestResult));
    }

    @Override
    public synchronized void onTestFailure(ITestResult iTestResult) {
        messageBuilder.messageFailTest();
        failedTests.add(MessageBuilder.getTestDescription(iTestResult));
        logger.error(String.valueOf(iTestResult.getThrowable()));
        if (SeleniumDriver.getDriver() != null) {
            allureSaveScreenshotPNG();
        }
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    /**
     * For Cucumber tests
     **/
    public void onScenarioStart(Scenario scenario) {
        messageBuilder.messageStartScenario(scenario);
    }

    public void onScenarioFinish(Scenario scenario) {
        messageBuilder.messageFinishScenario(scenario);
    }

    private void setExcelCucumberTestsResult(Scenario scenario) throws Exception {
        if (scenario.isFailed()) {
            localSaveScreenshotPNG(scenario);
            allureSaveScreenshotPNG();
            allureSaveTextLogCucumber(scenario);
        }
    }
}
