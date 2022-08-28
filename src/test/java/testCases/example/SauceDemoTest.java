package testCases.example;

import pages.*;
import testCases.TestBuilder;
import utils.PropertiesManager;
import com.google.common.collect.Sets;
import io.qameta.allure.*;
import org.testng.annotations.Test;


public class SauceDemoTest extends TestBuilder {

    @Severity(SeverityLevel.BLOCKER)
    @Epic("Epic test")
    @Description("Test description")
    @Link(name = "Test name", url = "https://test.com")
    @Test(groups = {"regression", "sanity"}, priority = 1, description = "Verify user can complete checkout")
    public void testExample1(){
        initializeTestCase();
        goTo(PropertiesManager.getConfig().BASE_URL());
        sendKeysOn(LoginPage.usernameInput, PropertiesManager.getConfig("USER"));
        sendKeysOn(LoginPage.passwordInput, PropertiesManager.getConfig().PASSWORD());
        clickOn(LoginPage.loginBtn).returnNewInstance(ProductsPage.class)
                .addProductToCart(Sets.newHashSet(0, 2, 1, 4))
                .filterBy("hilo");
        clickOn(Header.cartBtn);
        clickOn(CartPage.checkoutBtn);
        sendKeysOn(CheckoutPage.firstnameInput, "John");
        sendKeysOn(CheckoutPage.lastnameInput, "Doe");
        sendKeysOn(CheckoutPage.postalCodeInput, "89501");
        clickOn(CheckoutPage.continueBtn);
        clickOn(CheckoutPage.finishBtn);
        checkVisibilityOf(CheckoutPage.completionTitle, "Completion title isn't displayed!");
        finishTestCase();
    }
}
