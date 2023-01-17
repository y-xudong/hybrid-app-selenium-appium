package com.thoughtworks.hybridappseleniumappium.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public abstract class AbstractPage {

    protected WebDriver driver;
    private final String url;

    public AbstractPage( WebDriver driver, String url) {
        this.driver = driver;
        this.url = url;
        PageFactory.initElements(driver, this);
    }

    public AbstractPage open() {
        driver.navigate().to(url);
        return this;
    }
}
