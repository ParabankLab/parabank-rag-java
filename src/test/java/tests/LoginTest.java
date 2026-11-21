package tests;

import com.microsoft.playwright.*;
import com.playwright.framework.pages.HomePage;
import com.playwright.framework.pages.OverviewPage;
import io.qameta.allure.*;
import io.qameta.allure.internal.shadowed.jackson.databind.JsonNode;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static com.playwright.framework.util.ParaBankUtils.readJsonTree;

public class LoginTest{
    private JsonNode node = readJsonTree("src/test/resources/data/testData.json");
    HomePage homePage;
    OverviewPage overviewPage;

    @BeforeTest
    private void setUp() {
        Playwright playwright = Playwright.create();
        boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "true"));

        Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(isHeadless)
        );

        BrowserContext context = browser.newContext();

        Page page = context.newPage();
        homePage = new HomePage(page);
        overviewPage = new OverviewPage(page);
    }


    @Test
    @Story("Successful Login with Valid Credentials")
    @Description("Verify that a user can navigate to ParaBank home page.")
    @Severity(SeverityLevel.BLOCKER)
    void navigateToHomePageTest() {
        Allure.step("TC-POS-01 - Navigate to ParaBank home page", () -> {
            homePage.navigate();
        });

        Allure.step("TC-POS-02 - Verify that the main dashboard is visible", () -> {
            Assert.assertTrue(homePage.pageIsVisible(), "Dashboard is not visible");
        });

        Allure.step("TC-POS-03A - Confirm current URL matches is the start page", () -> {
            System.out.println("URL is: " + homePage.getPage().url());
        });
    }

    @Test
    @Story("Successful Login with Valid Credentials")
    @Description("Verify that a registered user can successfully log in.")
    @Severity(SeverityLevel.BLOCKER)
    void successfulLoginTest() {
        Allure.step("TC-POS-01 - Navigate to ParaBank home page", () -> {
            homePage.navigate();
        });

        Allure.step("TC-POS-03 - Login to Parabank with valid credentials", () -> {
            homePage.login(node.get("user").get(0).get("name").asText(), node.get("user").get(0).get("password").asText());
        });

        Allure.step("TC-POS-04 - Confirm current URL is the overview.htm", () -> {
            Assert.assertTrue(overviewPage.pageIsVisible(), "Overview page is not visible");
        });
    }

}
