package Setup;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class testMobile extends TestEnvironment{
    private static AndroidDriver androidDriver;

    public static void main(String[] args) throws IOException {
        AppiumDriverLocalService service = AppiumDriverLocalService.buildDefaultService();
        service.start();
//        File appDir = new File("src/test/resources");
//        File app = new File(appDir, "ApiDemos-debug.apk");
        DesiredCapabilities capabilitiesNative = new DesiredCapabilities();
//        capabilitiesNative.setCapability(MobileCapabilityType.DEVICE_NAME, "AndroidEmulator");
//        capabilitiesNative.setCapability(MobileCapabilityType.AUTOMATION_NAME,"uiautomator2");
//        capabilitiesNative.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());

        WebDriverManager wdm = WebDriverManager.chromedriver();
        wdm.setup();
        String chromedriverPath = wdm.getBinaryPath();


        capabilitiesNative.setCapability("deviceName", "AndroidEmulator");
        capabilitiesNative.setCapability("browserName", "Firefox");
        capabilitiesNative.setCapability("automationName", "uiautomator2");
        capabilitiesNative.setCapability("chromedriverExecutable", chromedriverPath);





        androidDriver = new AndroidDriver(new URL(APPIUM_URL), capabilitiesNative);


        service.close();
    }
}
