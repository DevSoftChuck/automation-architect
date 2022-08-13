package pages.sauce;

import setup.TestBuilder;
import utils.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Set;

public class ProductsPage extends Header{

    public static By filterDropDown = By.xpath("//select[@data-test='product_sort_container']");
    public static By addToCartBtn = By.xpath("//button[text()='Add to cart']");

    public ProductsPage(TestBuilder testBuilder) {
        super(testBuilder);
    }

    public ProductsPage addProductToCart(Set<Integer> list){
        List<WebElement> addToCartBtnList = SeleniumUtils.findElements(addToCartBtn);

        for (int itemNumber : list){
            if (itemNumber < addToCartBtnList.size()){
                addToCartBtnList.get(itemNumber).click();
            }
        }
        return this;
    }

    public ProductsPage filterBy(String value){
        SeleniumUtils.waitForElementToBeClickable(filterDropDown);
        SeleniumUtils.selectFromDropdownByValue(value, filterDropDown);
        return this;
    }
}
