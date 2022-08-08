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

    /**
     * Waits until the element passed by parameter is visible
     *
     * @param element = Element for which it will wait until is visible
     */
    public static WebElement waitForElementTobeVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Waits until the element located by the "By" passed by parameter is present
     *
     * @param locator = Element for which it will wait until is present is DOM
     */
    public static WebElement waitForElementTobePresent(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Waits until all elements passed by parameter are visible
     *
     * @param elements = Elements for which it will wait until all them are visible
     */
    public static List<WebElement> waitForAllElementsTobeVisible(List<WebElement> elements) {
        return wait.until(ExpectedConditions.visibilityOfAllElements(elements));
    }

    /**
     * Waits until the element passed by parameter is not visible
     *
     * @param locator = Element for which it will wait until is not visible
     */
    public static void waitForElementNotVisible(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Waits until the element passed by parameter is clickable
     *
     * @param element = Element for which it will wait until is clickable
     **/
    public static WebElement waitForElementTobeClickable(By element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Waits until JavaScript finishes processing
     **/
    public static void waitForJavascriptFinishes() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
    }

    /**
     * Returns true is the element passed by parameter is displayed, false in other case
     * @param element Element you would like to know if is displayed
     */
    public static boolean isExpectedElementDisplayed(By element){
        try{
            return wait.until(ExpectedConditions.visibilityOfElementLocated(element)).isDisplayed();
        }catch (NoSuchElementException | StaleElementReferenceException | TimeoutException ignore){
            return false;
        }
    }

    /**
     * Returns true is the element passed by parameter is Present, false in other case
     * @param locator Element you would like to know if is present
     */
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

    /** This method drags the first element passed by parameter and drops it on the position of the second element
     * @param element = Element you'd like to drag
     * @param target = Element on the position you'd like to drop the first element
     * **/
    public static void dragAndDrop(WebElement element, WebElement target) {
        Actions a = new Actions(getDriver());
        a.dragAndDrop(element, target).build().perform();
    }

    /* -------------------------------------- METHODS TO DRAG AND DROP - END ---------------------------------------- */

    /* -------------------------------------- METHODS TO HOVER OVER ELEMENTS ---------------------------------------- */

    /** Hovers over the element passed by parameter
     * @param element = Element you'd like to hover over
     * **/
    public static void hoverOverElement(WebElement element) {
        Actions a = new Actions(getDriver());
        a.moveToElement(element).build().perform();
    }

    /* ----------------------------------- METHODS TO HOVER OVER ELEMENTS - END ------------------------------------- */

    /* --------------------------------- METHODS TO SELECT ELEMENTS IN DROPDOWN'S ----------------------------------- */

    public void selectFromDropdownByIndex(int value, WebElement webElement) {
        try {
            Select dropdown = new Select(webElement);
            dropdown.selectByIndex(value);
        } catch (ElementNotSelectableException e) {
            TestEnvironment.logger.error(String.format("Couldn't select \"%S\" from element \"%S\"!", value, webElement));
        }
    }

    public void selectFromDropdownByText(String textValue, WebElement webElement) {
        try {
            Select dropdown = new Select(webElement);
            dropdown.selectByVisibleText(textValue);
        } catch (ElementNotSelectableException e) {
            TestEnvironment.logger.error(String.format("Couldn't select \"%S\" from element \"%S\"!", textValue, webElement));
        }
    }

    public void selectFromDropdownByValue(String textValue, WebElement webElement) {
        try {
            Select dropdown = new Select(webElement);
            dropdown.selectByValue(textValue);
        } catch (ElementNotSelectableException e) {
            TestEnvironment.logger.error(String.format("Couldn't select \"%S\" from element \"%S\"!", textValue, webElement));
        }
    }

    /* ------------------------------ METHODS TO SELECT ELEMENTS IN DROPDOWN'S - END -------------------------------- */

    /* ------------------------------- METHODS TO SCROLL AND GET ELEMENTS POSITIONS --------------------------------- */

    /** Returns the vertical position in the page of the element passed by parameter
     * @param element = Element you'd like to know the vertical position of
     * **/
    public static int getElementYPosition(WebElement element) {
        return element.getLocation().getY();
    }

    /** Returns the vertical pixels position of the window: 0 = top, >0 = number of pixels scrolled down **/
    public static Long scrollPosition() {
        JavascriptExecutor js = (JavascriptExecutor)getDriver();
        return (Long) js.executeScript("return window.scrollY;");
    }

    /** Scrolls to the bottom of the page **/
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

    /** Scrolls to the top of the page **/
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

    /** Scrolls to the element passed by parameter
     * @param element = Element to where you'd like to scroll
     * **/
    public static void scrollToElement(WebElement element) {
        JavascriptExecutor jse = (JavascriptExecutor) getDriver();
        jse.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /* ---------------------------- METHODS TO SCROLL AND GET ELEMENTS POSITIONS - END ------------------------------ */

    /* -------------------------------------------- METHODS TO NAVIGATE --------------------------------------------- */

    /** This method goes back to the previous page **/
    public static void goBackToPreviousPage() {
        getDriver().navigate().back();
    }

    /** This method goes to the page with the URL passed by parameter
     * @param url = URL you'd like to go to
     * **/
    public static void gotoURL(String url) {
        getDriver().navigate().to(url);
    }

    /** This method reloads the current page **/
    public static void reloadPage() {
        getDriver().navigate().refresh();
    }

    /* ----------------------------------------- METHODS TO NAVIGATE - END ------------------------------------------ */

    /* ------------------------------------------ METHODS TO MANAGE TABS -------------------------------------------- */

    /** It switches to the tab passed by parameter. The first tab is the number 0.
     * @param numberTab = Number of the tab to which you'd like to switch. The first tab is the number 0.
     * **/
    public static void switchToTab(int numberTab) {
        ArrayList<String> tabsList = new ArrayList<String>(getDriver().getWindowHandles());
        getDriver().switchTo().window(tabsList.get(numberTab));
    }

    /** It switches to the second tab and goes to the URL passed by parameter.
     * @param urlToOpenInNewTab = URL you'd like to open in the second tab.
     * **/
    public static void openInNewTab(String urlToOpenInNewTab) {
        String a = "window.open('about:blank','_blank');";
        ((JavascriptExecutor)getDriver()).executeScript(a);
        switchToTab(1);
        getDriver().navigate().to(urlToOpenInNewTab);
    }

    /** It closes the tab currently opened **/
    public static void closeCurrentTab() {
        getDriver().close();
    }

    /** It closes the tab currently opened **/
    public static int getNumberOfOpenTabs() {
        ArrayList<String> tabsList = new ArrayList<String>(getDriver().getWindowHandles());
        return tabsList.size();
    }

    /* --------------------------------------- METHODS TO MANAGE TABS - END ----------------------------------------- */

}