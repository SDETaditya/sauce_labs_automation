package com.aditya.tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aditya.BaseTest;
import com.aditya.abstract_components.AbstractComponents;
import com.aditya.page_objects.CartPage;
import com.aditya.page_objects.CataloguePage;
import com.aditya.page_objects.CheckoutPage;
import com.aditya.page_objects.ConfirmationPage;

public class StandaloneTestCopy extends BaseTest {
String login_as_standard_user_email = AbstractComponents.getProperty("std_user_email");
String login_password = AbstractComponents.getProperty("password");
String another_user = AbstractComponents.getProperty("visual_user");
    @Test(dataProvider = "getData", groups = "Regression")
    public void submitOrder(String username, String password) throws IOException, InterruptedException {
        String productName = "Sauce Labs Fleece Jacket";

        
        loginPage.loginApplication(username, password);

        CataloguePage cataloguePage = new CataloguePage(driver);
        cataloguePage.addProductToCart(productName);
        CartPage cartPage = cataloguePage.goToCart();

        Boolean isExpectedItemInCart = cartPage.verifyProductDisplay(productName);
        Assert.assertTrue(isExpectedItemInCart);
        CheckoutPage checkoutPage = cartPage.goToCheckout();
        checkoutPage.enterCheckoutInformation("Aditya", "Pathak", "474020");
        ConfirmationPage confirmationPage = checkoutPage.clickFinish();
        String message = confirmationPage.getConfirmationMessage();
        Assert.assertTrue(message.equalsIgnoreCase("Thank you for your order!"));
    }

    @DataProvider
    public Object[][] getData(){
        return new Object [][]{{login_as_standard_user_email, login_password},{another_user, login_password}};
    }


}
