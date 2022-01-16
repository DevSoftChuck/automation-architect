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

/**
 * @author Ivan Andraschko
 **/

public class TestEnvironment {

    protected static Logger logger = LoggerFactory.getLogger(Logger.class);
    protected static final String TODAY_DATE = new SimpleDateFormat("yyyy-MM-dd HH:ss").format(new Date());

    //Static variable that stores the amount of time the waits are going to wait.
    protected static final int SCRIPT_TIMEOUT = 15;
    protected static final int PAGE_LOAD_TIMEOUT = 30;
    protected static final int CLICK_TIMEOUT = 15;
    protected static final int VISIBLE_TIMEOUT = 30;
    protected static final long POLLING_TIME = 5;

    //AMOUNT OF TEST EXECUTED//
    public static int passedTestsAmount = 0;
    public static int failedTestsAmount = 0;

    //GENERAL SETTINGS//
    protected static final String ANSI_RED = "\u001B[31m";
    protected static final String ANSI_RESET = "\u001B[0m";
    protected static final String ANSI_BLUE = "\u001b[34m";
    protected static final String ANSI_GREEN = "\u001B[32m";
    protected static final String EXECUTOR = "MAVEN";

    //ENVIRONMENT PROPERTIES//
    protected static final String TRAVIS_BUILD_NUMBER = System.getProperty
            ("travis.buildNumber", "Build was made on localhost");
    protected static final String TRAVIS_BUILD_WEB_URL = System.getProperty
            ("travis.buildURL", "Build was made on localhost");
    protected static final String TRAVIS_BRANCH = System.getProperty
            ("travis.branch", "Build was made on localhost");
    protected static final String OS_NAME = System.getProperty
            ("travis.osName", "Build was made on localhost");
    protected static final String JAVA_VERSION = System.getProperty
            ("travis.jdkVersion", "Build was made on localhost");
    protected static final String SLACK_TOKEN = System.getProperty
            ("travis.slack", "");
    protected static final String DEFAULT_REMOTE_BROWSER = System.getProperty
            ("remote.browser", "chrome");
    protected static final String DEFAULT_TESTS_EXECUTOR = System.getProperty
            ("tests.executor", "chrome");
    protected static final String SELENIUM_GRID_URL = System.getProperty
            ("selenium.gridURL", "http://salesforce-qa-testing.com/");

    //ENVIRONMENT METHODS//
    public static String getCurrentPath() {
        return Paths.get(".").toAbsolutePath().normalize().toString();
    }

    protected static long getUnixTime() {
        return Instant.now().getEpochSecond();
    }

    public void allureWriteProperties() {
        Properties properties = new Properties();
        properties.setProperty("All tests were executed on:", PropertiesManager.getInstance().getConfig(EPropertiesNames.BASE_URL));
        properties.setProperty("Travis build URL:", TRAVIS_BUILD_WEB_URL);
        properties.setProperty("Travis build Run:", TRAVIS_BUILD_NUMBER);
        properties.setProperty("Branch:", TRAVIS_BRANCH);
        properties.setProperty("Browser:", DEFAULT_TESTS_EXECUTOR);
        properties.setProperty("OS Name:", OS_NAME);
        properties.setProperty("JDK Version:", JAVA_VERSION);
        try {
            properties.store(new FileOutputStream("allure-results/environment.properties"), null);
        } catch (IOException e) {
            logger.error("Failed to create properties file!", e);
        }
    }

    public void allureWriteExecutors() {
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
        return ((TakesScreenshot) SeleniumDriver.getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    protected void localSaveScreenshotPNG(Scenario scenario) throws Exception {
        String screenshotName = scenario.getName().replaceAll(" ", "_")
                .concat(String.valueOf(Utils.parser("${S8}")));

        byte[] screenshot = ((TakesScreenshot) SeleniumDriver.getDriver()).getScreenshotAs(OutputType.BYTES);
        scenario.attach(screenshot, "image/png", screenshotName);
        File scrFile = ((TakesScreenshot) SeleniumDriver.getDriver()).getScreenshotAs(OutputType.FILE);
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

    public void deleteOldLogs() {
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
}
