package Steps.web;

import PageObjects.Web.MainHeader;
import PageObjects.Web.LoginPage;
import PageObjects.Web.StandardObjectPage;
import Setup.TestEnvironment;
import Utils.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class LoginSteps extends TestEnvironment{

    private LoginPage loginPage = new LoginPage().open();
    private MainHeader mainHeader = new MainHeader().open();
    private StandardObjectPage standardObjectPage = new StandardObjectPage().open();


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
        logger.info(String.format("Page url: \"%s\"", pageUrl));
        Utils.gotoURL(pageUrl);
    }

    @Given("User is on Login page")
    public void userIsOnLoginPage() {
        loginPage.isLoaded();
        Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "The login button isn't displayed");
    }

    @Then("User is logged in")
    public void userIsLoggedIn() {
        mainHeader.isLoaded();
        Assert.assertTrue(mainHeader.isUserProfileDisplayed(), "User profile isn't displayed");
    }

    @When("User change the current application to {string}")
    public void userChangeTheCurrentApplicationTo(String appUrl) throws Exception {
        mainHeader.isLoaded();
        String baseUrl = Utils.getConstantValue(EPropertiesNames.BASE_URL);
        appUrl = Utils.parser(appUrl);
        logger.info(String.format("URL: \"%s\"", baseUrl + appUrl));
        Utils.gotoURL(baseUrl + appUrl);
    }

    @Then("Application name will be {string}")
    public void applicationNameWillBe(String appName) {
        mainHeader.isLoaded();
        logger.info(String.format("Aplication name: \"%s\"", mainHeader.getApplicationName()));
        Assert.assertEquals(appName, mainHeader.getApplicationName(),"Application name is empty or incorrect");
    }

    @When("User goes to {string}")
    public void userGoesToObject(String object) throws Exception {
        object = Utils.parser(object);
        String baseUrl = Utils.parser(EPropertiesNames.BASE_URL.name());
        logger.info(String.format("Object url: \"%s\"", baseUrl + object));
        Utils.gotoURL(baseUrl + object);
    }

    @Then("User is on Quote Page")
    public void userIsOnQuotePage() {
        mainHeader.isLoaded();
        Assert.assertTrue(mainHeader.isQuoteObjectTabActivated(), "The quote tab isn't activated");
    }

    @When("User change the list view to {string}")
    public void userChangeTheListViewToAll(String listView) {
        standardObjectPage.isLoaded();
        standardObjectPage.changeListViewTo(listView);
    }
}
