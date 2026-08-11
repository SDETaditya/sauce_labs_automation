package com.aditya.tests;

import java.io.IOException;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aditya.BaseTest;
import com.aditya.abstract_components.AbstractComponents;
import com.aditya.page_objects.CartPage;
import com.aditya.page_objects.CataloguePage;
import com.aditya.page_objects.CheckoutPage;
import com.aditya.page_objects.ConfirmationPage;

public class SubmitOrderTest extends BaseTest {
    String standard_user_email = AbstractComponents.getProperty("std_user_email");
    String login_password = AbstractComponents.getProperty("password");
    String another_user = AbstractComponents.getProperty("visual_user");

    @Test(dataProvider = "getData", groups = "Regression")
    public void submitOrder(HashMap<String, String> input) throws IOException, InterruptedException {
        
        String productName = "Sauce Labs Fleece Jacket";

        loginPage.get().loginApplication(input.get("username"), input.get("password"));

        CataloguePage cataloguePage = new CataloguePage(driver.get());
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

    // @DataProvider
    // public Object[][] getData() {
    //     return new Object[][] { { standard_user_email, login_password }, { another_user, login_password } };
    // }
    @DataProvider(parallel = true)
    public Object[][] getData() {
        HashMap<String, String> data = new HashMap<>();
        data.put("username", standard_user_email);
        data.put("password", login_password);

        HashMap<String, String> data1 = new HashMap<>();
        data1.put("username", another_user);
        data1.put("password", login_password);
        return new Object [] [] {{data},{data1}};
    }
}
