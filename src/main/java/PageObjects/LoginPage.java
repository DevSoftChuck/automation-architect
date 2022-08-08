package PageObjects;

import Utils.Utils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends Page {

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
}
