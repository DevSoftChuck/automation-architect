package Setup;

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
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SeleniumDriver extends TestEnvironment {

    protected static WebDriver driver;
    private static final List<WebDriver> storedDrivers = new ArrayList<>();
    private static final ThreadLocal<WebDriver> drivers = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return drivers.get();
    }

    private static void addDriver(WebDriver driver) {
        storedDrivers.add(driver);
        drivers.set(driver);
    }

    public static String getExecutor() {
        String executor = System.getProperty("executor");
        if (executor == null) {
            executor = System.getenv("executor");
            if (executor == null) {
                executor = DEFAULT_BROWSER;
            }
        }
        return executor;
    }

    public static String getService() {
        String service = System.getProperty("service");
        if (service == null) {
            service = System.getenv("service");
            if (service == null) {
                service = DEFAULT_SERVICE;
            }
        }
        return service;
    }

    public static void startBrowser() {
        switch (getService().toLowerCase()) {
            case "local":
                switch (getExecutor().toLowerCase()) {
                    case "chrome":
                        WebDriverManager.chromedriver().setup();
                        ChromeOptions chromeOptions = new ChromeOptions();
                        chromeOptions.addArguments("--lang=en");
                        chromeOptions.addArguments("--disable-extensions");
                        chromeOptions.addArguments("--disable-popup-blocking");
                        chromeOptions.addArguments("--disable-notifications");
                        chromeOptions.addArguments("--start-maximized");
                        addDriver(new ChromeDriver(chromeOptions));
                        break;
                    case "firefox":
                        WebDriverManager.firefoxdriver().setup();
                        FirefoxOptions firefoxOptions = new FirefoxOptions();

                        //Setting Profile for firefox
                        FirefoxProfile profile = new FirefoxProfile();
                        profile.setPreference("intl.accept_languages", "en-us");
                        profile.setPreference("permissions.default.desktop-notification", 2);
                        firefoxOptions.setProfile(profile);
                        addDriver(new FirefoxDriver(firefoxOptions));
                        break;
                    case "opera":
                        WebDriverManager.operadriver().setup();
                        OperaOptions operaOptions = new OperaOptions();
                        addDriver(new OperaDriver(operaOptions));
                        break;
                    case "edge":
                        WebDriverManager.edgedriver().setup();
                        EdgeOptions edgeOptions = new EdgeOptions();
                        addDriver(new EdgeDriver(edgeOptions));
                        break;
                    case "ie":
                        WebDriverManager.iedriver().setup();
                        InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions();
                        addDriver(new InternetExplorerDriver(internetExplorerOptions));
                        break;
                    case "safari":
                        SafariOptions safariOptions = new SafariOptions();
                        addDriver(new SafariDriver(safariOptions));
                        break;
                    default:
                        throw new IllegalStateException("This executor: "+ getExecutor().toLowerCase() +", is not supported yet!");
                }
                break;
            case "grid":
                addDriver(getDriverForRemoteBrowser(getExecutor().toLowerCase()));
                break;
            default:
                throw new IllegalStateException("This service: "+getService().toLowerCase()+", is not supported yet!");
        }
        getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(PAGE_LOAD_TIMEOUT));
        getDriver().manage().timeouts().scriptTimeout(Duration.ofSeconds(SCRIPT_TIMEOUT));
        getDriver().manage().deleteAllCookies();
        getDriver().manage().window().maximize();
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
                    driver = new RemoteWebDriver(new URL(SELENIUM_GRID_URL), chromeOptions);
                } catch (MalformedURLException e) {
                    logger.error("Failed to launch Chrome remote driver!", e);
                }
                return (RemoteWebDriver) driver;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                //Setting Profile for firefox
                FirefoxProfile profile = new FirefoxProfile();
                profile.setPreference("intl.accept_languages","en-us");
                profile.setPreference("permissions.default.desktop-notification", 2);
                firefoxOptions.setProfile(profile);
                try {
                    driver = new RemoteWebDriver(new URL(SELENIUM_GRID_URL), firefoxOptions);
                } catch (MalformedURLException e) {
                    logger.error("Failed to launch Firefox remote driver!", e);
                }
                return (RemoteWebDriver) driver;
            default:
                throw new IllegalStateException("This browser isn't supported yet. Sorry...");
        }
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
}
