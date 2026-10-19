package controller.tests;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import view.pages.HomePage;
import view.pages.OverviewPage;

public class BaseTest {
    // ThreadLocal storage ensuring complete isolation per thread
    private static ThreadLocal<Playwright> threadPlaywright = new ThreadLocal<>();
    private static ThreadLocal<Browser> threadBrowser = new ThreadLocal<>();
    private static ThreadLocal<BrowserContext> threadContext = new ThreadLocal<>();
    private static ThreadLocal<Page> threadPage = new ThreadLocal<>();


    @BeforeMethod
    public void setUp() {
        // 1. Create a brand-new Playwright instance strictly for this thread
        Playwright playwright = Playwright.create();
        threadPlaywright.set(playwright);

        // 2. Launch browser on this thread
        boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "true"));
        Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(isHeadless)
        );
        threadBrowser.set(browser);

        // 3. Create a fresh, isolated context and page for the test method
        BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                .setIgnoreHTTPSErrors(true)
        );
        threadContext.set(context);

        Page page = context.newPage();
        threadPage.set(page);

        }

    public static Page getPage() {
        return threadPage.get();
    }

    @AfterMethod
    public void tearDown() {
        // Safely close and cleanup thread-local resources in reverse order
        if (threadPage.get() != null) {
            try { threadPage.get().close(); } catch (Exception ignored) {}
            threadPage.remove();
        }
        if (threadContext.get() != null) {
            try { threadContext.get().close(); } catch (Exception ignored) {}
            threadContext.remove();
        }
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