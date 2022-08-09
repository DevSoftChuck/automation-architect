package PageObjects;

import Setup.TestBuilder;

public abstract class Page{
    private TestBuilder testBuilder;

    //Inits all the pages, use this method if you prefer
    protected void init(TestBuilder testBuilder){
        this.testBuilder = testBuilder;
    }

    /*  This method allows us to recover control of our TestBuilder instance, in case we have used the newInstance
       method above and created another object to call a method that does not return the TestBuilder instance. */
    public TestBuilder switchBackToTestBuilder(){
        return this.testBuilder;
    }
}
