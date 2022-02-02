package Setup.DriverFactory.Drivers.Platforms;

import Setup.TestEnvironment;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class SaucelabAppiumPlatform {

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
                        capabilities.setCapability("chromedriverExecutable", chromeDriverPath);
                        try {
                            return new AndroidDriver(new URL(TestEnvironment.APPIUM_URL), capabilities);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    case "native":
                        File appDir = new File("src/test/resources");
                        File app = new File(appDir, "ApiDemos-debug.apk");
                        DesiredCapabilities capabilitiesNative = new DesiredCapabilities();
                        capabilitiesNative.setCapability(MobileCapabilityType.PLATFORM_VERSION, "4.4");
                        capabilitiesNative.setCapability(MobileCapabilityType.PLATFORM_NAME,"Android");
                        capabilitiesNative.setCapability(MobileCapabilityType.AUTOMATION_NAME,"UiAutomator2");
                        capabilitiesNative.setCapability(MobileCapabilityType.DEVICE_NAME, TestEnvironment.ANDROID_DEVICE_NAME);
                        capabilitiesNative.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
                        capabilitiesNative.setCapability("newCommandTimeout", 2000);
                        try {
                            return new AndroidDriver(new URL(TestEnvironment.SAUCELAB_URL), capabilitiesNative);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    default:
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
                        try {
                            return new IOSDriver(new URL(TestEnvironment.APPIUM_URL), capabilitiesSF);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    case "native":
                        File appDir = new File("src/test/resources");
                        File app = null;
                        try {
                            app = new File(appDir.getCanonicalPath(), "TestApp.app.zip");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        DesiredCapabilities capabilitiesIOS = new DesiredCapabilities();
                        capabilitiesIOS.setCapability(MobileCapabilityType.PLATFORM_VERSION, "11.2.2");
                        capabilitiesIOS.setCapability(MobileCapabilityType.PLATFORM_NAME,"iOS");
                        capabilitiesIOS.setCapability(MobileCapabilityType.AUTOMATION_NAME,"XCUITest");
                        capabilitiesIOS.setCapability(MobileCapabilityType.DEVICE_NAME, TestEnvironment.IOS_DEVICE_NAME);
                        capabilitiesIOS.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
                        capabilitiesIOS.setCapability("newCommandTimeout", 2000);
                        try {
                            return new IOSDriver(new URL(TestEnvironment.SAUCELAB_URL), capabilitiesIOS);
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
