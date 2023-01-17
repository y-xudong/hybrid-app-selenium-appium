package com.thoughtworks.hybridappseleniumappium.appium.pages;

import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends AbstractPage {
    
    @FindBy(xpath = "//XCUIElementTypeButton[@name=\"Accept optional cookies\"]")
    public WebElement buttonAcceptOptionalCookies;

    @FindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Menu\"]")
    public WebElement buttonMobileMenu;

    @FindBy(xpath = "//XCUIElementTypeOther[@name=\"search\"]")
    public WebElement inputSearch;

    @FindBy(xpath = "//XCUIElementTypeButton[@name=\"Search\"]")
    public WebElement buttonSearch;

    public HomePage(IOSDriver driver) {
        super(driver);
    }

    public HomePage acceptOptionalCookies() {
        buttonAcceptOptionalCookies.click();
        return this;
    }
    
    public HomePage tapMobileMenuButton() {
        buttonMobileMenu.click();
        return this;
    }
    
    public SearchPage searchByKeyword(String keyword) {
        inputSearch.sendKeys(keyword);
        buttonSearch.click();
        return new SearchPage(driver);
    }
    
}
