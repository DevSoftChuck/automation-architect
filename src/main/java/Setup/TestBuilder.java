package Setup;

import Utils.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

import java.util.HashSet;
import java.util.Set;

public class TestBuilder<T> {
    private final SoftAssert softAssert;
    private final Set<T> uniques = new HashSet<>();

    public TestBuilder(SoftAssert softAssert) {
        this.softAssert = softAssert;
    }

    private T returnOrCreateInstance(Class<T> clazz){
        T newInstance = newInstance(clazz);
        if (this.uniques.add(newInstance)){
            return newInstance;
        }else {
            T instanceTemp = null;
            for(T unique : this.uniques){
                instanceTemp = unique;
                if(instanceTemp.equals(newInstance)){
                    break;
                }
            }
            return instanceTemp;
        }
    }

    public T newInstance(Class<T> clazz){
        try{
            return (T) clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e){
            throw new RuntimeException(e);
        }
    }

    public TestBuilder<T> clickOn(By webElement){
        SeleniumUtils.waitForElementTobeClickable(webElement).click();
        return this;
    }

    public TestBuilder<T> sendKeysOn(WebElement webElement, String keys){
        SeleniumUtils.waitForElementTobeVisible(webElement).sendKeys(keys);
        return this;
    }

    public TestBuilder<T> checkVisibilityOf(By webElement, String errorMessage){
        this.softAssert.assertTrue(SeleniumUtils.isExpectedElementDisplayed(webElement), errorMessage);
        return this;
    }

    public TestBuilder<T> waitUntilElementDisappears(By element){
        SeleniumUtils.waitForElementNotVisible(element);
        return this;
    }

    public TestBuilder<T> goTo(String url){
        SeleniumUtils.gotoURL(url);
        return this;
    }

    public void build(){
        this.softAssert.assertAll();
    }

}
