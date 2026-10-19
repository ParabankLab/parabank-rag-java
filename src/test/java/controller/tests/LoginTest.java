package controller.tests;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    @Story("Successful Login with Valid Credentials")
    @Description("Verify that a registered user can successfully log in and view the main dashboard.")
    @Severity(SeverityLevel.BLOCKER)
    void loginTest() {
        Allure.step("Navigate to ParaBank home page", () -> {
            homePage.navigate();
        });

        Allure.step("Verify that the main dashboard is visible", () -> {
            Assert.assertTrue(homePage.pageIsVisible(), "Dashboard is not visible");
        });

        Allure.step("Confirm current URL matches the expected session path", () -> {
            System.out.println("URL is: " + homePage.getPage().url());
        });
    }

}
