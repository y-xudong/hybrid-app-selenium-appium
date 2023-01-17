package com.thoughtworks.hybridappseleniumappium.appium.pages;

import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.support.PageFactory;

public abstract class AbstractPage {

    protected IOSDriver driver;

    public AbstractPage(IOSDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
