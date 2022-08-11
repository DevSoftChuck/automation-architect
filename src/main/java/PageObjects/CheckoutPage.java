package PageObjects;

import Setup.TestBuilder;
import org.openqa.selenium.By;

public class CheckoutPage extends Header{

    /* ---------------------------------------------- YOUR INFORMATION ---------------------------------------------- */
    public static By firstnameInput = By.name("firstName");
    public static By lastnameInput = By.xpath("//input[@id='last-name']");
    public static By postalCodeInput = By.id("postal-code");
    public static By continueBtn = By.id("continue");

    /* -------------------------------------------------- OVERVIEW -------------------------------------------------- */
    public static By finishBtn = By.id("finish");

    /* -------------------------------------------------- COMPLETE -------------------------------------------------- */
    public static By completionTitle = By.xpath("//h2[@class='complete-header']");

    public CheckoutPage(TestBuilder testBuilder) {
        super(testBuilder);
    }
}
