package Utils;

import Setup.DriverFactory;
import Setup.TestEnvironment;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.util.ArrayList;

public class SeleniumUtils {

    private static final FluentWait<WebDriver> wait = new FluentWait<>(DriverFactory.getDriver())
            .withTimeout(Duration.ofSeconds(TestEnvironment.VISIBLE_TIMEOUT))
            .pollingEvery(Duration.ofSeconds(TestEnvironment.POLLING_TIME));

    private static WebElement findElement(By locator){
        return DriverFactory.getDriver().findElement(locator);
    }

    private WebElement findElements(By locator){
        return DriverFactory.getDriver().findElement(locator);
    }

    public static WebElement waitForElementTobeVisible(By element) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(element));
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

    public static void dragAndDrop(By element, By target) {
        Actions a = new Actions(DriverFactory.getDriver());
        a.dragAndDrop(findElement(element), findElement(target)).build().perform();
    }

    /* -------------------------------------- METHODS TO DRAG AND DROP - END ---------------------------------------- */

    /* -------------------------------------- METHODS TO HOVER OVER ELEMENTS ---------------------------------------- */

    public static void hoverOverElement(By locator) {
        Actions a = new Actions(DriverFactory.getDriver());
        a.moveToElement(findElement(locator)).build().perform();
    }

    /* ----------------------------------- METHODS TO HOVER OVER ELEMENTS - END ------------------------------------- */

    /* --------------------------------- METHODS TO SELECT ELEMENTS IN DROPDOWN'S ----------------------------------- */

    public void selectFromDropdownByIndex(int value, By locator) {
        new Select(findElement(locator)).selectByIndex(value);
    }

    public void selectFromDropdownByText(String textValue, By locator) {
         new Select(findElement(locator)).selectByVisibleText(textValue);
    }

    public void selectFromDropdownByValue(String textValue, By locator) {
        new Select(findElement(locator)).selectByValue(textValue);
    }

    /* ------------------------------ METHODS TO SELECT ELEMENTS IN DROPDOWN'S - END -------------------------------- */

    /* ------------------------------- METHODS TO SCROLL AND GET ELEMENTS POSITIONS --------------------------------- */

    public static void scrollToTheBottom() {
        JavascriptExecutor jse = (JavascriptExecutor) DriverFactory.getDriver();
        jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        // Give the method time to complete scrolling
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void scrollToTheTop() {
        JavascriptExecutor jse = (JavascriptExecutor) DriverFactory.getDriver();
        jse.executeScript("window.scrollTo(0, 0)");

        // Give the method time to complete scrolling
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void scrollToElement(By locator) {
        JavascriptExecutor jse = (JavascriptExecutor) DriverFactory.getDriver();
        jse.executeScript("arguments[0].scrollIntoView(true);", findElement(locator));
    }

    /* ---------------------------- METHODS TO SCROLL AND GET ELEMENTS POSITIONS - END ------------------------------ */

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

    /* ----------------------------------------- METHODS TO NAVIGATE - END ------------------------------------------ */

    /* ------------------------------------------ METHODS TO MANAGE TABS -------------------------------------------- */

    /** It switches to the tab passed by parameter. The first tab is the number 0.
     * @param numberTab = Number of the tab to which you'd like to switch. The first tab is the number 0.
     */
    public static void switchToTab(int numberTab) {
        ArrayList<String> tabsList = new ArrayList<>(DriverFactory.getDriver().getWindowHandles());
        DriverFactory.getDriver().switchTo().window(tabsList.get(numberTab));
    }

    public static void openInNewTab(String urlToOpenInNewTab) {
        String a = "window.open('about:blank','_blank');";
        ((JavascriptExecutor)DriverFactory.getDriver()).executeScript(a);
        switchToTab(1);
        DriverFactory.getDriver().navigate().to(urlToOpenInNewTab);
    }

    public static void closeCurrentTab() {
        DriverFactory.getDriver().close();
    }

    public static int getNumberOfOpenTabs() {
        ArrayList<String> tabsList = new ArrayList<>(DriverFactory.getDriver().getWindowHandles());
        return tabsList.size();
    }

    /* --------------------------------------- METHODS TO MANAGE TABS - END ----------------------------------------- */

}