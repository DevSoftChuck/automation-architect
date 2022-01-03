package PageObjects;

import org.openqa.selenium.support.PageFactory;

import static Setup.SeleniumDriver.getDriver;

public abstract class Page<T>{

    //Inits all  the pages
    public T openPage(Class<T> clazz)
    {
        return PageFactory.initElements(getDriver(), clazz);
    }
}
