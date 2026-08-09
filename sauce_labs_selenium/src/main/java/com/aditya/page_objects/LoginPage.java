package com.aditya.page_objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.aditya.abstract_components.AbstractComponents;

public class LoginPage extends AbstractComponents {

    public LoginPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "user-name")
    WebElement standard_user_name;

    @FindBy(id = "password")
    WebElement passwordElement;

    @FindBy(id = "login-button")
    WebElement login_button;

    public void loginApplication(String username, String password){
        standard_user_name.sendKeys(username);
        passwordElement.sendKeys(password);
        login_button.click();
    }

    public void goTo(){
        String url = properties.getProperty("url");
        driver.get(url);
    }
    
}
