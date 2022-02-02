package Setup.DriverFactory.Services;

import Setup.DriverFactory.Drivers.AppiumDriver;
import Setup.DriverFactory.Drivers.Driver;

public class AppiumService extends Service{
    @Override
    public Driver initializeDriver() {
        return new AppiumDriver();
    }
}
