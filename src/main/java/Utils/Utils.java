package Utils;

import java.time.Duration;
import java.util.*;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.Toolkit;
import java.awt.HeadlessException;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import Setup.TestEnvironment;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.NoSuchElementException;
import java.io.IOException;
import static Setup.DriverFactory.DriverFactory.getDriver;

public class Utils extends TestEnvironment {

    /* --------------------------------------------------- WAITS ---------------------------------------------------- */

    /** Waits until the element passed by parameter is visible
     * @param element = Element for which it will wait until is visible
     * **/
    public static void waitForElementVisible(WebElement element) {
        try {
            Wait<WebDriver> wait = new FluentWait<>(getDriver())
                    .withTimeout(Duration.ofSeconds(VISIBLE_TIMEOUT))
                    .pollingEvery(Duration.ofSeconds(POLLING_TIME))
                    .ignoring(NoSuchElementException.class)
                    .ignoring(NullPointerException.class)
                    .ignoring(StaleElementReferenceException.class);
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (ElementNotVisibleException e) {
            logger.error(String.format("Couldn't display element \"%S\"!", element));
        }
    }

    /** Waits until the element passed by parameter is visible and return it as webelement
     * @param element = By element for which it will wait until is visible
     * **/
    public static WebElement waitForElementVisibleAndReturned(By element) {
        Wait<WebDriver> wait = new FluentWait<>(getDriver())
                .withTimeout(Duration.ofSeconds(VISIBLE_TIMEOUT))
                .pollingEvery(Duration.ofSeconds(POLLING_TIME))
                .ignoring(NoSuchElementException.class)
                .ignoring(NullPointerException.class)
                .ignoring(StaleElementReferenceException.class);
        return wait.until(ExpectedConditions.presenceOfElementLocated(element));
    }

    /** Waits until the element passed by parameter is not visible
     * @param element = Element for which it will wait until is not visible
     * **/
    public static void waitForElementNotVisible(WebElement element) {
        try {
            Wait<WebDriver> wait = new FluentWait<>(getDriver())
                    .withTimeout(Duration.ofSeconds(VISIBLE_TIMEOUT))
                    .pollingEvery(Duration.ofSeconds(POLLING_TIME))
                    .ignoring(NoSuchElementException.class)
                    .ignoring(NullPointerException.class)
                    .ignoring(StaleElementReferenceException.class);
            wait.until(ExpectedConditions.not(ExpectedConditions.visibilityOf(element)));
        } catch (ElementNotVisibleException e) {
            logger.error(String.format("The element \"%S\" is still displayed!", element));
        }
    }

    /** Waits until the element located by the "By" passed by parameter is not visible
     * @param by = Way to locate the element for which it will wait until is not visible
     * **/
    public static void waitForElementNotVisible(By by) {
        try {
            Wait<WebDriver> wait = new FluentWait<>(getDriver())
                    .withTimeout(Duration.ofSeconds(VISIBLE_TIMEOUT))
                    .pollingEvery(Duration.ofSeconds(POLLING_TIME))
                    .ignoring(NoSuchElementException.class)
                    .ignoring(NullPointerException.class)
                    .ignoring(StaleElementReferenceException.class);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
        } catch (ElementNotVisibleException e) {
            logger.error(String.format("The element \"%S\" is still displayed!", by));
        }
    }

    /** Waits until the element located by the "By" passed by parameter is present
     * @param by = Way to locate the element for which it will wait until is present
     * **/
    public static void waitForElementToBePresent(By by) {
        Wait<WebDriver> wait = new FluentWait<>(getDriver())
                .withTimeout(Duration.ofSeconds(VISIBLE_TIMEOUT))
                .pollingEvery(Duration.ofSeconds(POLLING_TIME))
                .ignoring(NoSuchElementException.class)
                .ignoring(NullPointerException.class)
                .ignoring(StaleElementReferenceException.class);
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    /** Waits until the element located by the "By" passed by parameter is not present
     * @param by = Way to locate the element for which it will wait until is not present
     * **/
    public static void waitForElementNotToBePresent(By by) {
        Wait<WebDriver> wait = new FluentWait<>(getDriver())
                .withTimeout(Duration.ofSeconds(VISIBLE_TIMEOUT))
                .pollingEvery(Duration.ofSeconds(POLLING_TIME))
                .ignoring(NoSuchElementException.class)
                .ignoring(NullPointerException.class)
                .ignoring(StaleElementReferenceException.class);
        wait.until(ExpectedConditions.not(ExpectedConditions.presenceOfElementLocated(by)));
    }

    /** Waits until the element passed by parameter is clickable
     * @param element = Element for which it will wait until is clickable
     * **/
    public static void waitForElementClickable(WebElement element) {
        try {
            Wait<WebDriver> wait = new FluentWait<>(getDriver())
                    .withTimeout(Duration.ofSeconds(CLICK_TIMEOUT))
                    .pollingEvery(Duration.ofSeconds(POLLING_TIME))
                    .ignoring(NoSuchElementException.class)
                    .ignoring(NullPointerException.class)
                    .ignoring(StaleElementReferenceException.class);
            wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (ElementNotInteractableException e) {
            logger.error(String.format("Couldn't click on element \"%S\"!", element));
        }
    }

    /** Waits until the condition passed by parameter is fulfilled
     * @param condition = Condition for which it will wait
     * **/
    public static void waitForCondition(ExpectedCondition condition) {
        (new WebDriverWait(getDriver(), Duration.ofSeconds(VISIBLE_TIMEOUT))).until(condition);
    }

    /** Waits until the number of open windows is equal to the number passed by parameter
     * @param numTabs = Number of tabs that should be opened so it stops waiting
     * **/
    public static void waitForNumberOfWindowsToBe(int numTabs) {
        new WebDriverWait(getDriver(), Duration.ofSeconds(VISIBLE_TIMEOUT)).until(ExpectedConditions.numberOfWindowsToBe(numTabs));
    }

    /** Waits until JavaScript finishes processing **/
    public static void waitForDocumentToBeReady() {
        JavascriptExecutor js = (JavascriptExecutor)getDriver();
        if (js.executeScript("return document.readyState").toString().equals("complete")) {
            getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(PAGE_LOAD_TIMEOUT));
        }
        else {
            waitForDocumentToBeReady();
        }
    }

    /** Waits until JavaScript finishes processing **/
    public static boolean isPageReady() {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(PAGE_LOAD_TIMEOUT));
            wait.until(webDriver ->
                    ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        } catch (WebDriverException e) {
            logger.error(String.format("Page wasn't ready to execute tests: %s!", getDriver().getCurrentUrl()));
            return false;
        }
        return true;
    }

    private static ExpectedCondition angularHasFinishedProcessing() {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                String hasAngularFinishedScript = "var callback = arguments[arguments.length - 1];\n" +
                        "var el = document.querySelector('html');\n" +
                        "if (!window.angular) {\n" +
                        "    callback('false')\n" +
                        "}\n" +
                        "if (angular.getTestability) {\n" +
                        "    angular.getTestability(el).whenStable(function(){callback('true')});\n" +
                        "} else {\n" +
                        "    if (!angular.element(el).injector()) {\n" +
                        "        callback('false')\n" +
                        "    }\n" +
                        "    var browser = angular.element(el).injector().get('$browser');\n" +
                        "    browser.notifyWhenNoOutstandingRequests(function(){callback('true')});\n" +
                        "}";

                JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
                String isProcessingFinished = javascriptExecutor.executeAsyncScript(hasAngularFinishedScript).toString();

                return Boolean.valueOf(isProcessingFinished);
            }
        };
    }

    /** Waits until Angular finishes processing **/
    public static void waitForAngular() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(SCRIPT_TIMEOUT));
        wait.until(angularHasFinishedProcessing());
    }

    /* ------------------------------------------------ WAITS - END ------------------------------------------------- */

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
        waitForDocumentToBeReady();
        JavascriptExecutor jse = (JavascriptExecutor) getDriver();
        jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        // Give the method time to complete scrolling
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        waitForDocumentToBeReady();
    }

    /** Scrolls to the top of the page **/
    public static void scrollToTheTop() {
        waitForDocumentToBeReady();
        JavascriptExecutor jse = (JavascriptExecutor) getDriver();
        jse.executeScript("window.scrollTo(0, 0)");

        // Give the method time to complete scrolling
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        waitForDocumentToBeReady();
    }

    /** Scrolls to the element passed by parameter
     * @param element = Element to where you'd like to scroll
     * **/
    public static void scrollToElement(WebElement element) {
        waitForDocumentToBeReady();
        JavascriptExecutor jse = (JavascriptExecutor) getDriver();
        jse.executeScript("arguments[0].scrollIntoView(true);", element);
        waitForDocumentToBeReady();
    }

    /* ---------------------------- METHODS TO SCROLL AND GET ELEMENTS POSITIONS - END ------------------------------ */

    /* -------------------------------------- METHODS TO HOVER OVER ELEMENTS ---------------------------------------- */

    /** Hovers over the element passed by parameter
     * @param element = Element you'd like to hover over
     * **/
    public static void hoverOverElement(WebElement element) {
        Actions a = new Actions(getDriver());
        a.moveToElement(element).build().perform();
    }

    /* ----------------------------------- METHODS TO HOVER OVER ELEMENTS - END ------------------------------------- */

    /* ----------------------------------------- METHODS TO DRAG AND DROP ------------------------------------------- */

    /** This method drags the first element passed by parameter and drops it on the position of the second element
     * @param element = Element you'd like to drag
     * @param target = Element on the position you'd like to drop the first element
     * **/
    public static void dragAndDrop(WebElement element, WebElement target) {
        Actions a = new Actions(getDriver());
        a.dragAndDrop(element, target).build().perform();
        waitForAngular();
    }

    /* -------------------------------------- METHODS TO DRAG AND DROP - END ---------------------------------------- */

    /* -------------------------------- METHODS TO CHECK IF ELEMENTS ARE DISPLAYED ---------------------------------- */

    /** Returns true if the element passed by parameter is displayed
     * @param element = Element you'd like to know if is displayed
     * **/
    public static boolean isExisting(WebElement element) {
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(VISIBLE_TIMEOUT));
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /** Returns true if the element located by the "By" passed by parameter is displayed
     * @param by = Way to locate the element you'd like to know if is displayed
     * **/
    public static boolean isExisting(By by) {
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(VISIBLE_TIMEOUT));
        List <WebElement> elements =  getDriver().findElements(by);
        return elements.size()>0;
    }

    /** Returns true if the list of web elements passed by parameter is not empty
     * @param elements = List of web elements you'd like to know if is empty
     * **/
    public static boolean isExisting(List<WebElement> elements) {
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(VISIBLE_TIMEOUT));
        return elements.size()>0;
    }

    /* ----------------------------- METHODS TO CHECK IF ELEMENTS ARE DISPLAYED - END ------------------------------- */

    /* --------------------------------- METHODS TO SELECT ELEMENTS IN DROPDOWN'S ----------------------------------- */

    public void selectFromDropdownByIndex(int value, WebElement webElement) {
        try {
            Select dropdown = new Select(webElement);
            dropdown.selectByIndex(value);
        } catch (ElementNotSelectableException e) {
            logger.error(String.format("Couldn't select \"%S\" from element \"%S\"!", value, webElement));
        }
    }

    public void selectFromDropdownByText(String textValue, WebElement webElement) {
        try {
            Select dropdown = new Select(webElement);
            dropdown.selectByVisibleText(textValue);
        } catch (ElementNotSelectableException e) {
            logger.error(String.format("Couldn't select \"%S\" from element \"%S\"!", textValue, webElement));
        }
    }

    public void selectFromDropdownByValue(String textValue, WebElement webElement) {
        try {
            Select dropdown = new Select(webElement);
            dropdown.selectByValue(textValue);
        } catch (ElementNotSelectableException e) {
            logger.error(String.format("Couldn't select \"%S\" from element \"%S\"!", textValue, webElement));
        }
    }

    /* ------------------------------ METHODS TO SELECT ELEMENTS IN DROPDOWN'S - END -------------------------------- */

    /* -------------------------------------- METHODS TO GENERATE RANDOM DATA --------------------------------------- */

    //Variables that store the different characters to use for the random data that will be generated.
    private static final String UPPERCASE_ALPHANUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String LOWERCASE_ALPHANUMERIC_STRING = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final String ALPHANUMERIC_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String NUMERIC_STRING = "0123456789";
    private static final String ONLY_LETTERS_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /** Returns a random number with the number of digits passed by parameter
     * @param numberOfDigits = Number of digits you'd like the random number to have
     * **/
    private static String randomNumber(int numberOfDigits) {
        StringBuilder builder = new StringBuilder();
        while (numberOfDigits-- != 0) {
            int character = (int) (Math.random() * NUMERIC_STRING.length());
            builder.append(NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    /** Returns a random string consisting onf letters only with the number of digits passed by parameter
     * @param numberOfDigits = Number of digits you'd like the random number to have
     * **/
    private static String randomNonNumericString(int numberOfDigits) {
        StringBuilder builder = new StringBuilder();
        while (numberOfDigits-- != 0) {
            int character = (int) (Math.random() * ONLY_LETTERS_STRING.length());
            builder.append(ONLY_LETTERS_STRING.charAt(character));
        }
        return builder.toString();
    }

    /** Returns a random uppercase alphanumeric string with the number of characters passed by parameter
     * @param numberOfCharacters = Number of characters you'd like the string to have
     * **/
    private static String randomUppercaseString(int numberOfCharacters) {
        StringBuilder builder = new StringBuilder();
        while (numberOfCharacters-- != 0) {
            int character = (int) (Math.random() * UPPERCASE_ALPHANUMERIC_STRING.length());
            builder.append(UPPERCASE_ALPHANUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    /** Returns a random lowercase alphanumeric string with the number of characters passed by parameter
     * @param numberOfCharacters = Number of characters you'd like the string to have
     * **/
    private static String randomLowercaseString(int numberOfCharacters) {
        StringBuilder builder = new StringBuilder();
        while (numberOfCharacters-- != 0) {
            int character = (int) (Math.random() * LOWERCASE_ALPHANUMERIC_STRING.length());
            builder.append(LOWERCASE_ALPHANUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    /** Returns a random alphanumeric string that has lowercase and uppercase characters, with the number of characters passed by parameter
     * @param numberOfCharacters = Number of characters you'd like the string to have
     * **/
    private static String randomString(int numberOfCharacters) {
        StringBuilder builder = new StringBuilder();
        while (numberOfCharacters-- != 0) {
            int character = (int) (Math.random() * ALPHANUMERIC_STRING.length());
            builder.append(ALPHANUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    /** Returns a random alphanumeric string of 8 characters **/
    public static String generateRandomName() {
        return randomString(8).toLowerCase();
    }

    /** Returns a random valid email address **/
    public static String generateRandomEmail() {
        return randomString(8).toLowerCase() + "@" + randomString(8).toLowerCase() + ".io";
    }

    /** Returns a random number between the two numbers passed by parameter
     * @param start = Smallest valid number the method can return
     * @param end = Largest valid number the method can return
     * **/
    public static int randomBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }

    /** Returns a random valid date in the next two years **/
    public static String generateRandomDate() throws ParseException {
        GregorianCalendar gc = new GregorianCalendar();

        int year = randomBetween(gc.get(GregorianCalendar.YEAR) + 1, gc.get(GregorianCalendar.YEAR) + 2);
        gc.set(GregorianCalendar.YEAR, year);

        int month = randomBetween(1, 12);
        gc.set(GregorianCalendar.MONTH, month);

        int day = randomBetween(1, gc.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));
        gc.set(GregorianCalendar.DAY_OF_MONTH, day);

        String date = gc.get(GregorianCalendar.MONTH) + "/" + gc.get(GregorianCalendar.DAY_OF_MONTH) + "/" + gc.get(GregorianCalendar.YEAR);
        Date dateParsed = new SimpleDateFormat("MM/dd/yyyy").parse(date);

        return new SimpleDateFormat("MM/dd/yyyy").format(dateParsed);
    }

    /* ----------------------------------- METHODS TO GENERATE RANDOM DATA - END ------------------------------------ */

    /* ----------------------------------------------- PARSER METHODS ----------------------------------------------- */

    /**
     * This method is used to convert some specific values sent in the cucumber scenarios to random data and data obtained
     * from the properties file. The following will be an explanation of what values you should use and what they will be parsed to.
     *
        RANDOM:
        ${} – everything inside will be parsed, strings are comma separated
         ! – use the string as it is
         N – random numbers
         S – random alphanumeric uppercase string
         s – random alphanumeric lowercase string
         m/d/y - random date with format: MM/dd/yyyy
         a - random alphanumeric string
        So:
            ${!0046,N8} will produce ‘0046’ followed by 8 random numbers.
            ${!test,a4,@,a8,!.com} will generate ‘test’ followed by 4 random alphanumeric letters, symbol ‘@’, 8 random alphanumeric letters and ‘.com’.

        CONSTANT:
        $[] – everything inside will be parsed.
        So:
            $[USER] -> value of the USER property in the properties file
     **/
    public static String parser(String source) throws Exception {
        if (!source.isEmpty()) {
            if (source.charAt(0) == '$' && source.charAt(1) == '{' && source.charAt(source.length() - 1) == '}'){
                return randomGenerator(source);
            } else if (source.charAt(0) == '$' && source.charAt(1) == '[' && source.charAt(source.length() - 1) == ']') {
                return constantSearch(source);
            }
        }
        return source;
    }


    private static String randomGenerator(String source) throws Exception {
        String result = "";
        source = source.replace("$", "").replace("{", "").replace("}", "");
        String[] splitSource = source.split(",");
        for (String s : splitSource) {
            String a = randomParser(s);
            result = result + a;
        }
        return result;
    }

    private static String randomParser(String random) throws Exception {
        if (random.charAt(0) == '!'){
            return random.substring(1);
        } else if (random.charAt(0) == 'N' && random.length() >= 2) {
            return randomNumber(Integer.parseInt(random.substring(1)));
        } else if (random.charAt(0) == 'L') {
            return randomNonNumericString(Integer.parseInt(random.substring(1)));
        } else if (random.charAt(0) == 'S') {
            return randomUppercaseString(Integer.parseInt(random.substring(1)));
        } else if (random.charAt(0) == 's') {
            return randomLowercaseString(Integer.parseInt(random.substring(1)));
        } else if (random.equals("m/d/y")) {
            return generateRandomDate();
        } else if (random.charAt(0) == 'a') {
            return randomString(Integer.parseInt(random.substring(1)));
        } else {
            return random;
        }
    }

    private static String constantSearch(String source) {
        source = source.replace("$", "").replace("[", "").replace("]", "");
        try {
            return PropertiesManager.getConfig(EPropertiesNames.valueOf(source));
        } catch(Exception e){
            System.out.println("Constant not found: " + e);
        }
        return source;
    }

    public static String getConstantValue(EPropertiesNames property) {
        String value = "";
        try {
            return PropertiesManager.getConfig(property);
        } catch(Exception e){
            System.out.println("Constant "+property.name()+" not found: " + e);
        }
        return value;
    }

    /* -------------------------------------------- PARSER METHODS - END -------------------------------------------- */

    /* --------------------------------------------- MANIPULATE STRINGS --------------------------------------------- */

    /** Method to get rid of the last characters from a string.
     * @param str = Complete string
     * @param charactersQty = Amount of letters to get rid of.
     * @return the string without the amount of characters you wanted, starting from the end.
     * **/
    public static String getRidOfLastLetters(String str, int charactersQty) {
        return str.substring(0, str.length() - charactersQty);
    }

    public static String getRidOfFirstLetters(String str, int charactersQty) {
        return str.substring(charactersQty);
    }

    /** Method that returns the text you currently have on the clipboard **/
    public static String getClipboardText() {
        try {
            return (String)Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
        } catch (HeadlessException | UnsupportedFlavorException | IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /* ------------------------------------------ MANIPULATE STRINGS - END ------------------------------------------ */

}
