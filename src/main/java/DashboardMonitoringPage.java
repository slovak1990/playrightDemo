import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import ru.intelogis.autotests.TestBase;
import ru.yandex.qatools.htmlelements.annotations.Name;

import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.actions;


public class DashboardMonitoringPage extends TestBase {
    /* Общее */
    @Name("Коллекция кнопок в контекстном меню")
    final private String CONTEXT_MENU = "//div[@class='plan-context-menu']//span[contains(normalize-space(.), '%s')]";

    @Name("Кнопка расширения карты")
    final private String MAP_SIZE_BUTTON = "//div[@class='__dbk__gutter Horizontal Dark ils-monitoring-details-gutter-horizontal']";

    @Name("Карта в мониторинге")
    final private String MAP_IN_MONITORING = MAP_SIZE_BUTTON + "/following::div[@class='__dbk__child-wrapper ils-monitoring-map-layout']";

    /* Кнопки на странице */
    @Name("Кнопки с названием")
    final private String DATE_SELECT_BUTTONS_WITH_NAME = "//div[@class='date-select']//button[@type='button']/span[text()='%s']";

    /* Табы - текущая сводка */
    @Name("Контейнер таблицы на странице")
    final private SelenideElement planTableContainer = $(byXpath("//section[@class='ils-table']"));

    @Name("Коллекция строк табов по имени")
    private final String BUTTONS_CLASSNAME = "//div[text()='%s']/ancestor::div[contains(@class, 'ils-monitoring-metrics-tab ')]";

    @Name("Коллекция метрических табов с информацией о точках")
    final private String METRICS_TABS = "//div[text()='%s']/ancestor::div[contains(@class, 'metrics-tab')]/div[@class='header']";

    @Name("Коллекция метрических табов по названию таба")
    final private String METRICS_TAB_BY_NAME = "//div[@class='ils-monitoring-metrics-tabs']//div[contains(text(), '%s')]/parent::div";


    /* Календарь */
    @Name("Ячейка дня в календаре месяца")
    final private String DAY_IN_MONTH_PICKER = "//table[@class='ant-picker-content']//td[@title='%s']";

    @Name("Поле ввода даты выбора проекта по placeholder")
    final private String INPUT_DATE_FIELD_BY_PLACEHOLDER = "//input[contains(@placeholder, '%s')]";

    @Name("Кнопка очистки даты в поле даты в календаре")
    final private SelenideElement pickerClear = $(By.xpath("//span[@class='ant-picker-clear']"));

    /* Тултипы */
    @Name("Элементы статусов доставки по индексу строки в таблице")
    final private String DELIVERY_STATUS_ELEMENT = "//tbody[@class='ant-table-tbody']//tr[not(@aria-hidden='true')][%d]//span[contains(@class,'ils-delivery-status-square')][%d]";

    @Name("Коллекция тултипов")
    final private String DELIVERY_STATUS_ELEMENT_TOOLTIP = "//tbody[@class='ant-table-tbody']//tr[not(@aria-hidden='true')][%d]//span[contains(@class, 'ant-tooltip-open')][%d]";


    /* Календарь */
    @Step("Выбрать начальную {startDate} и конечную дату {finishDate}")
    public void selectStartAndFinishDate(String placeholder, String startDate, String finishDate) throws InterruptedException {
        app.base().clickInputFieldDate(placeholder);
        app.base().selectDate(startDate, false, DAY_IN_MONTH_PICKER, false);
        app.base().selectDate(finishDate, false, DAY_IN_MONTH_PICKER, true);
    }

    @Step("Выбрать начальную дату и конечную")
    public void selectStartAndFinishDate(String placeholder, String secondPlaceholder,String startDate, String finishDate) {
        app.base().clickInputFieldDate(placeholder);
        app.base().enterDateInField(placeholder, startDate);
        actions().sendKeys(Keys.ENTER).perform();
        app.base().enterDateInField(secondPlaceholder, finishDate);
        actions().sendKeys(Keys.ENTER).perform();
    }

