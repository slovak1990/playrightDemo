package autotests.tests.mainpage;

import autotests.appmanager.BaseObject;
import autotests.pages.MainPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Epic("Главная страница")
@Feature("Календарь")
@Story("Проверка работы календаря на странице")
public class CalendarOnPage extends BaseObject {

    MainPage mainPage = new MainPage();
    String openDate = "//span[@class='time']";
    String today;
    String dateValue;

    @Test
    @Description("Тест проверяет отображение в календаре текущей даты")
    public void calendarTest() {
        today = mainPage.calendarDate();
        page.locator(today).click();
        dateValue = page.locator(openDate).textContent();
        today = mainPage.changeDateFormatValue();
        assertThat("Текущая дата не отображается!", dateValue.equals(today), equalTo(true));
    }
}
