package setup;

import utils.SeleniumUtils;
import org.openqa.selenium.By;
import org.testng.asserts.SoftAssert;

public class TestBuilder {
    private final SoftAssert softAssert;

//    private final Set<T> uniques = new HashSet<>();
//    private <T> T returnOrCreateInstance(Class<T> clazz){
//        T newInstance = newInstance(clazz);
//        if (this.uniques.add(newInstance)){
//            return newInstance;
//        }else {
//            T instanceTemp = null;
//            for(T unique : this.uniques){
//                instanceTemp = unique;
//                if(instanceTemp.equals(newInstance)){
//                    break;
//                }
//            }
//            return instanceTemp;
//        }
//    }

    public TestBuilder() {
        this.softAssert = new SoftAssert();
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
    public <T> T newInstance(Class<T> clazz){
        try{
            return clazz.getDeclaredConstructor(TestBuilder.class).newInstance(this);
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
        this.softAssert.assertTrue(SeleniumUtils.isExpectedElementDisplayed(webElement), message);
        return this;
    }

    public TestBuilder checkEqualityOf(By actualElement, By expectedElement, String message){
        this.softAssert.assertEquals(actualElement, expectedElement, message);
        return this;
    }

    /* ------------------------------ METHODS TO WAIT, CHECK AND ASSERT ELEMENTS - END ------------------------------ */

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

    /* ------------------------------------- METHODS TO CLICK ON ELEMENTS - END ------------------------------------- */

    public TestBuilder sendKeysOn(By webElement, String keys){
        SeleniumUtils.waitForElementToBeVisible(webElement).sendKeys(keys);
        return this;
    }

    public TestBuilder goTo(String url){
        SeleniumUtils.gotoURL(url);
        return this;
    }

    public void finish(){
        this.softAssert.assertAll();
    }

}
