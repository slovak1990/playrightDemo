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
public class CheckVhlLink extends BaseObject {
    MainPage mainPage = new MainPage();
    String linkName = "vhl";
    String vhlUrl = "https://vhlru.ru/";

    @Test
    @Description(value = "Тест проверяет работу перехода по ссылке 'ВХЛ' в хэдере страницы")
    public void vhlLinkTest() {
        Page popUp = page.waitForPopup(() -> {
            page.locator(mainPage.linksLocator(linkName)).click();
        });
        assertEquals(vhlUrl, popUp.url());
    }
}
