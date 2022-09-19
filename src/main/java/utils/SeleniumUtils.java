package utils;

import org.openqa.selenium.support.ui.ExpectedCondition;
import setup.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SeleniumUtils {

    public static final int VISIBLE_TIMEOUT = 10;
    public static final long POLLING_TIME = 1;

    private static FluentWait<WebDriver> getFluentWait(){
        return new FluentWait<>(DriverFactory.getDriver())
                .withTimeout(Duration.ofSeconds(VISIBLE_TIMEOUT))
                .pollingEvery(Duration.ofSeconds(POLLING_TIME));
    }

    /* -------------------------------------------------- WAITS ----------------------------------------------------- */

    public static WebElement waitForElementToBeVisible(Object element){
        return switch (element){
            case WebElement webElement -> getFluentWait().until(ExpectedConditions.visibilityOf(webElement));
            case By locator -> getFluentWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
            default -> throw new IllegalArgumentException("Unexpected value: " + element);
        };
    }

    public static List<WebElement> waitForAllElementsToBeVisible(Object element) {
        return switch (element){
            case WebElement webElement -> getFluentWait()
                    .until(ExpectedConditions.visibilityOfAllElements(webElement));
            case By locator -> getFluentWait()
                    .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
            default -> throw new IllegalArgumentException("Unexpected value: " + element);
        };
    }

    public static WebElement waitForElementToBePresent(Object element) {
        return switch (element){
            case WebElement webElement -> getFluentWait().until(webDriver -> webElement);
            case By locator -> getFluentWait().until(ExpectedConditions.presenceOfElementLocated(locator));
            default -> throw new IllegalArgumentException("Unexpected value: " + element);
        };
    }

    public static void waitForElementToBeNotVisible(Object element) {
        switch (element){
            case WebElement webElement -> getFluentWait().until(ExpectedConditions.invisibilityOf(webElement));
            case By locator -> getFluentWait().until(ExpectedConditions.invisibilityOfElementLocated(locator));
            default -> throw new IllegalArgumentException("Unexpected value: " + element);
        }
    }

    public static WebElement waitForElementToBeClickable(Object element) {
        return switch (element){
            case WebElement webElement -> getFluentWait().until(ExpectedConditions.elementToBeClickable(webElement));
            case By locator -> getFluentWait().until(ExpectedConditions.elementToBeClickable(locator));
            default -> throw new IllegalArgumentException("Unexpected value: " + element);
        };
    }

    public static boolean waitForJavascriptFinishes() {
        return getFluentWait().until((ExpectedCondition<Boolean>) webDriver ->
                ((JavascriptExecutor) Objects.requireNonNull(webDriver))
                        .executeScript("return document.readyState").equals("complete"));
    }

    public static boolean isExpectedElementDisplayed(Object element){
        try{
            return switch (element){
                case WebElement webElement -> getFluentWait().until(webDriver -> webElement.isDisplayed());
                case By locator -> getFluentWait().until(webDriver -> webDriver.findElement(locator).isDisplayed());
                default -> throw new IllegalStateException("Unexpected value: " + element);
            };
        }catch (NoSuchElementException | StaleElementReferenceException | TimeoutException ignore){
            return false;
        }
    }

    public static boolean isExpectedElementPresent(Object element){
        try{
            switch (element) {
                case WebElement webElement -> {
                    getFluentWait().until(webDriver -> webElement);
                }
                case By locator -> {
                    getFluentWait().until(ExpectedConditions.presenceOfElementLocated(locator));
                }
                default -> throw new IllegalStateException("Unexpected value: " + element);
            }
            return true;
        }catch (NoSuchElementException | StaleElementReferenceException | TimeoutException ignore){
            return false;
        }
    }

    public static boolean verifyPresenceOfElement(By locator){
        return DriverFactory.getDriver().findElements(locator).size() > 0;
    }

    public static WebElement clickByJavascriptExecutor(Object element){
        WebElement elementToClick = waitForElementToBeClickable(element);
        ((JavascriptExecutor) DriverFactory.getDriver()).executeScript("arguments[0].click();", elementToClick);
        return elementToClick;
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
        if (waitForJavascriptFinishes()){
            ((JavascriptExecutor) DriverFactory.getDriver())
                    .executeScript("window.scrollTo(0, document.body.scrollHeight)");
        }
    }

    public static void scrollToTheTop() {
        if (waitForJavascriptFinishes()){
            ((JavascriptExecutor) DriverFactory.getDriver()).executeScript("window.scrollTo(0, 0)");
        }
    }

    public static void scrollToElement(Object element) {
        if (waitForJavascriptFinishes()){
            ((JavascriptExecutor) DriverFactory.getDriver()).executeScript("arguments[0].scrollIntoView(true);",
                    waitForElementToBeVisible(element));
            ((JavascriptExecutor) DriverFactory.getDriver()).executeScript("window.scrollBy(0,-150)");
        }
    }

    /* -------------------------------------------- METHODS TO NAVIGATE --------------------------------------------- */

    public static void goBackToPreviousPage() {
        DriverFactory.getDriver().navigate().back();
    }


    public static void gotoURL(String url) {
        DriverFactory.getDriver().navigate().to(url);
        waitForJavascriptFinishes();
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