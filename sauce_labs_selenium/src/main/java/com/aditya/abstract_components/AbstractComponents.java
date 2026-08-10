package com.aditya.abstract_components;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AbstractComponents {
    protected WebDriver driver;
    protected static Properties properties;
    // Load the properties file once for all child classes
    static {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(System.getProperty("user.dir")
                + "/src/main/java/com/aditya/resources/GlobalData.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public AbstractComponents(WebDriver driver){
        this.driver = driver;
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    public void waitForElementToAppear(By findBy) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
    }
    public void waitForElementToBeClickable(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }
}
