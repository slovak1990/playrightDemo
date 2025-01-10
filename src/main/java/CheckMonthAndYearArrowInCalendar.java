import org.testng.annotations.Test;
import ru.intelogis.autotests.TestBase;
import ru.intelogis.autotests.models.UserData;
import ru.intelogis.autotests.tests.PropertiesRelease;
import ru.intelogis.autotests.ui.panels.SidePanel;
import ru.intelogis.autotests.ui.steps.LoginSteps;
import ru.intelogis.autotests.ui.steps.SettingsSteps;
import ru.intelogis.autotests.ui.steps.monitoring.DashboardMonitoringSteps;

import java.text.ParseException;

import static org.hamcrest.MatcherAssert.assertThat;

@Owner("Илья Шабакаев")
@Epic("Мониторинг")
@Feature("Календарь в разделе 'Текущая сводка'")
@Story("Проверка работы переключателей в календаре раздел 'Текущая сводка'")
public class CheckMonthAndYearArrowInCalendar extends TestBase {
    LoginSteps loginSteps = new LoginSteps();
    String login = PropertiesRelease.getInstance().getLoginUser2();
    String password = PropertiesRelease.getInstance().getPasswordUser2();
    SidePanel sidePanel = new SidePanel();
    DashboardMonitoringSteps dashboardMonitoringSteps = new DashboardMonitoringSteps();
    SettingsSteps settingsSteps = new SettingsSteps();

    String addressDetails = "/monitoring/details";
    String titleMonitoring = "Мониторинг: Текущая сводка";
    String placeholderStartDate = "Начальная дата";
    String placeholderEndDate = "Конечная дата";
    String startDateInPast = "2017-03-31";
    String endDateInPast = "2017-04-02";
    String startDateInFuture = "2027-09-11";
    String endDateInFuture = "2027-09-12";
    String infoMessage = "Данные загружены";
    String titleSettings = "Настройки";
    String infoText = "Настройки сохранены";

    @Test()
    @TmsLink(value = "TMS-3046")
    @Severity(value = SeverityLevel.CRITICAL)
    @Description(value = "Тест проверяет работу переключателей месяца и года в календаре в разделе 'Текущая сводка'")
    public void testCheckMonthAndYearArrowInCalendar() throws InterruptedException, ParseException {
        loginSteps.login(new UserData().withLogin(login).withPassword(password));
        sidePanel.clickSettingsButton();
        assertThat("Тайтл не содержит ожидаемый текст: " + titleSettings, app.base().titleHaveText(titleSettings));

        settingsSteps.changeConfigsInSettingsPage("Мониторинг",
                "Максимальный размер выгрузки данных", "3");
        assertThat("Всплывающее окно не появилось" + infoText, app.base().infoWindowHaveText(infoText));

        sidePanel.clickSideMenuByAddress(addressDetails);
        assertThat("Тайтл не содержит ожидаемый текст", sidePanel.isTitleExist());

        dashboardMonitoringSteps.selectStartAndFinishDate(placeholderStartDate, startDateInPast, endDateInPast);
        assertThat("В поле начальной даты не отображается выбранная дата!", app.base().isDateSelect(placeholderStartDate,
                app.date().convertDateInPattern("yyyy-MM-dd","dd.MM.yyyy",startDateInPast)));
        assertThat("В поле конечной даты не отображается выбранная дата!", app.base().isDateSelect(placeholderEndDate,
                app.date().convertDateInPattern("yyyy-MM-dd","dd.MM.yyyy",endDateInPast)));
        assertThat("Информационное окно " + infoMessage + " не отображается", app.base().infoWindowHaveText(infoMessage));

        sidePanel.clickSideMenuByAddress("/catalog");
        sidePanel.clickMonitoringButton();
        dashboardMonitoringSteps.clickClearDate();
        dashboardMonitoringSteps.selectStartAndFinishDate(placeholderStartDate, startDateInFuture, endDateInFuture);

        assertThat("В поле начальной даты не отображается выбранная дата!", app.base().isDateSelect(placeholderStartDate,
                app.date().convertDateInPattern("yyyy-MM-dd","dd.MM.yyyy",startDateInFuture)));
        assertThat("В поле конечной даты не отображается выбранная дата!", app.base().isDateSelect(placeholderEndDate,
                app.date().convertDateInPattern("yyyy-MM-dd","dd.MM.yyyy",endDateInFuture)));
        assertThat("Информационное окно " + infoMessage + " не отображается", app.base().infoWindowHaveText(infoMessage));
    }
}
