package Setup;

import Utils.Utils;
import io.cucumber.java.Scenario;
import io.qameta.allure.Attachment;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Utils.PropertiesManager;
import Utils.EPropertiesNames;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Properties;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.lessThan;
import static Setup.DriverFactory.DriverFactory.getDriver;

/**
 * @author Ivan Andraschko
 **/

public class TestEnvironment {

    public static Logger logger = LoggerFactory.getLogger(Logger.class);
    public static final String TODAY_DATE = new SimpleDateFormat("yyyy-MM-dd HH:ss").format(new Date());

    //Static variable that stores the amount of time the waits are going to wait.
    public static final int SCRIPT_TIMEOUT = 15;
    public static final int PAGE_LOAD_TIMEOUT = 30;
    public static final int CLICK_TIMEOUT = 15;
    public static final int VISIBLE_TIMEOUT = 30;
    public static final long POLLING_TIME = 5;

    //AMOUNT OF TEST EXECUTED//
    public static int passedTestsAmount = 0;
    public static int failedTestsAmount = 0;

    //GENERAL SETTINGS//
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001b[34m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String EXECUTOR = "MAVEN";

    //ENVIRONMENT PROPERTIES//
    public static final String TRAVIS_BUILD_NUMBER = System.getProperty
            ("travis.buildNumber", "Build was made on localhost");
    public static final String TRAVIS_BUILD_WEB_URL = System.getProperty
            ("travis.buildURL", "Build was made on localhost");
    public static final String TRAVIS_BRANCH = System.getProperty
            ("travis.branch", "Build was made on localhost");
    public static final String DEFAULT_BROWSER = System.getProperty
            ("browser", "chrome");
    public static final String DEFAULT_SERVICE = System.getProperty
            ("service", "selenium");
    public static final String DEFAULT_PLATFORM = System.getProperty
            ("platform", "local");
    public static final String DEFAULT_DEVICE = System.getProperty
            ("device", "android");
    public static final String SELENIUM_GRID_URL = System.getProperty
            ("selenium.gridURL", "http://salesforce-qa-testing.com/");
    public static final String APPIUM_URL = System.getProperty
            ("appium.URL", "http://0.0.0.0:4723");
    public static final String USERNAME = System.getProperty
            ("saucelab.username", "");
    public static final String ACCESS_KEY = System.getProperty
            ("saucelab.accesskey", "");
    public static final String SAUCELAB_URL = "https://"+USERNAME+":" + ACCESS_KEY + "@ondemand.saucelabs.com:443/wd/hub";
    public static final String ANDROID_DEVICE_NAME = System.getProperty
            ("android.device.name", "AndroidEmulator");
    public static final String IOS_DEVICE_NAME = System.getProperty
            ("ios.device.name", "iPhone Simulator");
    public static final String ANDROID_SDK_PATH = System.getenv("ANDROID_HOME");


    //ENVIRONMENT METHODS//
    public static String getCurrentPath() {
        return Paths.get(".").toAbsolutePath().normalize().toString();
    }

    public static long getUnixTime() {
        return Instant.now().getEpochSecond();
    }

    public static void allureWriteProperties() {
        Properties properties = new Properties();
        properties.setProperty("All tests were executed on:", PropertiesManager.getInstance().getConfig(EPropertiesNames.BASE_URL));
        properties.setProperty("Travis build URL:", TRAVIS_BUILD_WEB_URL);
        properties.setProperty("Travis build Run:", TRAVIS_BUILD_NUMBER);
        properties.setProperty("Branch:", TRAVIS_BRANCH);
        properties.setProperty("Browser:", DEFAULT_BROWSER);
        try {
            properties.store(new FileOutputStream("allure-results/environment.properties"), null);
        } catch (IOException e) {
            logger.error("Failed to create properties file!", e);
        }
    }

    public static void allureWriteExecutors() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", EXECUTOR);
        jsonObject.put("type", EXECUTOR);
        jsonObject.put("buildName", String.format("Allure Report via Travis CI: %s", TRAVIS_BUILD_NUMBER));
        jsonObject.put("reportUrl", TRAVIS_BUILD_WEB_URL);
        try {
            FileWriter fileWriter = new FileWriter("allure-results/executor.json");
            fileWriter.write(jsonObject.toString());
            fileWriter.flush();
        } catch (IOException e) {
            logger.error("Failed to create json object!", e);
        }
    }

