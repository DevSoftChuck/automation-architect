package Setup.DriverFactory;

import Setup.DriverFactory.Services.AppiumService;
import Setup.DriverFactory.Services.SeleniumService;
import Setup.DriverFactory.Services.Service;
import Setup.TestEnvironment;
import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class DriverFactory {

    private static final List<WebDriver> storedDrivers = new ArrayList<>();
    private static final ThreadLocal<WebDriver> drivers = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return drivers.get();
    }

    private static void addDriver(WebDriver driver) {
        storedDrivers.add(driver);
        drivers.set(driver);
    }

    public static void destroyDriver() {
        for (WebDriver driver : storedDrivers) {
            if (driver != null) {
                driver.quit();
            }
        }
    }

    public static void removeDriver() {
        storedDrivers.remove(drivers.get());
        drivers.remove();
    }

    /**
     * The concrete factory is usually chosen depending on configuration or
     * environment options.
     */
    public static void initialize() {

        Service service;
        switch (TestEnvironment.getService().toLowerCase()) {
            case "selenium":
                service = new SeleniumService();
                addDriver(service.createDriver());
                getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestEnvironment.PAGE_LOAD_TIMEOUT));
                getDriver().manage().timeouts().scriptTimeout(Duration.ofSeconds(TestEnvironment.SCRIPT_TIMEOUT));
                getDriver().manage().deleteAllCookies();
                getDriver().manage().window().maximize();
                break;
            case "appium":
                service = new AppiumService();
                addDriver(service.createDriver());
                break;
            default:
                throw new IllegalStateException("This service: "+TestEnvironment.getService().toLowerCase()+", is not supported yet!");
        }

    }

}
