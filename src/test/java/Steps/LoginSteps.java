package Steps;

import PageObjects.LoginPage;
import Utils.Utils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class LoginSteps {
    private LoginPage loginPage = new LoginPage().open();

    @When("Tries to log in with the username {string} and password {string}")
    public void triesToLogInWithTheUsernameAndPassword(String username, String password) throws Exception {
        loginPage.isLoaded();
        loginPage.setUsername(Utils.parser(username));
        loginPage.setPassword(Utils.parser(password));
        loginPage.clickOnLoginButton();
    }

    @Then("User gets an error message saying {string}")
    public void userGetsAnErrorMessageSayingError(String error) {
        loginPage.isLoaded();
        Assert.assertEquals(error, loginPage.getErrorMessage(),"Error message is empty or incorrect");
    }

    @Given("User goes to {string} page")
    public void userGoesToPage(String page) throws Exception {
        String pageUrl = Utils.parser(page);
        Utils.gotoURL(pageUrl);
    }

    @Given("User is on Login page")
    public void userIsOnLoginPage() {
        loginPage.isLoaded();
        Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "The login button isn't displayed");
    }
}
