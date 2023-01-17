package com.thoughtworks.hybridappseleniumappium.selenium;

import com.thoughtworks.hybridappseleniumappium.selenium.pages.HomePage;
import com.thoughtworks.hybridappseleniumappium.selenium.pages.SearchPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class SearchTest {
    private ChromeDriver driver;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().setSize(Dimensions.IPHONE_XR);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void should_see_technology_radar_when_searching_technology_radar() {
        HomePage homePage = new HomePage(driver);
        homePage.open();
        homePage.acceptOptionalCookies();
        homePage.tapMobileMenuButton();
        SearchPage searchPage = homePage.searchByKeyword("Technology Radar");
        assertThat(searchPage.getFirstSearchResult().getText(), containsString("Technology Radar"));
    }
}
