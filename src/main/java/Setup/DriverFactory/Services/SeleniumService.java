package Setup.DriverFactory.Services;

import Setup.DriverFactory.Drivers.Driver;
import Setup.DriverFactory.Drivers.SeleniumDriver;

public class SeleniumService extends Service{

    @Override
    public Driver initializeDriver() {
        return new SeleniumDriver();
    }
}