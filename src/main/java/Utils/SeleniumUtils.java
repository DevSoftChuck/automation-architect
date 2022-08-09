package Utils;

import Setup.DriverFactory;
import Setup.TestEnvironment;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static Setup.DriverFactory.getDriver;

public class SeleniumUtils {

    private static final FluentWait<WebDriver> wait = new FluentWait<>(DriverFactory.getDriver())
            .withTimeout(Duration.ofSeconds(TestEnvironment.VISIBLE_TIMEOUT))
            .pollingEvery(Duration.ofSeconds(TestEnvironment.POLLING_TIME));

    public static WebElement waitForElementTobeVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitForElementTobePresent(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static void waitForElementToBeNotVisible(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static WebElement waitForElementTobeClickable(By element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitForJavascriptFinishes() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
    }

    public static boolean isExpectedElementDisplayed(By element){
        try{
            return wait.until(ExpectedConditions.visibilityOfElementLocated(element)).isDisplayed();
        }catch (NoSuchElementException | StaleElementReferenceException | TimeoutException ignore){
            return false;
        }
    }

    public static boolean isExpectedElementPresent(By locator){
        try{
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        }catch (NoSuchElementException | StaleElementReferenceException | TimeoutException ignore){
            return false;
        }
    }

    public static void clickByJavascriptExecutor(By locator){
        ((JavascriptExecutor) DriverFactory.getDriver()).executeScript("arguments[0].click();", locator);
    }

    /* ----------------------------------------- METHODS TO DRAG AND DROP ------------------------------------------- */

    public static void dragAndDrop(WebElement element, WebElement target) {
        Actions a = new Actions(getDriver());
        a.dragAndDrop(element, target).build().perform();
    }

    /* -------------------------------------- METHODS TO DRAG AND DROP - END ---------------------------------------- */

    /* -------------------------------------- METHODS TO HOVER OVER ELEMENTS ---------------------------------------- */

    public static void hoverOverElement(WebElement element) {
        Actions a = new Actions(getDriver());
        a.moveToElement(element).build().perform();
    }

    /* ----------------------------------- METHODS TO HOVER OVER ELEMENTS - END ------------------------------------- */

    /* --------------------------------- METHODS TO SELECT ELEMENTS IN DROPDOWN'S ----------------------------------- */

    public void selectFromDropdownByIndex(int value, WebElement webElement) {
        Select dropdown = new Select(webElement);
        dropdown.selectByIndex(value);
    }

    public void selectFromDropdownByText(String textValue, WebElement webElement) {
        Select dropdown = new Select(webElement);
        dropdown.selectByVisibleText(textValue);
    }

    public void selectFromDropdownByValue(String textValue, WebElement webElement) {
        Select dropdown = new Select(webElement);
        dropdown.selectByValue(textValue);
    }

    /* ------------------------------ METHODS TO SELECT ELEMENTS IN DROPDOWN'S - END -------------------------------- */

    /* ------------------------------- METHODS TO SCROLL AND GET ELEMENTS POSITIONS --------------------------------- */

    public static void scrollToTheBottom() {
        JavascriptExecutor jse = (JavascriptExecutor) getDriver();
        jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        // Give the method time to complete scrolling
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void scrollToTheTop() {
        JavascriptExecutor jse = (JavascriptExecutor) getDriver();
        jse.executeScript("window.scrollTo(0, 0)");

        // Give the method time to complete scrolling
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void scrollToElement(WebElement element) {
        JavascriptExecutor jse = (JavascriptExecutor) getDriver();
        jse.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /* ---------------------------- METHODS TO SCROLL AND GET ELEMENTS POSITIONS - END ------------------------------ */

    /* -------------------------------------------- METHODS TO NAVIGATE --------------------------------------------- */

    public static void goBackToPreviousPage() {
        getDriver().navigate().back();
    }


    public static void gotoURL(String url) {
        getDriver().navigate().to(url);
    }

    public static void reloadPage() {
        getDriver().navigate().refresh();
    }

    /* ----------------------------------------- METHODS TO NAVIGATE - END ------------------------------------------ */

    /* ------------------------------------------ METHODS TO MANAGE TABS -------------------------------------------- */

    /** It switches to the tab passed by parameter. The first tab is the number 0.
     * @param numberTab = Number of the tab to which you'd like to switch. The first tab is the number 0.
     */
    public static void switchToTab(int numberTab) {
        ArrayList<String> tabsList = new ArrayList<>(getDriver().getWindowHandles());
        getDriver().switchTo().window(tabsList.get(numberTab));
    }

    public static void openInNewTab(String urlToOpenInNewTab) {
        String a = "window.open('about:blank','_blank');";
        ((JavascriptExecutor)getDriver()).executeScript(a);
        switchToTab(1);
        getDriver().navigate().to(urlToOpenInNewTab);
    }

    public static void closeCurrentTab() {
        getDriver().close();
    }

    public static int getNumberOfOpenTabs() {
        ArrayList<String> tabsList = new ArrayList<String>(getDriver().getWindowHandles());
        return tabsList.size();
    }

    /* --------------------------------------- METHODS TO MANAGE TABS - END ----------------------------------------- */

}