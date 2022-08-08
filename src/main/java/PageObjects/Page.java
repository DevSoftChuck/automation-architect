package PageObjects;

import Setup.DriverFactory;
import org.openqa.selenium.support.PageFactory;


public abstract class Page{

    //Inits all the pages


     protected void init(Object object){
        PageFactory.initElements(DriverFactory.getDriver(), object);
    }
}
