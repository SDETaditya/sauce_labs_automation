package com.aditya;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.aditya.abstract_components.AbstractComponents;
import com.aditya.page_objects.LoginPage;

public class BaseTest {
    public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public static ThreadLocal<LoginPage> loginPage = new ThreadLocal<>();

    public WebDriver initializeDriver() throws IOException {
        String browserNameFromPropertiesFile = AbstractComponents.getProperty("browser");
        String browserNameFromMaven = System.getProperty("browser");
        String browserName = browserNameFromMaven != null ? browserNameFromMaven : browserNameFromPropertiesFile;

        WebDriver localDriver = null;
        if (browserName.contains("chrome")) {

            ChromeOptions options = new ChromeOptions();
            Map<String, Object> prefs = new HashMap<>();
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            prefs.put("profile.password_manager_leak_detection", false);
            options.setExperimentalOption("prefs", prefs);
            if(browserName.contains("headless")){
            options.addArguments("headless");
            }
            localDriver = new ChromeDriver(options);
        } else if (browserName.equalsIgnoreCase("firefox")) {

            FirefoxOptions options = new FirefoxOptions();

            options.addPreference("signon.rememberSignons", false);
            options.addPreference("signon.autofillForms", false);
            options.addPreference("signon.generation.enabled", false);

            localDriver = new FirefoxDriver(options);
        }

        localDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        localDriver.manage().window().maximize();
        return localDriver;
    }

    @BeforeMethod(alwaysRun = true)
    public LoginPage launchApplication() throws IOException {
        WebDriver localDriver = initializeDriver();
        driver.set(localDriver);
        LoginPage localLoginPage = new LoginPage(localDriver);
        loginPage.set(localLoginPage);
        localLoginPage.goTo();
        return localLoginPage;
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver.get() != null) {
            driver.get().quit();
        }
        driver.remove();
        loginPage.remove();
    }

    public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        File file = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
        FileUtils.copyFile(source, file);
        return System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";
    }
}
