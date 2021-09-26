package Steps.Runner;

import Setup.Listeners.TestNGListener;
import Setup.SeleniumDriver;
import Setup.TestEnvironment;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.testng.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.MDC;
import org.testng.annotations.*;
import Utils.Utils;

@CucumberOptions(
		features = {"src/test/java/Features"},
		glue = {"Steps"},
		tags = "@test",
		plugin = {	"pretty",
					"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
					"json:target/json-report/cucumber.json"})
public class CucumberRunner extends AbstractTestNGCucumberTests {

	private TestNGCucumberRunner testNGCucumberRunner;
	private final TestNGListener testNGListener = new TestNGListener();

	private final TestEnvironment testEnvironment = new TestEnvironment();

	@BeforeClass(alwaysRun = true)
	public void setUpClass() {
		testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
	}

	@Test(dataProvider = "features", priority = 0)
	public void runTests(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
		testNGCucumberRunner.runScenario(pickleWrapper.getPickle());
	}

	@DataProvider(name = "features", parallel = true)
	public Object[][] features() {
		return testNGCucumberRunner.provideScenarios();
	}

	@Before()
	public void beforeScenario(Scenario scenario) {
		MDC.put("testid", scenario.getName().toUpperCase());
		testNGListener.onScenarioStart(scenario);
		SeleniumDriver.startBrowser();
	}

	@After()
	public void afterScenario(Scenario scenario) {
		testNGListener.onScenarioFinish(scenario);
		MDC.remove("testid");
//		SeleniumDriver.destroyDriver();
		SeleniumDriver.getDriver().quit();
		SeleniumDriver.removeDriver();
	}

	@AfterStep()
	public void takeScreenShot(Scenario scenario) throws Exception {
		String screenshotName = scenario.getName().replaceAll(" ", "_")
				.concat(String.valueOf(Utils.parser("${S8}")));

		if (scenario.isFailed()) {
			byte[] screenshot = ((TakesScreenshot) SeleniumDriver.getDriver()).getScreenshotAs(OutputType.BYTES);
			scenario.attach(screenshot, "image/png", screenshotName);
		}
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		if (testNGCucumberRunner == null) {
			return;
		}
		testNGCucumberRunner.finish();
		testEnvironment.allureWriteProperties();
		testEnvironment.allureWriteExecutors();
	}

}
