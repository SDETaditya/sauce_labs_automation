package com.aditya.page_objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.aditya.abstract_components.AbstractComponents;

public class ConfirmationPage extends AbstractComponents {

    public ConfirmationPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//h2[@data-test='complete-header']")
    WebElement completeHeader;

    public String getConfirmationMessage() {
        return completeHeader.getText();
    }
}
