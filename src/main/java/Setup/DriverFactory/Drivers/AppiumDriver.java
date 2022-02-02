package Setup.DriverFactory.Drivers;

import Setup.DriverFactory.Drivers.Platforms.*;
import Setup.TestEnvironment;
import org.openqa.selenium.WebDriver;

public class AppiumDriver implements Driver{

    @Override
    public WebDriver createDriver() {
        switch (TestEnvironment.getPlatform().toLowerCase()) {
            case "local":
                return new LocalAppiumPlatform().startApplication();
            case "saucelab":
                return new SaucelabAppiumPlatform().startApplication();
            default:
                throw new IllegalStateException("This platform: "+ TestEnvironment.getPlatform().toLowerCase() +", is not supported yet!");
        }
    }
}
