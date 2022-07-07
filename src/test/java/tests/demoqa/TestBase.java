package tests.demoqa;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.CredentialsConfig;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class TestBase {
    private static final CredentialsConfig configs = ConfigFactory.create(CredentialsConfig.class);

    @BeforeAll
    static void setUp() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

//        Configuration.baseUrl = "https://astrio.ru";
//        Configuration.browserSize = "1920x1080";
//        Configuration.remote = "https://user1:1234@selenoid.autotests.cloud/wd/hub";
        //Configuration.browserSize = System.getProperty("browserSize", configs.browserSize());

        Configuration.baseUrl = System.getProperty("base_url");
        Configuration.browser = System.getProperty("browser", "chrome");
        //-Dbase_url=${BASE_URL} первое это переменная в коде а второе переменная в дженкинсе
        Configuration.browserSize = System.getProperty("browsersize");
        Configuration.remote = "https://"+ configs.selenoidLogin() + ":" + configs.selenoidPass() + "@" + System.getProperty("selenoid_server");
      DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;
        Configuration.pageLoadTimeout=40000;
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
        closeWebDriver();
    }


}
