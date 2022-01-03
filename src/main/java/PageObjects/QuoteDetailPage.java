package PageObjects;

import Utils.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class QuoteDetailPage extends Page<QuoteDetailPage>{

    /* ------------------------------------------------ WEB ELEMENTS ------------------------------------------------ */



    /* --------------------------------------------- WEB ELEMENTS - END --------------------------------------------- */

    /* --------------------------------------------- PAGE OBJECT METHODS -------------------------------------------- */

    public QuoteDetailPage open() {
        return new QuoteDetailPage().openPage(QuoteDetailPage.class);
    }

    public void isLoaded() {
        Utils.waitForDocumentToBeReady();
    }

    /* ------------------------------------------ PAGE OBJECT METHODS - END ----------------------------------------- */

    /* --------------------------------------- METHODS TO GET AND SET A TEXT ---------------------------------------- */

    /** Sets a text on the -- field **/
    public void set(String arg){
    }

    /* ------------------------------------- METHODS TO GET AND SET A TEXT - END ------------------------------------ */

    /* ---------------------------------------- METHODS TO CLICK ON ELEMENTS ---------------------------------------- */

    /** Click on -- button **/
    public void clickOn(){
//        Utils.waitForElementClickable();
    }

    /* ------------------------------------- METHODS TO CLICK ON ELEMENTS - END ------------------------------------- */

    /* ---------------------------------- METHODS TO CHECK IF ELEMENTS ARE DISPLAYED ---------------------------------- */

    /** Returns true if the -- is displayed. False in other case. **/
    public boolean isDisplayed(){
        return false;// Utils.isExisting();
    }

    /* ------------------------------- METHODS TO CHECK IF ELEMENTS ARE DISPLAYED - END ------------------------------- */

}
