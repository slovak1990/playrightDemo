package autotests.tests.mainpage;

import autotests.appmanager.BaseObject;
import autotests.pages.MainPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

@Epic("Главная страница")
@Feature("Новости")
@Story("Проверка отображения новостей")
public class OpenNews extends BaseObject {

    MainPage mainPage = new MainPage();

    @Test
    @Description("Тест проверяет, что по клику на новость, отображаются корректные информация и дата")
    public void calendarTest() {

    }
}
