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

    // Synchronization lock to prevent CI driver extraction race conditions
    private static final Object initializationLock = new Object();

    @BeforeMethod
    public void setUp() {
        if (threadPlaywright.get() == null) {
            synchronized (initializationLock) {
                // Double-checked locking to ensure safe, serialized driver extraction on CI
                if (threadPlaywright.get() == null) {
                    Playwright playwright = Playwright.create();
                    threadPlaywright.set(playwright);

                    boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "true"));
                    Browser browser = playwright.chromium().launch(
                            new BrowserType.LaunchOptions()
                                    .setHeadless(isHeadless)
                                    .setArgs(java.util.List.of(
                                            "--no-sandbox",
                                            "--disable-setuid-sandbox",
                                            "--disable-dev-shm-usage"
                                    ))
                    );
                    threadBrowser.set(browser);
                }
            }
        }

        // Fresh context and page per test method for absolute state isolation
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