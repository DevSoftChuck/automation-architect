package Setup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AndroidEmulator extends TestEnvironment{
    public static void startEmulator(){
        if(System.getProperty("os.name").equals("Windows 10")){
            //For Windows
            try {
                String line;
                Process p = Runtime.getRuntime().exec("cmd /K \"" +
                        "cd /D " + TestEnvironment.ANDROID_SDK_PATH +"\\emulator && " +
                        "start emulator.exe -noaudio -no-snapshot-load @"+TestEnvironment.ANDROID_DEVICE_NAME+"\"");
                BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()) );
                while ((line = in.readLine()) != null) {
                    System.out.println(line);
                }
                in.close();
            } catch (IOException e) {
                System.out.println("Error, failing to start the emulator.");
            }
        }else if (System.getProperty("os.name").equals("Linux")){
            //For Linux
            try {
                String line;
                Process p = Runtime.getRuntime().exec(
                        new String[] { "sh",  "-c", "cd " + TestEnvironment.ANDROID_SDK_PATH +
                                "/emulator && ./emulator -noaudio -no-snapshot-load @"+TestEnvironment.ANDROID_DEVICE_NAME });
                BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()) );
                while ((line = in.readLine()) != null) {
                    System.out.println(line);
                }
                in.close();
            } catch (IOException e) {
                System.out.println("Error, failing to start the emulator.");
            }
        }
    }
}
