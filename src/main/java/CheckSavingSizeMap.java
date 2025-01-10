import org.testng.annotations.Test;
import ru.intelogis.autotests.TestBase;
import ru.intelogis.autotests.models.UserData;
import ru.intelogis.autotests.ui.panels.SidePanel;
import ru.intelogis.autotests.ui.steps.LoginSteps;
import ru.intelogis.autotests.ui.steps.monitoring.DashboardMonitoringSteps;

import static com.codeborne.selenide.Selenide.sleep;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Owner("Тимур Мугинов")
@Epic("Мониторинг")
@Feature("Карта")
@Story("Проверка сохранения размеров карты")
public class CheckSavingSizeMap extends TestBase {
    LoginSteps loginSteps = new LoginSteps();
//    String login = PropertiesRelease.getInstance().getLoginUser2();
//    String password = PropertiesRelease.getInstance().getPasswordUser2();
    String login = "admin@chefonov.ru";
    String password = "123";
    SidePanel sidePanel = new SidePanel();
    DashboardMonitoringSteps dashboardMonitoringSteps = new DashboardMonitoringSteps();

    String addressDetails = "/monitoring/details";
    String titleMonitoring = "Детализация событий";
    String sizeMap;

    @Test()
    @TmsLink(value = "TMS-9439")
    @Severity(value = SeverityLevel.CRITICAL)
    @Description(value = "Тест проверяет сохранение пользовательского состояния размеров карты")
    public void testCheckSavingSizeMap() {
        loginSteps.login(new UserData().withLogin(login).withPassword(password));
        sidePanel.clickSideMenuByAddress(addressDetails);
        //assertThat("Тайтл не содержит ожидаемый текст " + titleMonitoring, sidePanel.isTitleExist());

        app.requestProxy().on();
        sizeMap = dashboardMonitoringSteps.getMapSize();
        sleep(3000);
        dashboardMonitoringSteps.expandMap();
        assertThat("Карта не увеличилась!", !dashboardMonitoringSteps.getMapSize().equals(sizeMap), equalTo(true));

        sizeMap = dashboardMonitoringSteps.getMapSize();
        app.requestProxy().waitForRequest("Monitoring/getMetrics", 7);
        assertThat("Размеры карты поменялись!", dashboardMonitoringSteps.getMapSize().equals(sizeMap), equalTo(true));
    }
}