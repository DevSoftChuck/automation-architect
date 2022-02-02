package Steps.Runner;

import Setup.DriverFactory.DriverFactory;
import Setup.SlackLogger;
import Setup.TestEnvironment;
import Utils.Utils;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.junit.Cucumber;
import io.cucumber.testng.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.MDC;
import org.testng.annotations.*;
import io.cucumber.testng.AbstractTestNGCucumberTests;

/**
 * @author Ivan Andraschko
 **/

@RunWith(Cucumber.class)
@CucumberOptions(
		features = {"src/test/java/Features"},
		glue = {"Steps"},
		tags = "@solo",
		plugin = {	"pretty",
					"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
					"io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"})
public class CucumberRunner extends AbstractTestNGCucumberTests {

	private final SlackLogger slackLogger = new SlackLogger();
	private final TestEnvironment testEnvironment = new TestEnvironment();

	@Override
	@DataProvider(parallel = true)
	public Object[][] scenarios() {
		return super.scenarios();
	}

	@BeforeClass(alwaysRun = true)
	public void onStart() {
		testEnvironment.deleteOldLogs();
	}

	@AfterClass(alwaysRun = true)
	public void onFinish() {
		testEnvironment.allureWriteExecutors();
		testEnvironment.allureWriteProperties();
		testEnvironment.testResultsCleaner();
	}

	@Before()
	public void beforeScenario(Scenario scenario) {
		if(scenario.getName().contains("API -")){
			testEnvironment.setupRestAssured("https://reqres.in");
		}else{
			MDC.put("testid", scenario.getName().toUpperCase());
			testEnvironment.messageStartScenario(scenario);
			DriverFactory.initialize();
		}
	}

	@After()
	public void afterScenario(Scenario scenario) {
		if (scenario.isFailed()) {
			TestEnvironment.failedTestsAmount++;
		}else{
			TestEnvironment.passedTestsAmount++;
		}

		if(!scenario.getName().contains("API -")){
			testEnvironment.messageFinishScenario(scenario);
			MDC.remove("testid");
			DriverFactory.getDriver().quit();
			DriverFactory.removeDriver();
		}
		//        slackLogger.sendTestExecutionStatusToSlack(iTestContext);
		testEnvironment.testResultsCleaner();
	}

	@AfterStep()
	public void afterStep(Scenario scenario) throws Exception {
		if (scenario.isFailed() && !scenario.getName().contains("API_")) {
			String screenshotName = scenario.getName().replaceAll(" ", "_")
					.concat(String.valueOf(Utils.parser("${S8}")));
			byte[] screenshot = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
			scenario.attach(screenshot, "image/png", screenshotName);

			testEnvironment.allureSaveTextLogCucumber(scenario);
			testEnvironment.allureSaveScreenshotPNG();
		}
	}

}
