package com.aditya.tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aditya.BaseTest;
import com.aditya.abstract_components.AbstractComponents;
import com.aditya.page_objects.CataloguePage;
import com.aditya.test_components.Retry;

public class ErrorValidationsTest extends BaseTest {

    @Test(groups = {"ErrorValidation"}, retryAnalyzer = Retry.class)
    public void loginErrorValidation() throws IOException, InterruptedException {

        String incorrect_user = AbstractComponents.getProperty("incorrect_user");
        String incorrect_password = AbstractComponents.getProperty("incorrect_password");
        loginPage.get().loginApplication(incorrect_user, incorrect_password);

        String expectedErrorMessage = "Epic sadface: Username and password do not match any user in this service";
        Assert.assertEquals(loginPage.get().getErrorMessage(), expectedErrorMessage);
    }

    @Test()
    public void catalogueCountValidation() throws IOException, InterruptedException {
        String login_as_standard_user_email = AbstractComponents.getProperty("std_user_email");
        String login_password = AbstractComponents.getProperty("password");
        loginPage.get().loginApplication(login_as_standard_user_email, login_password);

        CataloguePage cataloguePage = new CataloguePage(driver.get());
        int productCount = cataloguePage.getProductList().size();
        Assert.assertTrue(productCount > 0);
    }
}
