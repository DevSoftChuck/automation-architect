package Steps;

import PageObjects.LoginPage;
import Setup.TestEnvironment;
import Utils.Utils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class LoginSteps extends TestEnvironment{
    private LoginPage loginPage = new LoginPage().open();

    @When("Tries to log in with the username {string} and password {string}")
    public void triesToLogInWithTheUsernameAndPassword(String username, String password) throws Exception {
        loginPage.isLoaded();
        username = Utils.parser(username);
        password = Utils.parser(password);
        logger.info(String.format("Username: \"%s\"", username));
        logger.info(String.format("User password: \"%s\"", password));
        loginPage.setUsername(username);
        loginPage.setPassword(password);
        loginPage.clickOnLoginButton();
    }

    @Then("User gets an error message saying {string}")
    public void userGetsAnErrorMessageSayingError(String error) {
        loginPage.isLoaded();
        String errorMessage = loginPage.getErrorMessage();
        logger.info(String.format("Error Message: \"%s\"", errorMessage));
        Assert.assertEquals(error, errorMessage,"Error message is empty or incorrect");
    }

    @Given("User goes to {string} page")
    public void userGoesToPage(String page) throws Exception {
        String pageUrl = Utils.parser(page);
        logger.info(String.format("Page name: \"%s\"", pageUrl));
        Utils.gotoURL(pageUrl);
    }

    @Given("User is on Login page")
    public void userIsOnLoginPage() {
        loginPage.isLoaded();
        Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "The login button isn't displayed");
    }
}