    @Step("Выбрать только начальную дату")
    public void selectStartDate(String placeholder, String startDate) throws InterruptedException {
        app.base().clickInputFieldDate(placeholder);
        app.base().selectDate(startDate, false, DAY_IN_MONTH_PICKER, false);
    }

    @Step("Нажать на 'Очистить поле даты")
    public void clickClearDate() {
        app.base().isExistElementWithWait(pickerClear, 5);
        pickerClear.hover().click();
    }

    /* Клик на элементы */
    @Step("Нажать значение {contextMenuValue} в контекстном меню")
    public void clickContextMenuButton(String contextMenuValue) {
        app.base().clickOnElement(app.base().getSelenideElement(CONTEXT_MENU, contextMenuValue));
    }

    @Step("Нажать на метрическую вкладку {tabName}")
    public void clickMetricTab(String tabName) {
        app.base().isExistElementWithWait(app.base().getSelenideElement(METRICS_TAB_BY_NAME, tabName), 5);
        app.base().clickOnElement(app.base().getSelenideElement(METRICS_TAB_BY_NAME, tabName));
    }

    @Step("Нажать на кнопку с именем {buttonName}")
    public void clickButtonByName(String buttonName) {
        app.base().isExistElementWithWait(app.base().getSelenideElement(DATE_SELECT_BUTTONS_WITH_NAME, buttonName), 5);
        app.base().clickOnElement(app.base().getSelenideElement(DATE_SELECT_BUTTONS_WITH_NAME, buttonName));
    }

    @Step("Расширить карту с помощью Actions")
    public void expandMap() {
        SelenideElement element = app.base().getSelenideElement(MAP_SIZE_BUTTON);
        actions()
                .moveToElement(element)
                .clickAndHold(element)
                .moveToLocation(0, -15)
                .release()
                .build();
        actions().perform();
    }

    /* Тултипы */
    @Step("Навести мышку на тултип в таблице по индексу строки и индексу тултипа")
    public void toolTypeHover(int indexRow, int indexTooltip) {
        SelenideElement element = app.base().getSelenideElement(DELIVERY_STATUS_ELEMENT, indexRow, indexTooltip);
        app.base().isExistElementWithWait(element, 5);
        element.hover();
    }

    @Step("Проверить наличие тултипа")
    public Boolean isTooltipExist(int indexRow, int indexTooltip) {
        return app.base().isExistElement(app.base().getSelenideElement(DELIVERY_STATUS_ELEMENT_TOOLTIP, indexRow, indexTooltip));
    }

    /* Получение значений элементов */
    @Step("Получение значения метрической вкладки {tabName} по индексу массива {indexArray}")
    public int getTabElementValue(String generalPointsValue, String tabName, int indexArray) {
        app.base().isExistElementWithWait(planTableContainer, 20);
        String[] str;
        if (tabName.equals(generalPointsValue)) {
            str = app.base().getTextValueOfElement(app.base().getSelenideElement(METRICS_TABS, tabName)).split(" ");
        } else {
            str = app.base().getTextValueOfElement(app.base().getSelenideElement(METRICS_TABS, tabName)).split("\n");
        }
        return Integer.parseInt(str[indexArray]);
    }

    @Step("Получить размер карты")
    public String getMapSize() {
        return app.base().getElementValueOfAttribute(app.base().getSelenideElement(MAP_IN_MONITORING), "style");
    }

    /* Проверка на наличие */
    @Step("Проверка отображения поле ввода даты {placeholder} с ожиданием")
    public Boolean isDateInputExist(String placeholder) {
        return app.base().isExistElementWithWait(app.base().getSelenideElement(INPUT_DATE_FIELD_BY_PLACEHOLDER, placeholder), 10);
    }

    @Step("Проверить выбор элемента (выделение рамкой)")
    public Boolean isElementActive(String buttonName) {
        return app.base().isElementClassNameHaveText(app.base().getSelenideElement(BUTTONS_CLASSNAME, buttonName), "active");
    }
}