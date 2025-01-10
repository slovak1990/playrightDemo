import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.FileDownloadMode;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static com.codeborne.selenide.Selenide.*;

public class MyAppManager {
  private final Properties properties;
  private final String browser;
  private BaseObject baseObject;
  private NavigationHelper navigationHelper;
  private DateAndTimeHelper dateAndTimeHelper;
  private FileHelper fileHelper;
  private RequestHelper requestHelper;
  private MailHelper mailHelper;

  public MyAppManager(String browser) {
    this.browser = browser;
    properties = new Properties();
  }

  public void init() throws IOException {
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(String.format("src/test/resources/%s.properties", target)));
    Configuration.browser = browser;
    Configuration.timeout = 30000;
    Configuration.browserSize = properties.getProperty("browser.size");
//    Configuration.baseUrl = System.getProperty("baseUrl", properties.getProperty("baseUrl"));
    Configuration.baseUrl = "https://fregat-tms.intelogis.ru/";
    Configuration.proxyEnabled = true;
    Configuration.pageLoadStrategy = "eager";
    Configuration.pageLoadTimeout = 60000;
    Configuration.fileDownload = FileDownloadMode.FOLDER;

    if ("".equals(properties.getProperty("selenoid.server"))) {
      Configuration.reopenBrowserOnFail = true;
      Configuration.savePageSource = false;
    } else {
      Configuration.remote = "http://" + InetAddress.getLocalHost().getHostAddress() + properties.getProperty("selenoid.server");
      DesiredCapabilities capabilities = new DesiredCapabilities();
      Map<String, Object> selenoidOptions = new HashMap<>();
      selenoidOptions.put("enableVNC", true);
      selenoidOptions.put("enableVideo", false);
      selenoidOptions.put("enableLog", true);
      capabilities.setCapability("selenoid:options", selenoidOptions);
      Configuration.browserCapabilities = capabilities;
    }
    baseObject = new BaseObject(this);
    navigationHelper = new NavigationHelper(this);
    dateAndTimeHelper = new DateAndTimeHelper(this);
    fileHelper = new FileHelper(this);
    requestHelper = new RequestHelper(this);
    mailHelper = new MailHelper(this);
    open(Configuration.baseUrl);
  }

  public void stop() {
    if (WebDriverRunner.hasWebDriverStarted()) {
      if ("".equals(properties.getProperty("selenoid.server"))) {
        localStorage().clear();
        clearBrowserCookies();
      }
      closeWebDriver();
    }
  }

  public BaseObject base() {
    return baseObject;
  }

  public NavigationHelper go() {
    return navigationHelper;
  }

  public DateAndTimeHelper date() {
    return dateAndTimeHelper;
  }

  public FileHelper doc() {
    return fileHelper;
  }

  public RequestHelper requestProxy() {
    return this.requestHelper;
  }
  public MailHelper mail() {return this.mailHelper;}
}