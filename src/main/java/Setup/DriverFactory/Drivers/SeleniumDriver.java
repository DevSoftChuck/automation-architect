package Setup.DriverFactory.Drivers;

import Setup.DriverFactory.Drivers.Platforms.GridSeleniumPlatform;
import Setup.DriverFactory.Drivers.Platforms.LocalSeleniumPlatform;
import Setup.DriverFactory.Drivers.Platforms.SaucelabSeleniumPlatform;
import Setup.TestEnvironment;
import org.openqa.selenium.WebDriver;

public class SeleniumDriver implements Driver{

    @Override
    public WebDriver createDriver() {
        switch (TestEnvironment.getPlatform().toLowerCase()) {
            case "local":
                return new LocalSeleniumPlatform().startBrowser();
            case "grid":
                return new GridSeleniumPlatform().startBrowser();
            case "saucelab":
                return new SaucelabSeleniumPlatform().startBrowser();
            default:
                throw new IllegalStateException("This platform: "+ TestEnvironment.getPlatform().toLowerCase() +", is not supported yet!");
        }
    }
}
