package PageObjects.Web;

import PageObjects.Page;
import Utils.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainHeader extends Page<MainHeader> {

    /* ------------------------------------------------ WEB ELEMENTS ------------------------------------------------ */

    //Profile img
    @FindBy(xpath = "//img[@title='User']")
    private WebElement userProfile;
    private final By userProfileBy = By.xpath("//img[@title='User']");

    //Application name
    @FindBy(xpath = "//span[contains(@class,'appName')]/span")
    private WebElement applicationName;

    //Quotes tab
    @FindBy(xpath = "//a[@title='Quotes']/ancestor::one-app-nav-bar-item-root[contains(@class,'slds-is-active')]")
    private WebElement quoteTab;
    private By quoteTabBy = By.xpath("//a[@title='Quotes']/ancestor::one-app-nav-bar-item-root[contains(@class,'slds-is-active')]");


    /* --------------------------------------------- WEB ELEMENTS - END --------------------------------------------- */

    /* --------------------------------------------- PAGE OBJECT METHODS -------------------------------------------- */

    public MainHeader open() {
        return new MainHeader().openPage(MainHeader.class);
    }

    public void isLoaded() {
        Utils.waitForElementToBePresent(this.userProfileBy);
        Utils.waitForDocumentToBeReady();
    }

    /* ------------------------------------------ PAGE OBJECT METHODS - END ----------------------------------------- */

    /* --------------------------------------- METHODS TO GET AND SET A TEXT ---------------------------------------- */

    /** Gets text from the application name next to the nine dots  **/
    public String getApplicationName(){
        Utils.waitForElementVisible(this.applicationName);
        return this.applicationName.getText();
    }

    /* ------------------------------------- METHODS TO GET AND SET A TEXT - END ------------------------------------- */

    /* ---------------------------------------- METHODS TO CLICK ON ELEMENTS ----------------------------------------- */

    // TO-DO

    /* -------------------------------------- METHODS TO CLICK ON ELEMENTS - END -------------------------------------- */

    /* ---------------------------------- METHODS TO CHECK IF ELEMENTS ARE DISPLAYED ---------------------------------- */

    /** Returns true if the user profile image is displayed. False in other case. **/
    public boolean isUserProfileDisplayed(){
        return Utils.isExisting(this.userProfileBy);
    }

    /** Returns true if the Quote object page is displayed. False in other case. **/
    public boolean isQuoteObjectTabActivated(){
        return Utils.isExisting(this.quoteTabBy);
    }

    /* ---------------------------------- METHODS TO CHECK IF ELEMENTS ARE DISPLAYED ---------------------------------- */

}
