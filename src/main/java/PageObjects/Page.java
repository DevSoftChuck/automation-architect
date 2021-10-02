package PageObjects;

import org.openqa.selenium.support.PageFactory;

import static Setup.SeleniumDriver.getDriver;

public abstract class Page<T>
{
    private static final int LOAD_TIMEOUT = 30;
    private static final int REFRESH_RATE = 2;

    //Inits all  the pages
    public T openPage(Class<T> clazz)
    {
        return PageFactory.initElements(getDriver(), clazz);
    }
}
