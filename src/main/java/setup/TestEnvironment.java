package setup;

import io.qameta.allure.Attachment;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import static org.hamcrest.Matchers.lessThan;
import static setup.DriverFactory.getDriver;

/**
 * @author Ivan Andraschko
 **/

public class TestEnvironment {

    public static Logger logger = LoggerFactory.getLogger(Logger.class);
    public static final String TODAY_DATE = new SimpleDateFormat("yyyy-MM-dd HH:ss").format(new Date());

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

    @Attachment(value = "Scenario FAIL screenshot", type = "image/png")
    public byte[] allureSaveScreenshotPNG() {
        return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
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
