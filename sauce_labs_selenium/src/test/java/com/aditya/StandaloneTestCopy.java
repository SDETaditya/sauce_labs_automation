package com.aditya;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aditya.abstract_components.AbstractComponents;
import com.aditya.page_objects.LoginPage;
import com.aditya.page_objects.CataloguePage;
import com.aditya.page_objects.CartPage;

public class StandaloneTestCopy{

public static void main(String[] args) throws IOException, InterruptedException {
        String productName = "Sauce Labs Fleece Jacket";
        
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);
        options.setExperimentalOption("prefs", prefs);
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        
        
        LoginPage loginPage = new LoginPage(driver);
        String login_as_standard_user_email = loginPage.getProperty("std_user_email");
        String login_password = loginPage.getProperty("password");
        loginPage.goTo();
        loginPage.loginApplication(login_as_standard_user_email, login_password);



        CataloguePage cataloguePage = new CataloguePage(driver);
        cataloguePage.addProductToCart(productName);
        CartPage cartPage = cataloguePage.goToCart();

        Boolean isExpectedItemInCart = cartPage.verifyProductDisplay(productName);
        Assert.assertTrue(isExpectedItemInCart);
        cartPage.goToCheckout();

        
        driver.findElement(By.id("first-name")).sendKeys("Aditya");
        driver.findElement(By.id("last-name")).sendKeys("Pathak");
        driver.findElement(By.id("postal-code")).sendKeys("474020");
        driver.findElement(By.id("continue")).click();
        driver.findElement(By.id("finish")).click();

        
        String message = driver.findElement(By.xpath("//h2[data-test='complete-header']")).getText();
        Assert.assertTrue(message.equalsIgnoreCase("Thank you for your order!"));
        driver.close();
    }

}
