package controller.tests;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import view.pages.HomePage;
import view.pages.OverviewPage;

public class BaseTest {
    private static ThreadLocal<Playwright> threadPlaywright = new ThreadLocal<>();
    private static ThreadLocal<Browser> threadBrowser = new ThreadLocal<>();
    private static ThreadLocal<BrowserContext> threadContext = new ThreadLocal<>();
    private static ThreadLocal<Page> threadPage = new ThreadLocal<>();

    @BeforeMethod
    public void setUp() {
        if (threadPlaywright.get() == null) {
            Playwright playwright = Playwright.create();
            threadPlaywright.set(playwright);

            boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "true"));
            Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions().setHeadless(isHeadless)
            );
            threadBrowser.set(browser);
        }

        BrowserContext context = threadBrowser.get().newContext(new Browser.NewContextOptions()
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

    // --- SENIOR INHERITANCE PATTERN: Protected Thread-Safe Getters ---
    protected HomePage homePage() {
        return new HomePage(getPage());
    }

    protected OverviewPage overviewPage() {
        return new OverviewPage(getPage());
    }

    @AfterMethod
    public void tearDown() {
        if (threadPage.get() != null) {
            try { threadPage.get().close(); } catch (Exception ignored) {}
            threadPage.remove();
        }
        if (threadContext.get() != null) {
            try { threadContext.get().close(); } catch (Exception ignored) {}
            threadContext.remove();
        }
    }
}