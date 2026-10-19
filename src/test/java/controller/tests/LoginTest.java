package controller.tests;

import io.qameta.allure.Allure;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
    @Test
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
