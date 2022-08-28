package testCases;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.SeleniumUtils;
import org.openqa.selenium.By;
import org.testng.asserts.SoftAssert;

public class TestBuilder extends BaseTestCase {

    protected final ThreadLocal<SoftAssert> softAssertList = new ThreadLocal<>();

    @BeforeMethod(alwaysRun = true, description = "Setting up asserts")
    protected void beforeTestBuilder(){
        this.softAssertList.set(new SoftAssert());
    }

    /**
     * Use Class.newInstance() with constructor arguments
     * Assuming you have the following constructor:
     *
     * class MyClass {
     *     public MyClass(Long l, String s, int i) {
     *
     *     }
     * }
     *
     * You will need to show you intend to use this constructor like so:
     *
     * Class classToLoad = MyClass.class;
     *
     * Long l = new Long(88);
     * String s = "text";
     * int i = 5;
     *
     * classToLoad.getDeclaredConstructor(Long.class, String.class, Integer.class).newInstance(l, s, i);
     */
    public <T> T returnNewInstance(Class<T> clazz){
        try{
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /* --------------------------------- METHODS TO WAIT, CHECK AND ASSERT ELEMENTS --------------------------------- */

    protected TestBuilder waitUntilElementDisappears(By webElement){
        SeleniumUtils.waitForElementToBeNotVisible(webElement);
        return this;
    }

    protected TestBuilder checkVisibilityOf(By webElement, String message){
        this.softAssertList.get().assertTrue(SeleniumUtils.isExpectedElementDisplayed(webElement), message);
        return this;
    }

    protected <T> TestBuilder checkEqualityOf(final T actual, final T expected, final String message){
        this.softAssertList.get().assertEquals(actual, expected, message);
        return this;
    }

    /* ---------------------------------------- METHODS TO CLICK ON ELEMENTS ---------------------------------------- */

    protected TestBuilder clickOn(By webElement){
        SeleniumUtils.scrollToElement(webElement);
        SeleniumUtils.waitForElementToBeClickable(webElement).click();
        return this;
    }

    protected TestBuilder clickOn(By webElement, By waitForItDisappears){
        SeleniumUtils.waitForElementToBeNotVisible(waitForItDisappears);
        SeleniumUtils.waitForElementToBeClickable(webElement).click();
        return this;
    }

    protected TestBuilder ignoreOrClickOn(By webElement){
        try{
            SeleniumUtils.waitForElementToBeClickable(webElement).click();
        }catch (Exception ignore){}
        return this;
    }

    protected TestBuilder waitAndClickOn(By webElement, By waitForVisibility){
        SeleniumUtils.waitForElementToBeVisible(waitForVisibility);
        SeleniumUtils.waitForElementToBeClickable(webElement).click();
        return this;
    }

    protected TestBuilder sendKeysOn(By webElement, String keys){
        SeleniumUtils.waitForElementToBeVisible(webElement).sendKeys(keys);
        return this;
    }

    protected TestBuilder goTo(String url){
        SeleniumUtils.gotoURL(url);
        return this;
    }

    protected SoftAssert getSoftAssert(){
        return this.softAssertList.get();
    }

    @AfterMethod(alwaysRun = true)
    protected void afterTestBuilder(){
        this.softAssertList.get().assertAll();
    }
}
