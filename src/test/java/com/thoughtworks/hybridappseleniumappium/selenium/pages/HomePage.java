package com.thoughtworks.hybridappseleniumappium.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends com.thoughtworks.hybridappseleniumappium.selenium.pages.AbstractPage {

    @FindBy(xpath = "//*[@id='onetrust-accept-btn-handler']")
    public WebElement buttonAcceptOptionalCookies;

    @FindBy(xpath = "//*[@id='cmp-header__icon__button--open']")
    public WebElement buttonMobileMenu;

    @FindBy(xpath = "//input[@role='combobox']")
    public WebElement inputSearch;

    @FindBy(xpath = "//a[@role='button']")
    public WebElement buttonSearch;

    public HomePage(WebDriver driver) {
        super(driver, "https://www.thoughtworks.com");
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
        return new com.thoughtworks.hybridappseleniumappium.selenium.pages.SearchPage(driver, driver.getCurrentUrl());
    }
    
}
