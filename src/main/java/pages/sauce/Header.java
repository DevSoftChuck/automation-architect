package pages.sauce;

import setup.TestBuilder;
import org.openqa.selenium.By;

public class Header extends Page{

    public static By menuBtn = By.xpath("//button[@id='react-burger-menu-btn']");
    public static By cartBtn = By.xpath("//a[@class='shopping_cart_link']");

    public Header(TestBuilder testBuilder){
        init(testBuilder);
    }
}
