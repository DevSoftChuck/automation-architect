package PageObjects.Web;

import PageObjects.Page;
import Utils.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class StandardObjectPage extends Page<StandardObjectPage> {

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

    /* --------------------------------------------- PAGE OBJECT METHODS -------------------------------------------- */

    public StandardObjectPage open() {
        return new StandardObjectPage().openPage(StandardObjectPage.class);
    }

    public void isLoaded() {
        Utils.waitForDocumentToBeReady();
    }

    /* ------------------------------------------ PAGE OBJECT METHODS - END ----------------------------------------- */

    /* --------------------------------------- METHODS TO GET AND SET A TEXT ---------------------------------------- */

    /** Get the column number from the column name, Return -1 in case column isn't found **/
    public int getColumnNumber(String columnName){
        int count = 0;
        for (WebElement column : columns) {
            count++;
            if(column.getAttribute("aria-label").equals(columnName)){
                return count;
            }
        }
        return -1;
    }

    /** Get WebElement from the column name and specific row
     *
     * Index                        -> ./span/div/span
     * Row checkbox                 -> ./input[@type='checkbox']
     * Object Number                -> .//a
     * Content with edit button     -> ./span/*
     * Action button                -> .//a --> //div[contains(@class,'uiMenuList') and contains(@class,'visible')]//a[@title='Edit']
     * **/
    public WebElement getWebElementFromColumn(int rowNumberToSearch, String columnName){
        for (WebElement row : recordRows) {
            int rowNumberInTable = Integer.parseInt(row.findElement(By.xpath("./td[contains(@class,'errorColumn')]")).getText());
            if(rowNumberInTable == rowNumberToSearch){
                List<WebElement> columns = row.findElements(By.xpath("./*"));
                for (int i = 0; i < columns.size(); i++){
                    if(getColumnNumber(columnName) == i){
                        return columns.get(i);
                    }
                }
            }
        }
        return null;
    }

    /* ------------------------------------- METHODS TO GET AND SET A TEXT - END ------------------------------------ */

    /* ---------------------------------------- METHODS TO CLICK ON ELEMENTS ---------------------------------------- */

    /** Click on All option in List view button **/
    public void clickOn(){
    }

    /* ------------------------------------- METHODS TO CLICK ON ELEMENTS - END ------------------------------------- */

    /* --------------------------------- METHODS TO CHECK IF ELEMENTS ARE DISPLAYED --------------------------------- */

    /** Returns true if the -- is displayed. False in other case. **/
    public boolean isDisplayed(){
        return false;// Utils.isExisting();
    }

    /* ------------------------------ METHODS TO CHECK IF ELEMENTS ARE DISPLAYED - END ------------------------------ */

    /* ----------------------------- METHODS TO CHANGE VALUES IN PICKLIST AND DROPDOWNS ----------------------------- */

    /** Click on All option in List view button **/
    public void changeListViewTo(String listView){
        Utils.waitForElementClickable(this.listViewBtn);
        this.listViewBtn.click();

        WebElement allListView = Utils.waitForElementVisibleAndReturned(
                By.xpath("//span[@class=' virtualAutocompleteOptionText' and text()='"+ listView +"']/ancestor::a"));
        Utils.waitForElementClickable(allListView);
        allListView.click();
    }

    /* -------------------------- METHODS TO CHANGE VALUES IN PICKLIST AND DROPDOWNS - END -------------------------- */

}
