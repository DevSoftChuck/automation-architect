package pages;

import org.openqa.selenium.By;

public class LoginPage extends Page {

    public static By usernameInput = By.xpath("//input[@id='user-name']");
    public static By passwordInput = By.xpath("//input[@id='password']");
    public static By loginBtn = By.xpath("//input[@id='login-button']");

}
