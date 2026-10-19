package controller.tests;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import view.pages.HomePage;
import view.pages.OverviewPage;

public class BaseTest {
    // ThreadLocal instances for process-level isolation
    private static ThreadLocal<Playwright> threadPlaywright = new ThreadLocal<>();
    private static ThreadLocal<Browser> threadBrowser = new ThreadLocal<>();

    // ThreadLocal for per-test context and page isolation
    private static ThreadLocal<BrowserContext> threadContext = new ThreadLocal<>();
    private static ThreadLocal<Page> threadPage = new ThreadLocal<>();

    protected HomePage homePage;
    protected OverviewPage overViewPage;

    @BeforeClass
    public void setUpClass() {
        // Launch Playwright and Browser ONCE per thread/class
        Playwright playwright = Playwright.create();
        threadPlaywright.set(playwright);

        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        threadBrowser.set(browser);
    }

    @BeforeMethod
    public void setUp() {
        // 1. Ensure Playwright exists for this thread
        if (threadPlaywright.get() == null) {
            threadPlaywright.set(Playwright.create());
        }

        // 2. Ensure Browser exists for this thread (fallback safeguard)
        if (threadBrowser.get() == null) {
            threadBrowser.set(threadPlaywright.get().chromium().launch(
                    new BrowserType.LaunchOptions().setHeadless(false)
            ));
        }

        Browser browser = threadBrowser.get();

        // 3. Create fresh context and page for this specific test method
        BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                .setIgnoreHTTPSErrors(true)
        );
        threadContext.set(context);

        Page page = context.newPage();
        threadPage.set(page);

        // 4. Initialize Page Objects
        homePage = new HomePage(page);
        overViewPage = new OverviewPage(page);
    }
    public static Page getPage() {
        return threadPage.get();
    }

    @AfterMethod
    public void tearDownMethod() {
        // Clean up only the context and page after each test method
        if (threadContext.get() != null) {
            try { threadContext.get().close(); } catch (Exception ignored) {}
            threadContext.remove();
        }
        if (threadPage.get() != null) {
            threadPage.remove();
        }
    }

    @AfterClass
    public void tearDownClass() {
        // Close browser and playwright when the thread finishes
        if (threadBrowser.get() != null) {
            try { threadBrowser.get().close(); } catch (Exception ignored) {}
            threadBrowser.remove();
        }
        if (threadPlaywright.get() != null) {
            try { threadPlaywright.get().close(); } catch (Exception ignored) {}
            threadPlaywright.remove();
        }
    }
}