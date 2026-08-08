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

public class StandaloneTestCopy {

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
        
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir")
                + "/sauce_labs_selenium/src/main/java/com/aditya/resources/GlobalData.properties");
        prop.load(fis);
        String login_as_standard_user_email = prop.getProperty("std_user_email");
        String login_password = prop.getProperty("password");
        
        driver.get("https://www.saucedemo.com/");

        driver.findElement(By.id("user-name")).sendKeys(login_as_standard_user_email);
        driver.findElement(By.id("password")).sendKeys((login_password));
        driver.findElement(By.id("login-button")).click();

        List<WebElement> products = driver.findElements(By.xpath("//div[@data-test='inventory-item']"));
        WebElement productElement = products.stream()
                .filter(product -> product.findElement(By.xpath(".//div[@data-test='inventory-item-name']"))
                        .getText().equals(productName))
                .findFirst().orElse(null);
        productElement.findElement(By.xpath(".//button[text()='Add to cart']")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions
                .elementToBeClickable(driver.findElement(By.xpath("//a[@data-test='shopping-cart-link']"))));
        driver.findElement(By.xpath("//a[@data-test='shopping-cart-link']")).click();
        List<WebElement> cartProducts = driver.findElements(By.xpath("//div[@data-test='inventory-item-name']"));

        Boolean isExpectedItemInCart = cartProducts.stream()
                .anyMatch(cartProduct -> cartProduct.getText().equals(productName));
        Assert.assertTrue(isExpectedItemInCart);
        driver.findElement(By.id("checkout")).click();
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
