package testCases.example;

import PageObjects.*;
import Setup.TestBuilder;
import Utils.PropertiesManager;
import com.google.common.collect.Sets;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import testCases.BaseTestCase;


public class TestCase extends BaseTestCase {

    @Severity(SeverityLevel.BLOCKER)
    @Epic("Epic test")
    @Description("Test description")
    @Link(name = "Test name", url = "https://test.com")
    @Test(groups = {"Regression"}, priority = 1, description = "Test description")
    public void testExample1(){
        new TestBuilder()
                .goTo(PropertiesManager.getConfig().BASE_URL())
                .sendKeysOn(LoginPage.usernameInput, PropertiesManager.getConfig("USER"))
                .sendKeysOn(LoginPage.passwordInput, PropertiesManager.getConfig().PASSWORD())
                .clickOn(LoginPage.loginBtn)
                .newInstance(ProductsPage.class)
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
    @Test(groups = {"Regression"}, priority = 1, description = "Test description")
    public void testExample2(){
        new TestBuilder()
                .goTo(PropertiesManager.getConfig().BASE_URL())
                .sendKeysOn(LoginPage.usernameInput, PropertiesManager.getConfig("USER"))
                .sendKeysOn(LoginPage.passwordInput, PropertiesManager.getConfig().PASSWORD())
                .clickOn(LoginPage.loginBtn)
                .newInstance(ProductsPage.class)
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
    @Test(groups = {"Regression"}, priority = 1, description = "Test description")
    public void testExample3(){
        new TestBuilder()
                .goTo(PropertiesManager.getConfig().BASE_URL())
                .sendKeysOn(LoginPage.usernameInput, PropertiesManager.getConfig("USER"))
                .sendKeysOn(LoginPage.passwordInput, PropertiesManager.getConfig().PASSWORD())
                .clickOn(LoginPage.loginBtn)
                .newInstance(ProductsPage.class)
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
    @Test(groups = {"Regression"}, priority = 1, description = "Test description")
    public void testExample4(){
        new TestBuilder()
                .goTo(PropertiesManager.getConfig().BASE_URL())
                .sendKeysOn(LoginPage.usernameInput, PropertiesManager.getConfig("USER"))
                .sendKeysOn(LoginPage.passwordInput, PropertiesManager.getConfig().PASSWORD())
                .clickOn(LoginPage.loginBtn)
                .newInstance(ProductsPage.class)
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
    @Test(groups = {"Regression"}, priority = 1, description = "Test description")
    public void testExample5(){
        new TestBuilder()
                .goTo(PropertiesManager.getConfig().BASE_URL())
                .sendKeysOn(LoginPage.usernameInput, PropertiesManager.getConfig("USER"))
                .sendKeysOn(LoginPage.passwordInput, PropertiesManager.getConfig().PASSWORD())
                .clickOn(LoginPage.loginBtn)
                .newInstance(ProductsPage.class)
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
    @Test(groups = {"Regression"}, priority = 1, description = "Test description")
    public void testExample(){
        new TestBuilder()
                .goTo(PropertiesManager.getConfig().BASE_URL())
                .sendKeysOn(LoginPage.usernameInput, PropertiesManager.getConfig("USER"))
                .sendKeysOn(LoginPage.passwordInput, PropertiesManager.getConfig().PASSWORD())
                .clickOn(LoginPage.loginBtn)
                .newInstance(ProductsPage.class)
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
}
