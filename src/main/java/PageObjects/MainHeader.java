package PageObjects;

import Utils.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainHeader {

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

}
