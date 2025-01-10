package autotests.appmanager;

import com.microsoft.playwright.*;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class BaseObject {
    protected Browser browser;
    protected Page page;
    protected BrowserContext context;
    private Boolean isTraceEnabled = false;
    final static protected String BASE_URL = "https://uralhockey.ru/";

    @BeforeClass
    public void setUp() {
        // инициализация браузера
        browser = Playwright
                .create()
                .chromium()
                .launch(new BrowserType.LaunchOptions().setHeadless(false));

        // создаем контекст для браузера
        context = browser.newContext(new Browser.NewContextOptions().setScreenSize(1920, 1080));

        // трейсинг замедляет скорость заполнения полей
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(false));

        isTraceEnabled = false;

        // создаем новую страницу
        page = context.newPage();
        page.navigate(BASE_URL);
    }

    @AfterClass
    @Step("Закрыть браузер")
    public void tearDown() {
        if (browser != null) {
            browser.close();
            browser = null;
        }
    }

    // Добавляет вложения к упавшему тесту. Скриншот, исходный код страницы, трейсинг
    @AfterMethod
    public void attachFilesToFailedTest(ITestResult result) throws IOException {
        if (!result.isSuccess()) {
            String uuid = UUID.randomUUID().toString();
            byte[] screenshot = page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get("build/allure-results/screenshot_" + uuid + "screenshot.png"))
                    .setFullPage(true));

            Allure.addAttachment(uuid, new ByteArrayInputStream(screenshot));
            Allure.addAttachment("source.html", "text/html", page.content());

            if (isTraceEnabled) {
                String traceFileName = String.format("build/%s_trace.zip", uuid);
                Path tracePath = Paths.get(traceFileName);
                context.tracing()
                        .stop(new Tracing.StopOptions()
                                .setPath(tracePath));
                Allure.addAttachment("trace.zip", new ByteArrayInputStream(Files.readAllBytes(tracePath)));
            }
        }
    }
}
