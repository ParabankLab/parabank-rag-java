package controller.tests;

import io.qameta.allure.*;
import io.qameta.allure.internal.shadowed.jackson.databind.JsonNode;
import org.testng.Assert;
import org.testng.annotations.Test;
import view.pages.HomePage;
import view.pages.OverviewPage;

import static controller.util.ParaBankUtils.readJsonTree;

public class LoginTest extends BaseTest {
    private  JsonNode node = readJsonTree("src/test/resources/data/testData.json");



    @Test
    @Story("Successful Login with Valid Credentials")
    @Description("Verify that a user can navigate to ParaBank home page.")
    @Severity(SeverityLevel.BLOCKER)
    void navigateToHomePageTest() {
        HomePage homePage = new HomePage(getPage());
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

    @Test
    @Story("Successful Login with Valid Credentials")
    @Description("Verify that a registered user can successfully log in.")
    @Severity(SeverityLevel.BLOCKER)
    void successfulLoginTest() {
        HomePage homePage = new HomePage(getPage());
        OverviewPage overViewPage = new OverviewPage(getPage());
        Allure.step("Navigate to ParaBank home page", () -> {
            homePage.navigate();
        });

        Allure.step("Login to Parabank with valid credentials", () -> {
            homePage.login(node.get("user").get(0).get("name").asText(), node.get("user").get(0).get("password").asText());
        });

        Allure.step("Confirm current URL matches the expected session path", () -> {
            Assert.assertTrue(overViewPage.pageIsVisible(), "Overview page is not visible");
        });
    }

}
