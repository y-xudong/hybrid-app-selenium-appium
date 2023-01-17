package com.thoughtworks.hybridappseleniumappium.appium;

import com.thoughtworks.hybridappseleniumappium.appium.pages.HomePage;
import com.thoughtworks.hybridappseleniumappium.appium.pages.SearchPage;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.junit.jupiter.api.*;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.nio.file.Paths;
import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class IosTest {

    private static AppiumDriverLocalService appiumDriverLocalService;
    private IOSDriver driver;
    private final String appPath = Paths.get(System.getProperty("user.dir"),
                    "/src/test/java/com/thoughtworks/hybridappseleniumappium/appium/Thoughtworks Web.app")
            .toAbsolutePath().toString();

    @BeforeAll
    static void beforeAll() {
        appiumDriverLocalService = new AppiumServiceBuilder().withArgument(() -> "--base-path", "/wd/hub").build();
        appiumDriverLocalService.start();
    }

    @AfterAll
    static void afterAll() {
        appiumDriverLocalService.stop();
    }

    @BeforeEach
    void setUp() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "16.2");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 14 Pro");
        capabilities.setCapability(MobileCapabilityType.APP, appPath);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
        driver = new IOSDriver(appiumDriverLocalService.getUrl(), capabilities);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    public void should_run_the_same_test_as_selenium() {
        HomePage homePage = new HomePage(driver);
        homePage.acceptOptionalCookies();
        homePage.tapMobileMenuButton();
        SearchPage searchPage = homePage.searchByKeyword("Technology Radar");
        searchPage.clickSearchTitle();
        assertThat(searchPage.getFirstSearchTitle().getText(), containsString("Technology Radar"));
    }
}
