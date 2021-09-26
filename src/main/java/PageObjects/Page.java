package PageObjects;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;

import static Setup.SeleniumDriver.getDriver;

public abstract class Page<T>
{
    private static final int LOAD_TIMEOUT = 30;
    private static final int REFRESH_RATE = 2;

    //Inits all  the pages
    public T openPage(Class<T> clazz)
    {
        T page = PageFactory.initElements(getDriver(), clazz);
        //ExpectedCondition pageLoadCondition = ((Page) page).getPageLoadCondition();
        //waitForPageToLoad(pageLoadCondition);
        return page;
    }
}
