package Setup.DriverFactory.Drivers.Platforms;

import Setup.TestEnvironment;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class LocalAppiumPlatform {

    public WebDriver startApplication() {
        switch (TestEnvironment.getDevice().toLowerCase()) {
            case "android":
                switch (TestEnvironment.getBrowser().toLowerCase()) {
                    case "chrome":
                        WebDriverManager wdm = WebDriverManager.chromedriver();
                        wdm.setup();
                        String chromeDriverPath = wdm.getBinaryPath();

                        DesiredCapabilities capabilities = new DesiredCapabilities();
                        capabilities.setCapability("deviceName", TestEnvironment.ANDROID_DEVICE_NAME);
                        capabilities.setCapability("browserName", "Chrome");
                        capabilities.setCapability("automationName", "uiautomator2");
                        capabilities.setCapability("appWaitDuration", "60000");
                        capabilities.setCapability("chromedriverExecutable", chromeDriverPath);
                        try {
                            return new AndroidDriver(new URL(TestEnvironment.APPIUM_URL), capabilities);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    case "native":
                        File appDir = new File("src/test/resources");
                        File app =  new File(appDir, "ApiDemos-debug.apk");
                        DesiredCapabilities capabilitiesNative = new DesiredCapabilities();
                        capabilitiesNative.setCapability("deviceName", TestEnvironment.ANDROID_DEVICE_NAME);
                        capabilitiesNative.setCapability("app", app.getAbsolutePath());
                        capabilitiesNative.setCapability("appPackage", "io.appium.android.apis");
                        capabilitiesNative.setCapability("automationName", "uiautomator2");
                        capabilitiesNative.setCapability("appWaitDuration", "60000");
                        try {
                            return new AndroidDriver(new URL(TestEnvironment.APPIUM_URL), capabilitiesNative);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }                    default:
                        throw new IllegalStateException("This browser: "+ TestEnvironment.getBrowser().toLowerCase() +", is not supported yet!");
                }
            case "ios":
                switch (TestEnvironment.getBrowser().toLowerCase()) {
                    case "safari":
                        DesiredCapabilities capabilitiesSF = new DesiredCapabilities();
                        capabilitiesSF.setCapability("deviceName", TestEnvironment.IOS_DEVICE_NAME);
                        capabilitiesSF.setCapability("platformVersion", "11.2.2");
                        capabilitiesSF.setCapability("browserName", "Safari");
                        capabilitiesSF.setCapability("automationName", "XCUITest");
                        capabilitiesSF.setCapability("appWaitDuration", "60000");
                        try {
                            return new IOSDriver(new URL(TestEnvironment.APPIUM_URL), capabilitiesSF);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    case "native":
                        File appDir = new File("src/test/resources");
                        File app =  new File(appDir, "TestApp.app.zip");
                        DesiredCapabilities capabilitiesNative = new DesiredCapabilities();
                        capabilitiesNative.setCapability("deviceName", TestEnvironment.IOS_DEVICE_NAME);
                        capabilitiesNative.setCapability("platformVersion", "11.2.2");
                        capabilitiesNative.setCapability("app", app.getAbsolutePath());
                        capabilitiesNative.setCapability("automationName", "XCUITest");
                        capabilitiesNative.setCapability("appWaitDuration", "60000");
                        try {
                            return new IOSDriver(new URL(TestEnvironment.APPIUM_URL), capabilitiesNative);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    default:
                        throw new IllegalStateException("This browser: "+ TestEnvironment.getBrowser().toLowerCase() +", is not supported yet!");
                }
            default:
                throw new IllegalStateException("This device: "+ TestEnvironment.getDevice().toLowerCase() +", is not supported yet!");
        }
    }
}
