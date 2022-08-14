package pages;

import setup.TestBuilder;
import org.openqa.selenium.By;

public class CartPage extends Header{

    public static By removeBtn = By.xpath("//button[text()='Remove']");
    public static By checkoutBtn = By.xpath("//button[text()='Checkout']");
    public static By cartTitle = By.xpath("//span[text()='Your Cart']");

    public CartPage(TestBuilder testBuilder) {
        super(testBuilder);
    }
}
