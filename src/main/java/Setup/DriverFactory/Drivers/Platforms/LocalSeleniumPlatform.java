package Setup.DriverFactory.Drivers.Platforms;

import Setup.TestEnvironment;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

public class LocalSeleniumPlatform {

    public WebDriver startBrowser() {
        switch (TestEnvironment.getBrowser().toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--lang=en");
                chromeOptions.addArguments("--disable-extensions");
                chromeOptions.addArguments("--disable-popup-blocking");
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--start-maximized");
                return new ChromeDriver(chromeOptions);
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();

                //Setting Profile for firefox
                FirefoxProfile profile = new FirefoxProfile();
                profile.setPreference("intl.accept_languages", "en-us");
                profile.setPreference("permissions.default.desktop-notification", 2);
                firefoxOptions.setProfile(profile);
                return new FirefoxDriver(firefoxOptions);
            case "opera":
                WebDriverManager.operadriver().setup();
                OperaOptions operaOptions = new OperaOptions();
                return new OperaDriver(operaOptions);
            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                return new EdgeDriver(edgeOptions);
            case "ie":
                WebDriverManager.iedriver().setup();
                InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions();
                return new InternetExplorerDriver(internetExplorerOptions);
            case "safari":
                SafariOptions safariOptions = new SafariOptions();
                return new SafariDriver(safariOptions);
            default:
                throw new IllegalStateException("This browser: "+ TestEnvironment.getBrowser().toLowerCase() +", is not supported yet!");
        }
    }
}
