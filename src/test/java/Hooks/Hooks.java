package Hooks;

import Setup.DriverFactory.DriverFactory;
import Setup.SlackLogger;
import Setup.TestEnvironment;
import Utils.Utils;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.MDC;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class Hooks extends TestEnvironment{
    private final SlackLogger slackLogger = new SlackLogger();

    @BeforeClass(alwaysRun = true)
    public void onStart() {
        deleteOldLogs();
    }

    @AfterClass(alwaysRun = true)
    public void onFinish() {
        allureWriteExecutors();
        allureWriteProperties();
        testResultsCleaner();
    }

    @Before()
    public void beforeScenario(Scenario scenario) {
        if(scenario.getName().contains("API -")){
            setupRestAssured("https://reqres.in");
        }else{
            MDC.put("testid", scenario.getName().toUpperCase());
            messageStartScenario(scenario);
            DriverFactory.initialize();
        }
    }

    @After()
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            failedTestsAmount++;
        }else{
            passedTestsAmount++;
        }

        if(!scenario.getName().contains("API -")){
            messageFinishScenario(scenario);
            MDC.remove("testid");
            DriverFactory.getDriver().quit();
            DriverFactory.removeDriver();
        }
        //        slackLogger.sendTestExecutionStatusToSlack(iTestContext);
//        testResultsCleaner();
    }

    @AfterStep()
    public void afterStep(Scenario scenario) throws Exception {
        if (scenario.isFailed() && !scenario.getName().contains("API_")) {
            String screenshotName = scenario.getName().replaceAll(" ", "_")
                    .concat(String.valueOf(Utils.parser("${S8}")));
            byte[] screenshot = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", screenshotName);

            allureSaveTextLogCucumber(scenario);
            allureSaveScreenshotPNG();
        }
    }
}
