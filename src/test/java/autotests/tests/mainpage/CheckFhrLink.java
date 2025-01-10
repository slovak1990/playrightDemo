package autotests.tests.mainpage;

import autotests.appmanager.BaseObject;
import autotests.pages.MainPage;
import com.microsoft.playwright.Page;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

@Epic("Главная страница")
@Feature("Ссылки в хэдэре")
@Story("Проверка работы ссылок")
public class CheckFhrLink extends BaseObject {

    MainPage mainPage = new MainPage();
    String linkName = "fhr";
    String fhrUrl = "https://fhr.ru/";

    @Test
    @Description(value = "Тест проверяет работу перехода по ссылке 'ФХР' в хэдере страницы")
    public void fhrLinkTest() {
        Page popUp = page.waitForPopup(() -> {
            page.locator(mainPage.linksLocator(linkName)).click();
        });
        assertEquals(fhrUrl, popUp.url());
    }
}
