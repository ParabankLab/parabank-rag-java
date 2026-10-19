package controller.tests;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import view.pages.HomePage;
import view.pages.OverviewPage;

public class BaseTest {
    protected Playwright playwright;
    protected Browser browser;
    protected Page page;
    protected BrowserContext context;

    protected HomePage homePage;
    protected OverviewPage overViewPage;

    @BeforeMethod
    public void setUp() throws Exception{
        playwright = Playwright.create();
        boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "true"));
        // Inside your BaseTest.java setUp() method:
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false) // Watch it live
        );

        context = browser.newContext(new Browser.NewContextOptions()
                .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                .setIgnoreHTTPSErrors(true)
        );
        page = browser.newPage();

        //Page objects
        homePage = new HomePage(page);
        overViewPage = new OverviewPage(page);
    }

    @AfterMethod
    public void tearDown() throws Exception{
        if (page != null){
            page.close();
        }
        if(browser != null){
            browser.close();
        }
        if(playwright != null){
            playwright.close();
        }
    }
}

