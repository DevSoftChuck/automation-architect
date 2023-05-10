package setup;

import org.openqa.selenium.Capabilities;
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
import java.util.HashMap;
import java.util.Map;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> drivers = new ThreadLocal<>();
    private static final int PAGE_LOAD_TIMEOUT = 30;
    private static final long SCRIPT_TIMEOUT = 15;

    public static void initialize(String testName) {
        switch (TestEnvironment.DEFAULT_DRIVER_REMOTE_SERVER.toLowerCase()) {
            case "baas" -> {
                addDriver(getRemoteSeleniumDriver(testName));
                setupDriverConfiguration();
            }
            case "local" -> {
                addDriver(getLocalSeleniumDriver());
                setupDriverConfiguration();
            }
            default -> throw new IllegalStateException("This remote driver: " +
                    TestEnvironment.DEFAULT_DRIVER_REMOTE_SERVER.toLowerCase() + ", is not supported yet!");
        }
    }

    public static WebDriver getDriver() {
        return drivers.get();
    }

    private static void addDriver(WebDriver driver) {
        drivers.set(driver);
    }

    public static void removeDriver() {
        drivers.get().quit();
        drivers.remove();
    }

    private static void setupDriverConfiguration(){
        getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(PAGE_LOAD_TIMEOUT));
        getDriver().manage().timeouts().scriptTimeout(Duration.ofSeconds(SCRIPT_TIMEOUT));
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(PAGE_LOAD_TIMEOUT));
        getDriver().manage().deleteAllCookies();
        getDriver().manage().window().maximize();
    }

    private static WebDriver getLocalSeleniumDriver(){
        return switch (TestEnvironment.DEFAULT_BROWSER.toLowerCase()) {
            case "chrome" -> {
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--lang=en");
                chromeOptions.addArguments("--disable-extensions");
                chromeOptions.addArguments("--disable-popup-blocking");
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--ignore-certificate-errors");
                chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                chromeOptions.setHeadless(TestEnvironment.HEADLESS);
                yield new ChromeDriver(chromeOptions);
            }
            case "firefox" -> {
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setHeadless(TestEnvironment.HEADLESS);
                FirefoxProfile profile = new FirefoxProfile();
                profile.setPreference("intl.accept_languages", "en-us");
                firefoxOptions.setProfile(profile);
                yield new FirefoxDriver(firefoxOptions);
            }
            default -> throw new IllegalArgumentException("This browser: " +
                    TestEnvironment.DEFAULT_BROWSER.toLowerCase() + ", is not supported yet!");
        };
    }

    private static WebDriver getRemoteSeleniumDriver(String testName){
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("screenResolution", "1920x1440");
        prefs.put("name", testName);
        prefs.put("username", TestEnvironment.DEFAULT_SAUCE_USERNAME);
        prefs.put("accessKey", TestEnvironment.DEFAULT_SAUCE_ACCESS_KEY);

        return switch (TestEnvironment.DEFAULT_BROWSER.toLowerCase()) {
            case "chrome" -> {
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setPlatformName(TestEnvironment.DEFAULT_SAUCE_PLATFORM_NAME);
                chromeOptions.setBrowserVersion(TestEnvironment.DEFAULT_SAUCE_BROWSER_VERSION);
                chromeOptions.addArguments("--lang=en");
                chromeOptions.addArguments("--disable-extensions");
                chromeOptions.addArguments("--disable-popup-blocking");
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--ignore-certificate-errors");
                chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                chromeOptions.setHeadless(TestEnvironment.HEADLESS);

                chromeOptions.setCapability("sauce:options", prefs);
                yield makeRemoteConnection(chromeOptions);
            }
            case "firefox" -> {
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setPlatformName(TestEnvironment.DEFAULT_SAUCE_PLATFORM_NAME);
                firefoxOptions.setBrowserVersion(TestEnvironment.DEFAULT_SAUCE_BROWSER_VERSION);
                firefoxOptions.setHeadless(TestEnvironment.HEADLESS);

                //Setting Profile for firefox
                FirefoxProfile profile = new FirefoxProfile();
                profile.setPreference("intl.accept_languages", "en-us");
                firefoxOptions.setProfile(profile);
                firefoxOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                firefoxOptions.setCapability("se:options", prefs);
                yield makeRemoteConnection(firefoxOptions);
            }
            default -> throw new IllegalArgumentException("This browser: " +
                    TestEnvironment.DEFAULT_BROWSER.toLowerCase() + ", is not supported yet!");
        };
    }

    private static WebDriver makeRemoteConnection(Capabilities capabilities) {
        RemoteWebDriver remoteWebDriver;
        try {
//            remoteWebDriver = new RemoteWebDriver(
//                    new URL("https://ondemand.us-west-1.saucelabs.com/wd/hub"), capabilities);
            remoteWebDriver = new RemoteWebDriver(
                    new URL(TestEnvironment.SELENIUM_GRID_URL), capabilities);
        } catch (Exception e) {
            TestEnvironment.logger.error("Failed to launch Chrome remote driver in Baas!");
            throw new RuntimeException(e);
        }
        return remoteWebDriver;
    }
}
