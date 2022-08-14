package utils;

import setup.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SeleniumUtils {

    public static final int VISIBLE_TIMEOUT = 30;
    public static final long POLLING_TIME = 5;

    private static FluentWait<WebDriver> getFluentWait(){
        return new FluentWait<>(DriverFactory.getDriver())
                .withTimeout(Duration.ofSeconds(VISIBLE_TIMEOUT))
                .pollingEvery(Duration.ofSeconds(POLLING_TIME));
    }
    /* -------------------------------------------------- WAITS ----------------------------------------------------- */

    public static WebElement waitForElementToBeVisible(By element) {
        return getFluentWait().until(ExpectedConditions.visibilityOfElementLocated(element));
    }

    public static List<WebElement> waitForAllElementsToBeVisible(By element) {
        return getFluentWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(element));
    }

    public static WebElement waitForElementToBePresent(By locator) {
        return getFluentWait().until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static void waitForElementToBeNotVisible(By locator) {
        getFluentWait().until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static WebElement waitForElementToBeClickable(By element) {
        return getFluentWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitForJavascriptFinishes() {
        getFluentWait().until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
    }

    public static boolean isExpectedElementDisplayed(By element){
        try{
            return getFluentWait().until(ExpectedConditions.visibilityOfElementLocated(element)).isDisplayed();
        }catch (NoSuchElementException | StaleElementReferenceException | TimeoutException ignore){
            return false;
        }
    }

    public static boolean isExpectedElementPresent(By locator){
        try{
            getFluentWait().until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        }catch (NoSuchElementException | StaleElementReferenceException | TimeoutException ignore){
            return false;
        }
    }

    public static void clickByJavascriptExecutor(By locator){
        ((JavascriptExecutor) DriverFactory.getDriver()).executeScript("arguments[0].click();",
                waitForElementToBeClickable(locator));
    }

    /* ----------------------------------------- METHODS TO DRAG AND DROP ------------------------------------------- */

    public static void dragAndDrop(By element, By target) {
        Actions actions = new Actions(DriverFactory.getDriver());
        actions.dragAndDrop(waitForElementToBeVisible(element), waitForElementToBeVisible(target)).build().perform();
    }

    /* -------------------------------------- METHODS TO HOVER OVER ELEMENTS ---------------------------------------- */

    public static void hoverOverElement(By locator) {
        Actions actions = new Actions(DriverFactory.getDriver());
        actions.moveToElement(waitForElementToBeVisible(locator)).build().perform();
    }

    /* --------------------------------- METHODS TO SELECT ELEMENTS IN DROPDOWN'S ----------------------------------- */

    public static void selectFromDropdownByIndex(int value, By locator) {
        new Select(waitForElementToBeVisible(locator)).selectByIndex(value);
    }

    public static void selectFromDropdownByText(String textValue, By locator) {
         new Select(waitForElementToBeVisible(locator)).selectByVisibleText(textValue);
    }

    public static void selectFromDropdownByValue(String textValue, By locator) {
        new Select(waitForElementToBeVisible(locator)).selectByValue(textValue);
    }

    /* ------------------------------- METHODS TO SCROLL AND GET ELEMENTS POSITIONS --------------------------------- */

    public static void scrollToTheBottom() {
        ((JavascriptExecutor) DriverFactory.getDriver())
                .executeScript("window.scrollTo(0, document.body.scrollHeight)");

        // Give the method time to complete scrolling
        Utils.pause(1000);
    }

    public static void scrollToTheTop() {
        ((JavascriptExecutor) DriverFactory.getDriver()).executeScript("window.scrollTo(0, 0)");

        // Give the method time to complete scrolling
        Utils.pause(1000);
    }

    public static void scrollToElement(By locator) {
        ((JavascriptExecutor) DriverFactory.getDriver()).executeScript("arguments[0].scrollIntoView(true);",
                waitForElementToBeVisible(locator));
        ((JavascriptExecutor) DriverFactory.getDriver()).executeScript("window.scrollBy(0,-150)");
    }

    /* -------------------------------------------- METHODS TO NAVIGATE --------------------------------------------- */

    public static void goBackToPreviousPage() {
        DriverFactory.getDriver().navigate().back();
    }


    public static void gotoURL(String url) {
        DriverFactory.getDriver().navigate().to(url);
    }

    public static void reloadPage() {
        DriverFactory.getDriver().navigate().refresh();
    }

    /* ------------------------------------------ METHODS TO MANAGE TABS -------------------------------------------- */

    public static void switchToTab(int numberTab) {
        ArrayList<String> tabsList = new ArrayList<>(DriverFactory.getDriver().getWindowHandles());
        DriverFactory.getDriver().switchTo().window(tabsList.get(numberTab));
    }

    public static void openInNewTab(String urlToOpenInNewTab) {
        DriverFactory.getDriver().switchTo().newWindow(WindowType.TAB);
        DriverFactory.getDriver().navigate().to(urlToOpenInNewTab);
    }

    public static void closeCurrentTab() {
        DriverFactory.getDriver().close();
    }

    public static int getNumberOfOpenTabs() {
        ArrayList<String> tabsList = new ArrayList<>(DriverFactory.getDriver().getWindowHandles());
        return tabsList.size();
    }

}