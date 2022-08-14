package testCases.example;

import pages.*;
import setup.TestBuilder;
import utils.PropertiesManager;
import com.google.common.collect.Sets;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import testCases.BaseTestCase;


public class SauceDemoTest extends BaseTestCase {

    @Severity(SeverityLevel.BLOCKER)
    @Epic("Epic test")
    @Description("Test description")
    @Link(name = "Test name", url = "https://test.com")
    @Test(groups = {"regression"}, priority = 1, description = "Verify user can complete checkout")
    public void testExample1(){
        new TestBuilder()
                .goTo(PropertiesManager.getConfig().BASE_URL())
                .sendKeysOn(LoginPage.usernameInput, PropertiesManager.getConfig("USER"))
                .sendKeysOn(LoginPage.passwordInput, PropertiesManager.getConfig().PASSWORD())
                .clickOn(LoginPage.loginBtn)
                    .returnNewInstance(ProductsPage.class)
                    .addProductToCart(Sets.newHashSet(0, 2, 1, 4))
                    .filterBy("hilo")
                    .switchBackToTestBuilder()
                .clickOn(Header.cartBtn)
                .clickOn(CartPage.checkoutBtn)
                .sendKeysOn(CheckoutPage.firstnameInput, "John")
                .sendKeysOn(CheckoutPage.lastnameInput, "Doe")
                .sendKeysOn(CheckoutPage.postalCodeInput, "89501")
                .clickOn(CheckoutPage.continueBtn)
                .clickOn(CheckoutPage.finishBtn)
                .checkVisibilityOf(CheckoutPage.completionTitle, "Completion title isn't displayed!")
                .finish();
    }

    @Severity(SeverityLevel.BLOCKER)
    @Epic("Epic test")
    @Description("Test description")
    @Link(name = "Test name", url = "https://test.com")
    @Test(groups = {"regression"}, priority = 1, description = "Verify user can reach to cart page")
    public void testExample2(){
        new TestBuilder()
                .goTo(PropertiesManager.getConfig().BASE_URL())
                .sendKeysOn(LoginPage.usernameInput, PropertiesManager.getConfig("USER"))
                .sendKeysOn(LoginPage.passwordInput, PropertiesManager.getConfig().PASSWORD())
                .clickOn(LoginPage.loginBtn)
                    .returnNewInstance(ProductsPage.class)
                    .addProductToCart(Sets.newHashSet(1, 3))
                    .filterBy("hilo")
                    .switchBackToTestBuilder()
                .clickOn(Header.cartBtn)
                .clickOn(CartPage.checkoutBtn)
                .finish();
    }

    @Severity(SeverityLevel.BLOCKER)
    @Epic("Epic test")
    @Description("Test description")
    @Link(name = "Test name", url = "https://test.com")
    @Test(groups = {"regression"}, priority = 1, description = "Verify products can be added to cart")
    public void testExample3(){
        new TestBuilder()
                .goTo(PropertiesManager.getConfig().BASE_URL())
                .sendKeysOn(LoginPage.usernameInput, PropertiesManager.getConfig("USER"))
                .sendKeysOn(LoginPage.passwordInput, PropertiesManager.getConfig().PASSWORD())
                .clickOn(LoginPage.loginBtn)
                .returnNewInstance(ProductsPage.class)
                    .addProductToCart(Sets.newHashSet(0, 1, 2))
                    .filterBy("hilo")
                    .switchBackToTestBuilder()
                .finish();
    }
}
