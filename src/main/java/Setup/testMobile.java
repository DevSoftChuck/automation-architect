package Setup;

import Setup.DriverFactory.DriverFactory;
import Setup.DriverFactory.Services.Service;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class testMobile extends TestEnvironment{
    private static AndroidDriver androidDriver;
    private static final ExecutorService executor = Executors.newFixedThreadPool(2);
    private static Process p;

    public static void main(String[] args) throws Exception {

        if(System.getProperty("os.name").equals("Windows 10")){
            //For Windows
            executor.submit(() -> {
                    try {
                        p = Runtime.getRuntime().exec("cmd /K \"" +
                                "cd /D " + TestEnvironment.ANDROID_SDK_PATH +"\\emulator && " +
                                "start emulator.exe -noaudio @"+TestEnvironment.ANDROID_DEVICE_NAME+"\"");
//                        "start emulator.exe -noaudio -no-snapshot-load @"+TestEnvironment.ANDROID_DEVICE_NAME+"\"");
                        System.out.println("Process started");
                    } catch (IOException e) {
                        System.out.println("Error, failing to start the emulator.");
                    }
                });
            Thread.sleep(10000);
            System.out.println("Process is alive");

            String line;
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()) );
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();

            System.out.println("Process PID: "+ getProcessID(p));


            p.destroy();
            if (p.isAlive()) {
                System.out.println("Process was alive");
                p.destroyForcibly();
            }
            if (!p.isAlive()) {
                System.out.println("Process was destroyed");
            }
            executor.shutdown();
            if(!executor.isShutdown()){
                System.out.println("Executor was alive");
                executor.shutdownNow();
            }
        }


//        AppiumDriverLocalService service = AppiumDriverLocalService.buildDefaultService();
//        service.start();
//
//        System.setProperty("browser","native");
//        System.setProperty("service","appium");
//
//        DriverFactory.initialize();
//        androidDriver = (AndroidDriver) DriverFactory.getDriver();
//        androidDriver.findElement(By.xpath("//android.widget.TextView[@text='Preference']")).click();
//        androidDriver.findElement(By.xpath("//android.widget.TextView[@text='3. Preference dependencies']")).click();
//        androidDriver.findElement(By.id("android:id/checkbox")).click();
//        androidDriver.findElement(By.xpath("(//android.widget.RelativeLayout)[2]")).click();
//        service.close();

    }

    public static long getProcessID(Process p)
    {
        long result = -1;
        try
        {
            //for windows
            if (p.getClass().getName().equals("java.lang.Win32Process") ||
                    p.getClass().getName().equals("java.lang.ProcessImpl"))
            {
                Field f = p.getClass().getDeclaredField("handle");
                f.setAccessible(true);
                long handl = f.getLong(p);
                Kernel32 kernel = Kernel32.INSTANCE;
                WinNT.HANDLE hand = new WinNT.HANDLE();
                hand.setPointer(Pointer.createConstant(handl));
                result = kernel.GetProcessId(hand);
                f.setAccessible(false);
            }
            //for unix based operating systems
            else if (p.getClass().getName().equals("java.lang.UNIXProcess"))
            {
                Field f = p.getClass().getDeclaredField("pid");
                f.setAccessible(true);
                result = f.getLong(p);
                f.setAccessible(false);
            }
        }
        catch(Exception ex)
        {
            result = -1;
        }
        return result;
    }
}
