package PageObjects.Web;

import PageObjects.Page;
import Setup.DriverFactory.DriverFactory;
import Utils.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends Page<LoginPage> {

    /* ------------------------------------------------ WEB ELEMENTS ------------------------------------------------ */

    //Salesforce Logo
    @FindBy(xpath = "//img[@name='logo']")
    private WebElement logoImg;

    //Username field
    @FindBy(id = "username")
    private WebElement usernameInput;

    //Password field
    @FindBy(id = "password")
    private WebElement passwordInput;

    //Login button
    @FindBy(id = "Login")
    private WebElement loginBtn;

    //Remember checkbox
    @FindBy(xpath = "//input[@name='rememberUn']")
    private WebElement rememberChk;

    //Forgot password link
    @FindBy(xpath = "//a[@id='forgot_password_link']")
    private WebElement forgotPasswordLink;

    //Error message label
    @FindBy(xpath = "//div[@id='error']")
    private WebElement errorLbl;

    /* --------------------------------------------- WEB ELEMENTS - END --------------------------------------------- */

    /* --------------------------------------------- PAGE OBJECT METHODS -------------------------------------------- */

    public LoginPage open() {
        return new LoginPage().openPage(LoginPage.class);
    }

    public void isLoaded() {
        Utils.waitForElementVisible(this.loginBtn);
    }

    /* ------------------------------------------ PAGE OBJECT METHODS - END ----------------------------------------- */

    /* --------------------------------------- METHODS TO GET AND SET A TEXT ---------------------------------------- */

    /** Sets a text on the username field **/
    public void setUsername(String username){
        Utils.waitForElementVisible(this.usernameInput);
        usernameInput.sendKeys("");
        usernameInput.sendKeys(username);
    }

    /** Sets a text on the password field **/
    public void setPassword(String password){
        Utils.waitForElementVisible(this.passwordInput);
        passwordInput.sendKeys("");
        passwordInput.sendKeys(password);
    }

    /** Gets text from the error message **/
    public String getErrorMessage(){
        Utils.waitForElementVisible(this.errorLbl);
        return this.errorLbl.getText();
    }

    /* ------------------------------------- METHODS TO GET AND SET A TEXT - END ------------------------------------ */

    /* ---------------------------------------- METHODS TO CLICK ON ELEMENTS ---------------------------------------- */

    /** Clicks on Login button **/
    public void clickOnLoginButton(){
        Utils.waitForElementClickable(this.loginBtn);
        this.loginBtn.click();
    }

    /* ------------------------------------- METHODS TO CLICK ON ELEMENTS - END ------------------------------------- */


    /* --------------------------------- METHODS TO CHECK IF ELEMENTS ARE DISPLAYED --------------------------------- */

    /** Returns true if the login button is displayed. False in other case. **/
    public boolean isLoginButtonDisplayed(){
        return Utils.isExisting(this.loginBtn);
    }

    /* ------------------------------ METHODS TO CHECK IF ELEMENTS ARE DISPLAYED - END ------------------------------ */

}
