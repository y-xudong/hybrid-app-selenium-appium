package com.thoughtworks.hybridappseleniumappium.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchPage extends com.thoughtworks.hybridappseleniumappium.selenium.pages.AbstractPage {

    @FindBy(xpath = "//div[contains(@class, 'aem-responsive-grid')]/ul[1]/li[1]")
    public WebElement firstSearchResult;

    public SearchPage(WebDriver driver, String url) {
        super(driver, url);
    }

    public SearchPage(WebDriver driver) {
        super(driver, "https://www.thoughtworks.com/search?q=keyword");
    }

    public WebElement getFirstSearchResult() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                ExpectedConditions.visibilityOf(firstSearchResult)
        );
        return firstSearchResult;
    }
}
