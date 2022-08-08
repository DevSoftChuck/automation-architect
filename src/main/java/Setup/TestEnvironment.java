package Setup;

import Utils.Utils;
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
import static Setup.DriverFactory.getDriver;

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
    public static final String DEFAULT_BROWSER = System.getProperty
            ("browser", "chrome");
    public static final String DEFAULT_DRIVER_REMOTE_SERVER = System.getProperty
            ("driver.remote.server", "local");
    public static final String DEFAULT_SAUCE_PLATFORM_NAME = System.getProperty
            ("sauce.platform.name", "Windows 11");
    public static final String DEFAULT_SAUCE_BROWSER_VERSION = System.getProperty
            ("sauce.browser.version", "latest");
    public static final String SELENIUM_GRID_URL = System.getProperty
            ("selenium.gridURL", "http://salesforce-qa-testing.com/");
    public static final String DEFAULT_SAUCE_USERNAME = System.getProperty
            ("sauce.user", "");
    public static final String DEFAULT_SAUCE_ACCESS_KEY = System.getProperty
            ("sauce.key", "");

    //ENVIRONMENT METHODS//
    public static String getCurrentPath() {
        return Paths.get(".").toAbsolutePath().normalize().toString();
    }

    public static long getUnixTime() {
        return Instant.now().getEpochSecond();
    }

    public static void allureWriteProperties() {
        Properties properties = new Properties();
        properties.setProperty("All tests were executed on:", "URL");
        properties.setProperty("Browser:", DEFAULT_BROWSER);
        try {
            properties.store(Files.newOutputStream(Paths.get("allure-results/environment.properties")), null);
        } catch (IOException e) {
            logger.error("Failed to create properties file!", e);
        }
    }

    public static void allureWriteExecutors() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", EXECUTOR);
        jsonObject.put("type", EXECUTOR);
        jsonObject.put("buildName", String.format("Allure Report via Travis CI: %s", "31"));
        jsonObject.put("reportUrl", "www.test.com");
        try {
            FileWriter fileWriter = new FileWriter("allure-results/executor.json");
            fileWriter.write(jsonObject.toString());
            fileWriter.flush();
        } catch (IOException e) {
            logger.error("Failed to create json object!", e);
        }
    }
    @Attachment(value = "Scenario FAIL screenshot", type = "image/png")
    public byte[] allureSaveScreenshotPNG() {
        return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
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
