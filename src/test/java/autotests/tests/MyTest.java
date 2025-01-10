package autotests.tests;

import autotests.appmanager.BaseObject;
import autotests.pages.MainPage;
import com.microsoft.playwright.*;

public class MyTest extends BaseObject {
    String text = "Новости";
    String locator = "//div[@class='breadcrumbs']";
    private static final String DATE_VALUE = "//span[@class='time']";

    public static void main(String[] args) {
        MainPage mainPage = new MainPage();
        Browser browser;
        Page page;
        BrowserContext context;
        String BASE_URL = "https://uralhockey.ru/";
        browser = Playwright
                .create()
                .chromium()
                .launch(new BrowserType.LaunchOptions().setHeadless(false));
        context = browser.newContext(new Browser.NewContextOptions().setScreenSize(1920, 1080));
        page = context.newPage();
        page.navigate(BASE_URL);


        int i = 0;
    }
}
