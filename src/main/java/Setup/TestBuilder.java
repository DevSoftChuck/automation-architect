package Setup;

import Utils.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

import java.lang.reflect.InvocationTargetException;

public class TestBuilder {
    private final SoftAssert softAssert;
    private final TestBuilder testBuilder;

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

    public TestBuilder(SoftAssert softAssert) {
        this.softAssert = softAssert;
        this.testBuilder = this;
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
     * Class[] cArg = new Class[3]; //Our constructor has 3 arguments
     * cArg[0] = Long.class; //First argument is of *object* type Long
     * cArg[1] = String.class; //Second argument is of *object* type String
     * cArg[2] = int.class; //Third argument is of *primitive* type int
     *
     * Long l = new Long(88);
     * String s = "text";
     * int i = 5;
     *
     * classToLoad.getDeclaredConstructor(cArg).newInstance(l, s, i);
     *
     * */
    public <T> T newInstance(Class<T> clazz){
        try{
            Class[] cArg = new Class[1];
            cArg[0] = TestBuilder.class;

            return (T) clazz.getDeclaredConstructor(cArg).newInstance(this);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public TestBuilder waitUntilElementDisappears(By webElement){
        SeleniumUtils.waitForElementToBeNotVisible(webElement);
        return this;
    }

    public TestBuilder clickOn(By webElement){
        SeleniumUtils.waitForElementTobeClickable(webElement).click();
        return this;
    }

    public TestBuilder ignoreOrClickOn(By webElement, By waitForItDisappears){
        try{
            SeleniumUtils.waitForElementTobeClickable(webElement).click();
        }catch (Exception ignore){

        }
        return this;
    }

    public TestBuilder waitAndClickOn(By webElement, By waitForVisibility){
        SeleniumUtils.waitForElementToBeNotVisible(waitForVisibility);
        SeleniumUtils.waitForElementTobeClickable(webElement).click();
        return this;
    }

    public TestBuilder sendKeysOn(By webElement, String keys){
        SeleniumUtils.waitForElementTobeVisible(webElement).sendKeys(keys);
        return this;
    }

    public TestBuilder checkVisibilityOf(By webElement, String errorMessage){
        this.softAssert.assertTrue(SeleniumUtils.isExpectedElementDisplayed(webElement), errorMessage);
        return this;
    }

    public TestBuilder goTo(String url){
        SeleniumUtils.gotoURL(url);
        return this;
    }

    public void assertAll(){
        this.softAssert.assertAll();
    }

}
