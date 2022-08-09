package PageObjects;

import Setup.TestBuilder;

public class TestPageObject extends Page {

    public TestPageObject(TestBuilder testBuilder){
        init(testBuilder);
    }
    public TestPageObject printTestPageObject(){
        System.out.println("TestPageObject");
        return this;
    }
}
