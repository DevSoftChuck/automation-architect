package PageObjects.Mobile;


import PageObjects.Page;
import Utils.Utils;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class ApiDemos extends Page<ApiDemos> {

    /* ---------------------------------------------- MOBILE ELEMENTS ----------------------------------------------- */

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Preference']")
    public WebElement preferenceLbl;

    /* ------------------------------------------- MOBILE ELEMENTS - END -------------------------------------------- */

    /* --------------------------------------------- PAGE OBJECT METHODS -------------------------------------------- */

    public ApiDemos open() {
        return new ApiDemos().openMobilePage(ApiDemos.class);
    }

    public void isLoaded() {
        Utils.waitForElementVisible(this.preferenceLbl);
    }

    /* ------------------------------------------ PAGE OBJECT METHODS - END ----------------------------------------- */

    /* ---------------------------------------- METHODS TO CLICK ON ELEMENTS ---------------------------------------- */

    /** Clicks on preference label **/
    public void clickOnPreferenceLabel(){

    }

    /* ------------------------------------- METHODS TO CLICK ON ELEMENTS - END ------------------------------------- */
}
