package Setup;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private static void setupDriverConfiguration(){
        getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestEnvironment.PAGE_LOAD_TIMEOUT));
        getDriver().manage().timeouts().scriptTimeout(Duration.ofSeconds(TestEnvironment.SCRIPT_TIMEOUT));
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(TestEnvironment.PAGE_LOAD_TIMEOUT));
        getDriver().manage().deleteAllCookies();
        getDriver().manage().window().maximize();
    }

    /**
     * The concrete factory is usually chosen depending on configuration or
     * environment options.
     */
    public static void initialize(String testName) {
        switch (TestEnvironment.DEFAULT_DRIVER_REMOTE_SERVER.toLowerCase()) {
            case "saucegrid":
                addDriver(getRemoteSeleniumDriver(testName));
                setupDriverConfiguration();
                break;
            case "local":
                addDriver(getLocalSeleniumDriver());
                setupDriverConfiguration();

                break;
            default:
                throw new IllegalStateException("This remote driver: " +
                        TestEnvironment.DEFAULT_DRIVER_REMOTE_SERVER.toLowerCase() + ", is not supported yet!");
        }
    }

    private static WebDriver getLocalSeleniumDriver(){
        switch (TestEnvironment.DEFAULT_BROWSER.toLowerCase()){
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--lang=en");
                chromeOptions.addArguments("--disable-extensions");
                chromeOptions.addArguments("--disable-popup-blocking");
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--ignore-certificate-errors");
                chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);

                Map<String, Object> prefs = new HashMap<>();
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
                chromeOptions.setExperimentalOption("prefs", prefs);

                return new ChromeDriver(chromeOptions);
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();

                //Setting Profile for firefox
                FirefoxProfile profile = new FirefoxProfile();
                profile.setPreference("intl.accept_languages", "en-us");
                firefoxOptions.setProfile(profile);
                return new FirefoxDriver(firefoxOptions);
            default:
                throw new IllegalArgumentException("This browser: "+
                        TestEnvironment.DEFAULT_BROWSER.toLowerCase() + ", is not supported yet!");
        }
    }

    private static WebDriver getRemoteSeleniumDriver(String testName){
        RemoteWebDriver remoteWebDriver = null;
        switch (TestEnvironment.DEFAULT_BROWSER.toLowerCase()){
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setPlatformName(TestEnvironment.DEFAULT_SAUCE_PLATFORM_NAME);
                chromeOptions.setBrowserVersion(TestEnvironment.DEFAULT_SAUCE_BROWSER_VERSION);
                chromeOptions.addArguments("--lang=en");
                chromeOptions.addArguments("--disable-extensions");
                chromeOptions.addArguments("--disable-popup-blocking");
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--ignore-certificate-errors");
                chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);

                Map<String, Object> prefs = new HashMap<>();
                prefs.put("screenResolution", "1920x1440");
                prefs.put("name", testName);
                prefs.put("username", TestEnvironment.DEFAULT_SAUCE_USERNAME);
                prefs.put("accessKey", TestEnvironment.DEFAULT_SAUCE_ACCESS_KEY);
                chromeOptions.setCapability("sauce:options", prefs);

                try{
                    remoteWebDriver = new RemoteWebDriver(
                            new URL("https://ondemand.us-west-1.saucelabs.com/wd/hub"), chromeOptions);
                }catch (Exception e){
                    TestEnvironment.logger.error("Failed to launch Chrome remote driver in Saucelabs");
                    e.printStackTrace();
                }
                break;
            default:
                throw new IllegalArgumentException("This browser: "+
                        TestEnvironment.DEFAULT_BROWSER.toLowerCase() + ", is not supported yet!");
        }
        return remoteWebDriver;
    }

}
