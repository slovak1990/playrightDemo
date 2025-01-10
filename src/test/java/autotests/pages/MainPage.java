package autotests.pages;

import autotests.appmanager.BaseObject;
import autotests.appmanager.GlobalHelper;
import io.qameta.allure.Step;
import ru.yandex.qatools.htmlelements.annotations.Name;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;

public class MainPage extends BaseObject {
    private GlobalHelper helper;

    // локаторы
    @Name("Ссылки в правом углу")
    private final String LINKS = "//img[@src='/images/links/%s.png']";

    @Name("Месяц в календаре")
    private final String MONTH = "//div[@data-day='%s']";

    // получение локатора
    @Step("Получение локатора")
    public String getLocatorElement(String locator, Object... value) {
        return new Formatter().format(locator, value).toString();
    }

    // ссылки локаторов
    @Step("Получение ссылки локатора")
    public String linksLocator(String nameUrl) {
        return getLocatorElement(LINKS, nameUrl);
    }

    @Step("Получение текущей даты")
    public String calendarDate() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String today = formatter.format(date);
        return getLocatorElement(MONTH, today);
    }

    @Step("Получение значения текущей даты")
    public String changeDateFormatValue() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        return formatter.format(date);
    }

}
