package PageObjects;

import Utils.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class StandardObjectPage extends Page {

    /* ------------------------------------------------ WEB ELEMENTS ------------------------------------------------ */

    //List view button
    @FindBy(xpath = "//div[@data-aura-class='forceListViewPicker']//button[@title='Select List View']")
    private WebElement listViewBtn;

    //Columns within table head
    @FindBy(xpath = "//thead/tr/th")
    private List<WebElement> columns;

    //Record rows within table body
    @FindBy(xpath = "//tbody/tr")
    private List<WebElement> recordRows;

    /* --------------------------------------------- WEB ELEMENTS - END --------------------------------------------- */

}
