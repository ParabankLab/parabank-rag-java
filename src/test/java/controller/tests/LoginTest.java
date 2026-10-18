package controller.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
    @Test
    void loginTest() {
        homePage.navigate();

        Assert.assertTrue(homePage.pageIsVisible(),"Dashboard is not visible");

       // homePage.clickOnTimeLink();
        //Assert.assertTrue(timeSheetPage.pageIsVisible(),"Timesheet is not visible");


        //System.out.println("testing... " + homePage.getHeadingText());
       // System.out.println("User: " + homePage.getLoggedInUser());

        System.out.println("URL is: " + homePage.getPage().url());
    }

}
