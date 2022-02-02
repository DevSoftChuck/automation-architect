package Setup.DriverFactory.Drivers.Platforms;

import Setup.TestEnvironment;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class GridSeleniumPlatform{

    public WebDriver startBrowser() {
        return getDriverForRemoteBrowser(TestEnvironment.getBrowser().toLowerCase());
    }

    public static RemoteWebDriver getDriverForRemoteBrowser(String browserName) {
        switch (browserName.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--lang=en");
                chromeOptions.addArguments("--disable-extensions");
                chromeOptions.addArguments("--disable-popup-blocking");
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--start-maximized");
                try {
                    return new RemoteWebDriver(new URL(TestEnvironment.SELENIUM_GRID_URL), chromeOptions);
                } catch (MalformedURLException e) {
                    TestEnvironment.logger.error("Failed to launch Chrome remote driver!", e);
                }
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                //Setting Profile for firefox
                FirefoxProfile profile = new FirefoxProfile();
                profile.setPreference("intl.accept_languages","en-us");
                profile.setPreference("permissions.default.desktop-notification", 2);
                firefoxOptions.setProfile(profile);
                try {
                    return new RemoteWebDriver(new URL(TestEnvironment.SELENIUM_GRID_URL), firefoxOptions);
                } catch (MalformedURLException e) {
                    TestEnvironment.logger.error("Failed to launch Firefox remote driver!", e);
                }
            default:
                throw new IllegalStateException("This browser: "+ TestEnvironment.getBrowser().toLowerCase() +", is not supported yet!");
        }
    }
}
