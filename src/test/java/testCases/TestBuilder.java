package testCases;

import utils.SeleniumUtils;
import org.openqa.selenium.By;
import org.testng.asserts.SoftAssert;

public class TestBuilder extends BaseTestCase {
    private final ThreadLocal<SoftAssert> softAssertList = new ThreadLocal<>();

    public TestBuilder initializeTestCase() {
        this.softAssertList.set(new SoftAssert());
        return this;
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

    public TestBuilder waitUntilElementDisappears(By webElement){
        SeleniumUtils.waitForElementToBeNotVisible(webElement);
        return this;
    }

    public TestBuilder checkVisibilityOf(By webElement, String message){
        this.softAssertList.get().assertTrue(SeleniumUtils.isExpectedElementDisplayed(webElement), message);
        return this;
    }

    public <T> TestBuilder checkEqualityOf(final T actual, final T expected, final String message){
        this.softAssertList.get().assertEquals(actual, expected, message);
        return this;
    }

    /* ---------------------------------------- METHODS TO CLICK ON ELEMENTS ---------------------------------------- */

    public TestBuilder clickOn(By webElement){
        SeleniumUtils.scrollToElement(webElement);
        SeleniumUtils.waitForElementToBeClickable(webElement).click();
        return this;
    }

    public TestBuilder clickOn(By webElement, By waitForItDisappears){
        SeleniumUtils.waitForElementToBeNotVisible(waitForItDisappears);
        SeleniumUtils.waitForElementToBeClickable(webElement).click();
        return this;
    }

    public TestBuilder ignoreOrClickOn(By webElement){
        try{
            SeleniumUtils.waitForElementToBeClickable(webElement).click();
        }catch (Exception ignore){}
        return this;
    }

    public TestBuilder waitAndClickOn(By webElement, By waitForVisibility){
        SeleniumUtils.waitForElementToBeVisible(waitForVisibility);
        SeleniumUtils.waitForElementToBeClickable(webElement).click();
        return this;
    }

    public TestBuilder sendKeysOn(By webElement, String keys){
        SeleniumUtils.waitForElementToBeVisible(webElement).sendKeys(keys);
        return this;
    }

    public TestBuilder goTo(String url){
        SeleniumUtils.gotoURL(url);
        return this;
    }

    public SoftAssert getSoftAssert(){
        return this.softAssertList.get();
    }

    public void finishTestCase(){
        this.softAssertList.get().assertAll();
    }
}
