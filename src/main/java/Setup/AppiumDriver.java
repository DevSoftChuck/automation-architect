package Setup;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AppiumDriver extends TestEnvironment{

    private static AndroidDriver androidDriver;
    private static IOSDriver iosDriver;

    private static final List<AndroidDriver> storedAndroidDrivers = new ArrayList<>();
    private static final List<IOSDriver> storedIOSDrivers = new ArrayList<>();

    private static final ThreadLocal<AndroidDriver> androidDrivers = new ThreadLocal<>();
    private static final ThreadLocal<IOSDriver> iosDrivers = new ThreadLocal<>();


    public static AndroidDriver getAndroidDriver() {
        return androidDrivers.get();
    }
    public static IOSDriver getIOSDriver() {
        return iosDrivers.get();
    }


    private static void addAndroidDriver(AndroidDriver driver) {
        storedAndroidDrivers.add(driver);
        androidDrivers.set(driver);
    }

    private static void addIOSDriver(IOSDriver driver) {
        storedIOSDrivers.add(driver);
        iosDrivers.set(driver);
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

    public static String getPlatformDevice() {
        String mobileExecutor = System.getProperty("platform");
        if (mobileExecutor == null) {
            mobileExecutor = System.getenv("platform");
            if (mobileExecutor == null) {
                mobileExecutor = DEFAULT_PLATFORM;
            }
        }
        return mobileExecutor;
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

    public static void startPlatform() throws IOException {
        switch (getService().toLowerCase()) {
            case "local":
                switch (getPlatformDevice().toLowerCase()) {
                    case "android":
                        switch (getExecutor().toLowerCase()) {
                            case "chrome":
                                WebDriverManager wdm = WebDriverManager.chromedriver();
                                wdm.setup();
                                String chromeDriverPath = wdm.getBinaryPath();

                                DesiredCapabilities capabilities = new DesiredCapabilities();
                                capabilities.setCapability("deviceName", ANDROID_DEVICE_NAME);
                                capabilities.setCapability("browserName", "Chrome");
                                capabilities.setCapability("automationName", "uiautomator2");
                                capabilities.setCapability("chromedriverExecutable", chromeDriverPath);

                                androidDriver = new AndroidDriver(new URL(APPIUM_URL), capabilities);
                                addAndroidDriver(androidDriver);
                                break;
                            case "native":
                                File appDir = new File("src/test/resources");
                                File app = new File(appDir, "ApiDemos-debug.apk");
                                DesiredCapabilities capabilitiesNative = new DesiredCapabilities();
                                capabilitiesNative.setCapability("deviceName", ANDROID_DEVICE_NAME);
                                capabilitiesNative.setCapability("app", app.getAbsolutePath());
                                capabilitiesNative.setCapability("appPackage", "io.appium.android.apis");
                                androidDriver = new AndroidDriver(new URL(APPIUM_URL), capabilitiesNative);
                                addAndroidDriver(androidDriver);
                                break;
                            default:
                                throw new IllegalStateException("This executor: "+ getExecutor().toLowerCase() +", is not supported yet!");
                        }
                        break;
                    case "ios":
                        switch (getExecutor().toLowerCase()) {
                            case "safari":
                                DesiredCapabilities capabilitiesSF = new DesiredCapabilities();
                                capabilitiesSF.setCapability("deviceName", IOS_DEVICE_NAME);
                                capabilitiesSF.setCapability("platformVersion", "11.2.2");
                                capabilitiesSF.setCapability("browserName", "Safari");
                                capabilitiesSF.setCapability("automationName", "XCUITest");
                                iosDriver = new IOSDriver(new URL(APPIUM_URL), capabilitiesSF);
                                addIOSDriver(iosDriver);
                                break;
                            case "native":
                                File appDir = new File("src/test/resources");
                                File app = new File(appDir.getCanonicalPath(), "TestApp.app.zip");

                                DesiredCapabilities capabilitiesNative = new DesiredCapabilities();
                                capabilitiesNative.setCapability("deviceName", IOS_DEVICE_NAME);
                                capabilitiesNative.setCapability("platformVersion", "11.2.2");
                                capabilitiesNative.setCapability("app", app.getAbsolutePath());
                                capabilitiesNative.setCapability("automationName", "XCUITest");
                                iosDriver = new IOSDriver(new URL(APPIUM_URL), capabilitiesNative);
                                addIOSDriver(iosDriver);
                                break;
                            default:
                                throw new IllegalStateException("This executor: "+ getExecutor().toLowerCase() +", is not supported yet!");
                        }
                        break;
                    default:
                        throw new IllegalStateException("This platform: "+ getPlatformDevice().toLowerCase() +", is not supported yet!");
                }
            case "saucelab":
                switch (getPlatformDevice().toLowerCase()) {
                    case "android":
                        switch (getExecutor().toLowerCase()) {
                            case "chrome":
                                WebDriverManager wdm = WebDriverManager.chromedriver();
                                wdm.setup();
                                String chromeDriverPath = wdm.getBinaryPath();
                                DesiredCapabilities capabilities = new DesiredCapabilities();
                                capabilities.setCapability("deviceName", ANDROID_DEVICE_NAME);
                                capabilities.setCapability("browserName", "Chrome");
                                capabilities.setCapability("automationName", "uiautomator2");
                                capabilities.setCapability("chromedriverExecutable", chromeDriverPath);
                                androidDriver = new AndroidDriver(new URL(APPIUM_URL), capabilities);
                                addAndroidDriver(androidDriver);
                                break;
                            case "native":
                                File appDir = new File("src/test/resources");
                                File app = new File(appDir, "ApiDemos-debug.apk");
                                DesiredCapabilities capabilitiesNative = new DesiredCapabilities();
                                capabilitiesNative.setCapability(MobileCapabilityType.PLATFORM_VERSION, "4.4");
                                capabilitiesNative.setCapability(MobileCapabilityType.PLATFORM_NAME,"Android");
                                capabilitiesNative.setCapability(MobileCapabilityType.AUTOMATION_NAME,"UiAutomator2");
                                capabilitiesNative.setCapability(MobileCapabilityType.DEVICE_NAME, ANDROID_DEVICE_NAME);
                                capabilitiesNative.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
                                capabilitiesNative.setCapability("newCommandTimeout", 2000);
                                androidDriver = new AndroidDriver(new URL(SAUCELAB_URL), capabilitiesNative);
                                addAndroidDriver(androidDriver);
                                break;
                            default:
                                throw new IllegalStateException("This executor: "+ getExecutor().toLowerCase() +", is not supported yet!");
                        }
                        break;
                    case "ios":
                        switch (getExecutor().toLowerCase()) {
                            case "safari":
                                DesiredCapabilities capabilitiesSF = new DesiredCapabilities();
                                capabilitiesSF.setCapability("deviceName", IOS_DEVICE_NAME);
                                capabilitiesSF.setCapability("platformVersion", "11.2.2");
                                capabilitiesSF.setCapability("browserName", "Safari");
                                capabilitiesSF.setCapability("automationName", "XCUITest");
                                iosDriver = new IOSDriver(new URL(APPIUM_URL), capabilitiesSF);
                                addIOSDriver(iosDriver);
                                break;
                            case "native":
                                File appDir = new File("src/test/resources");
                                File app = new File(appDir.getCanonicalPath(), "TestApp.app.zip");

                                DesiredCapabilities capabilitiesIOS = new DesiredCapabilities();
                                capabilitiesIOS.setCapability(MobileCapabilityType.PLATFORM_VERSION, "11.2.2");
                                capabilitiesIOS.setCapability(MobileCapabilityType.PLATFORM_NAME,"iOS");
                                capabilitiesIOS.setCapability(MobileCapabilityType.AUTOMATION_NAME,"XCUITest");
                                capabilitiesIOS.setCapability(MobileCapabilityType.DEVICE_NAME, IOS_DEVICE_NAME);
                                capabilitiesIOS.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
                                capabilitiesIOS.setCapability("newCommandTimeout", 2000);
                                iosDriver = new IOSDriver(new URL(SAUCELAB_URL), capabilitiesIOS);
                                addIOSDriver(iosDriver);
                                break;
                            default:
                                throw new IllegalStateException("This executor: "+ getExecutor().toLowerCase() +", is not supported yet!");
                        }
                        break;
                    default:
                        throw new IllegalStateException("This platform: "+ getPlatformDevice().toLowerCase() +", is not supported yet!");
                }
                break;
            default:
                throw new IllegalStateException("This service: "+getService().toLowerCase()+", is not supported yet!");
        }
    }

    public static void destroyDriver() {
        for (AndroidDriver driver : storedAndroidDrivers) {
            if (driver != null) {
                driver.quit();
            }
        }

        for (IOSDriver driver : storedIOSDrivers) {
            if (driver != null) {
                driver.quit();
            }
        }
    }

    public static void removeDriver() {
        storedAndroidDrivers.remove(androidDrivers.get());
        androidDrivers.remove();

        storedIOSDrivers.remove(iosDrivers.get());
        iosDrivers.remove();
    }

}
