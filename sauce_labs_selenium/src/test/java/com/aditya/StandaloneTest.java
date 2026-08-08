package com.aditya;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class StandaloneTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        String productName = "Grey jacket";
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir")
                + "/sauce_labs_selenium/src/main/java/com/aditya/resources/GlobalData.properties");
        prop.load(fis);
        String login_email = prop.getProperty("email");
        String login_password = prop.getProperty("password");
        driver.get("https://sauce-demo.myshopify.com/");
        driver.findElement(By.id("customer_login_link")).click();
        driver.findElement(By.id("customer_email")).sendKeys(login_email);
        driver.findElement(By.id("customer_password")).sendKeys((login_password));
        driver.findElement(By.xpath("//input[@value='Sign In']")).click();
        driver.findElement(By.xpath("//ul/li/a[text()='Catalog']")).click();
        // driver.findElement(By.xpath("//ul[@id='main-menu']/li/a[text()='Home']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath(String.format(
    "//h3[translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')='%s']",
    productName.toLowerCase()
))).click();
        // driver.findElement(By.id("add")).click();
// WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
// wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(String.format(
//         "//a[starts-with(translate(normalize-space(.), " +
//         "'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '%s')]",
//         "Check Out".toLowerCase()
//     )))));
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

WebElement addToCartButton = driver.findElement(By.id("add"));

// Initial state: enabled → click
wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();

// Wait for the button to become disabled
wait.until(d ->
        !d.findElement(By.id("add")).isEnabled()
);

// Wait for the button to become enabled again
wait.until(d ->
        d.findElement(By.id("add")).isEnabled()
);

// Now the Add to Cart operation is complete
// Go to Checkout
driver.findElement(By.xpath(String.format(
        "//a[starts-with(translate(normalize-space(.), " +
        "'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '%s')]",
        "Check Out".toLowerCase()
))).click();

// Thread.sleep(4000);
// driver.findElement(By.xpath(String.format(
//         "//a[starts-with(translate(normalize-space(.), " +
//         "'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '%s')]",
//         "Check Out".toLowerCase()
//     ))).click();
Boolean isProductInCartPage = driver.findElements(By.cssSelector(".description h3 a"))
        .stream()
        .anyMatch(element ->
                element.getText().toLowerCase().contains(productName.toLowerCase())
        );
        Assert.assertTrue(isProductInCartPage);

        driver.findElement(By.cssSelector("input[id='checkout']")).click();
        // String cartPageProductText = driver.findElement(By.xpath("//div[@class='info']/h3/a")).getText();
        // Assert.assertTrue(cartPageProductText.contains(productName));
        // driver.findElement(By.xpath("//div[@class='actions']/input[@value='Check Out']")).click();
        driver.close();
    }
}
