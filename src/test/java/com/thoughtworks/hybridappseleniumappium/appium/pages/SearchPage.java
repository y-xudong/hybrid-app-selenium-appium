package com.thoughtworks.hybridappseleniumappium.appium.pages;

import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchPage extends AbstractPage {

    //TODO: EXTREMELY HIGHLY RISKY XPATH HERE!
    @FindBy(xpath = "//XCUIElementTypeOther[@name=\"main\"]/XCUIElementTypeOther[3]/XCUIElementTypeOther[1]/XCUIElementTypeLink")
    public WebElement firstSearchTitle;

    @FindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Search\"]")
    public WebElement searchTitle;

    public SearchPage(IOSDriver driver) {
        super(driver);
    }

    public WebElement getFirstSearchTitle() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                ExpectedConditions.visibilityOf(firstSearchTitle)
        );
        return firstSearchTitle;
    }

    public void clickSearchTitle() {
        searchTitle.click();
    }
}
