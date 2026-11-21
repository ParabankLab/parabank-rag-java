package tests;

import com.microsoft.playwright.*;
import com.playwright.framework.pages.AdminPage;
import com.playwright.framework.pages.TransferPage;
import io.qameta.allure.internal.shadowed.jackson.databind.JsonNode;
//import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeAll;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import com.playwright.framework.pages.HomePage;
import com.playwright.framework.pages.OverviewPage;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static com.playwright.framework.util.ParaBankUtils.*;

public class BaseTest {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;
    protected JsonNode node = readJsonTree("src/test/resources/data/testData.json");


    @BeforeSuite
    public static void beforeSuite() {
        Path authPath = Paths.get("playwright/.auth/auth.json");

        try {
            Files.createDirectories(authPath.getParent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (Playwright pw = Playwright.create();
             Browser browser = pw.chromium().launch()) {
            BrowserContext authContext = browser.newContext();
            Page loginPage = authContext.newPage();

            loginPage.navigate(BASE_URL);
            loginPage.locator("input[name='username']").fill("john");
            loginPage.locator("input[name='password']").fill("demo");
            loginPage.locator("input[value='Log In']").click();

            assertThat(loginPage.locator("#accountTable")).isVisible();
            loginPage.locator("a[href*='logout.htm']").waitFor();

            authContext.storageState(new BrowserContext.StorageStateOptions().setPath(authPath));

            AdminPage adminPage = new AdminPage(loginPage);
            adminPage.navigate();
            adminPage.cleanAndInitDB();
        }

    }

    @BeforeMethod
    public void setUp() {
        playwright = Playwright.create();
        boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "true"));

        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(isHeadless)
        );

        context = browser.newContext(
                new Browser.NewContextOptions()
                        .setStorageStatePath(Paths.get("playwright/.auth/auth.json"))
        );
        page = context.newPage();

    }

    public Page getPage() {
        return page;
    }

    protected HomePage homePage() {
        return new HomePage(getPage());
    }

    protected OverviewPage overviewPage() {
        return new OverviewPage(getPage());
    }

    protected AdminPage adminPage() {
        return new AdminPage(getPage());
    }

    protected TransferPage transferPage() {
        return new TransferPage(getPage());
    }


    @AfterMethod
    public void tearDown() {
        if (page != null) {
            try { page.close(); } catch (Exception ignored) {}
        }
        if (context != null) {
            try { context.close(); } catch (Exception ignored) {}
        }
        if (browser != null) {
            try { browser.close(); } catch (Exception ignored) {}
        }
        if (playwright != null) {
            try { playwright.close(); } catch (Exception ignored) {}
        }
    }
}