package PageObjects;

import Utils.Utils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends Page<HomePage> {

    /* ------------------------------------------------ WEB ELEMENTS ------------------------------------------------ */

    //Success message
    @FindBy(xpath = "//*[@id='flash']")
    private WebElement successMsg;

    /* --------------------------------------------- WEB ELEMENTS - END --------------------------------------------- */

    /* --------------------------------------------- PAGE OBJECT METHODS -------------------------------------------- */

    public HomePage open() {
        return new HomePage().openPage(HomePage.class);
    }

    public void isLoaded() {
        Utils.waitForElementVisible(this.successMsg);
        Utils.waitForDocumentToBeReady();
    }

    /* ------------------------------------------ PAGE OBJECT METHODS - END ----------------------------------------- */

    /* --------------------------------------- METHODS TO GET AND SET A TEXT ---------------------------------------- */

    /** Get a text from the Success message **/
    public String getSuccessMsg(){
        Utils.waitForElementVisible(this.successMsg);
        return this.successMsg.getText();
    }

    /* ------------------------------------- METHODS TO GET AND SET A TEXT - END ------------------------------------- */

    /* ---------------------------------------- METHODS TO CLICK ON ELEMENTS ----------------------------------------- */

    /** Clicks on Logout button **/
    public void clickLogoutButton(){
        Utils.waitForElementClickable(this.successMsg);
        this.successMsg.click();
    }

    /* -------------------------------------- METHODS TO CLICK ON ELEMENTS - END -------------------------------------- */

    /* ---------------------------------- METHODS TO CHECK IF ELEMENTS ARE DISPLAYED ---------------------------------- */

    /** Returns true if the logout button is displayed. False in other case. **/
    public boolean isLogoutButtonDisplayed(){
        return Utils.isExisting(this.successMsg);
    }

    /* ---------------------------------- METHODS TO CHECK IF ELEMENTS ARE DISPLAYED ---------------------------------- */

}
