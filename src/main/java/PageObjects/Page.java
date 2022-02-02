package PageObjects;

import org.openqa.selenium.support.PageFactory;

import static Setup.DriverFactory.DriverFactory.getDriver;
import static Setup.AppiumDriver.getAndroidDriver;
import static Setup.AppiumDriver.getIOSDriver;


public abstract class Page<T>{

    //Inits all the pages
    public T openPage(Class<T> clazz) {
        return PageFactory.initElements(getDriver(), clazz);
    }

    public T openMobilePage(Class<T> clazz) {
        if(System.getProperty("platform").equals("android")){
            return PageFactory.initElements(getAndroidDriver(), clazz);
        }else if (System.getProperty("platform").equals("ios")){
            return PageFactory.initElements(getIOSDriver(), clazz);
        }
        throw new IllegalStateException("Mobile page cannot be opened. Platform was not defined!");
    }
}
