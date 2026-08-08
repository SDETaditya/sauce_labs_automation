package com.aditya.page_objects;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    WebDriver driver;
    Properties properties;

    public LoginPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
        
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(System.getProperty("user.dir")
                + "/sauce_labs_selenium/src/main/java/com/aditya/resources/GlobalData.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
// driver.findElement(By.id("user-name")).sendKeys(login_as_standard_user_email);
//         driver.findElement(By.id("password")).sendKeys((login_password));
//         driver.findElement(By.id("login-button")).click();

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
