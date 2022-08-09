package testCases.example;

import PageObjects.TestPageObject;
import Setup.TestBuilder;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import testCases.BaseTestCase;

public class TestExample extends BaseTestCase {

    @Severity(SeverityLevel.BLOCKER)
    @Epic("Epic test")
    @Description("Test description")
    @Link(name = "Test name", url = "https://test.com")
    @Test(groups = {"Regression"}, priority = 1, description = "Test description")
    public void testExample(){
        SoftAssert softAssert = new SoftAssert();
        new TestBuilder(softAssert)
                .goTo("https://google.com")
                .newInstance(TestPageObject.class)
                    .printTestPageObject()
                    .switchBackToTestBuilder()
                .goTo("https://facebook.com");
    }
}