    @Attachment(value = "Cucumber scenario FAIL logs", type = "text/plain")
    public String allureSaveTextLogCucumber(Scenario scenario) {
        return logBuilder(scenario.getName().toUpperCase());
    }

    @Attachment(value = "Scenario FAIL screenshot", type = "image/png")
    public byte[] allureSaveScreenshotPNG() {
        return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    protected void localSaveScreenshotPNG(Scenario scenario) throws Exception {
        String screenshotName = scenario.getName().replaceAll(" ", "_")
                .concat(String.valueOf(Utils.parser("${S8}")));

        byte[] screenshot = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
        scenario.attach(screenshot, "image/png", screenshotName);
        File scrFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File(getCurrentPath()
                + "target"
                + File.separator
                + "screenshots"
                + File.separator
                + screenshotName
                + "-"
                + TODAY_DATE
                + ".png"));
    }

    protected String logBuilder(String fileName) {
        String path = getCurrentPath()
                + File.separator
                + "logs"
                + File.separator;
        path += fileName + ".log";
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(path), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            logger.error("Failed to attach .log file!", e);
        }
        return contentBuilder.toString();
    }

    public static void deleteOldLogs() {
        try {
            FileUtils.deleteDirectory(new File(getCurrentPath()
                    + File.separator
                    + "logs"));
        } catch (IOException e) {
            logger.error("Failed to delete logs directory!", e);
        }
    }

    public void testResultsCleaner() {
        passedTestsAmount = 0;
        failedTestsAmount = 0;
    }

    public void messageStartScenario(Scenario scenario) {
        logger.info(StringUtils.repeat("#", 110));
        logger.info(StringUtils.repeat("=", 46) + " BEFORE SCENARIO " + StringUtils.repeat("=", 47));
        logger.info(StringUtils.repeat("#", 110));
        logger.info(ANSI_BLUE + "SCENARIO NAME: " + scenario.getName().toUpperCase() + ANSI_RESET);
        logger.info(String.format("Chosen browser: \"%S\"", getBrowser()));
    }

    public void messageFinishScenario(Scenario scenario) {
        String status = (scenario.isFailed() ? ANSI_RED + "FAILED STATUS " + ANSI_RESET : ANSI_GREEN + "SUCCESS STATUS " + ANSI_RESET);
        logger.info(StringUtils.repeat("#", 110));
        logger.info(StringUtils.repeat("=", 36) + " SCENARIO FINISHED WITH " + status + StringUtils.repeat("=", 35));
        logger.info(StringUtils.repeat("#", 110));
        System.out.println();
    }

    public void setupRestAssured(String baseURL) {
        RestAssured.baseURI = baseURL;

        RequestSpecification request = new RequestSpecBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addFilter(new RequestLoggingFilter())//Set restAssured to log all requests globally
                .addFilter(new ResponseLoggingFilter())//Set restAssured to log all responses globally
                .build();

        ResponseSpecification response = new ResponseSpecBuilder()
                .expectResponseTime(lessThan(5000L))
                .build();

        RestAssured.requestSpecification = request;
        RestAssured.responseSpecification = response;
    }

    public static String getBrowser() {
        String browser = System.getProperty("browser");
        if (browser == null) {
            browser = System.getenv("browser");
            if (browser == null) {
                browser = DEFAULT_BROWSER;
            }
        }
        return browser;
    }

    public static String getPlatform() {
        String platform = System.getProperty("platform");
        if (platform == null) {
            platform = System.getenv("platform");
            if (platform == null) {
                platform = DEFAULT_PLATFORM;
            }
        }
        return platform;
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

    public static String getDevice() {
        String device = System.getProperty("device");
        if (device == null) {
            device = System.getenv("device");
            if (device == null) {
                device = DEFAULT_DEVICE;
            }
        }
        return device;
    }
}
