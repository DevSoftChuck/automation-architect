package Setup.DriverFactory.Services;

import Setup.DriverFactory.Drivers.Driver;
import org.openqa.selenium.WebDriver;

public abstract class Service {

    public abstract Driver initializeDriver();

    public WebDriver createDriver() {
        Driver driver = initializeDriver();
        return driver.createDriver();
    }

}
