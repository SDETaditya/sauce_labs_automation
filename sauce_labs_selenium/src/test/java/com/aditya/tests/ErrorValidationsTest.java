package com.aditya.tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aditya.BaseTest;
import com.aditya.page_objects.CataloguePage;

public class ErrorValidationsTest extends BaseTest {

    @Test
    public void loginErrorValidation() throws IOException, InterruptedException {

        String incorrect_user = loginPage.getProperty("incorrect_user");
        String incorrect_password = loginPage.getProperty("incorrect_password");
        loginPage.loginApplication(incorrect_user, incorrect_password);

        String expectedErrorMessage = "Epic sadface: Username and password do not match any user in this service";
        Assert.assertEquals(loginPage.getErrorMessage(), expectedErrorMessage);
    }

    @Test
    public void catalogueCountValidation() throws IOException, InterruptedException {
        String login_as_standard_user_email = loginPage.getProperty("std_user_email");
        String login_password = loginPage.getProperty("password");
        loginPage.loginApplication(login_as_standard_user_email, login_password);

        CataloguePage cataloguePage = new CataloguePage(driver);
        int productCount = cataloguePage.getProductList().size();
        Assert.assertTrue(productCount > 0);
    }
}
