package com.aditya.step_definition;

import java.io.IOException;
import org.testng.Assert;

import com.aditya.BaseTest;
import com.aditya.abstract_components.AbstractComponents;
import com.aditya.page_objects.CartPage;
import com.aditya.page_objects.CataloguePage;
import com.aditya.page_objects.CheckoutPage;
import com.aditya.page_objects.ConfirmationPage;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class StepDefinitionImplementation extends BaseTest {

    private CataloguePage cataloguePage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private ConfirmationPage confirmationPage;

    @Given("I landed on ecommerce page")
    public void i_landed_on_ecommerce_page() throws IOException {
        launchApplication();
    }

    @Given("Logged in with username key {string} and password key {string}")
    public void logged_in_with_username_key_and_password_key(String nameKey, String passwordKey) {
        String username = AbstractComponents.getProperty(nameKey);
        String password = AbstractComponents.getProperty(passwordKey);
        loginPage.get().loginApplication(username, password);
    }

    @When("I add product to cart")
    public void i_add_product_to_cart() {
        String productName = "Sauce Labs Fleece Jacket";
        cataloguePage = new CataloguePage(driver.get());
        cataloguePage.addProductToCart(productName);
        cartPage = cataloguePage.goToCart();
    }

    @When("Checkout and submit order")
    public void checkout_and_submit_order() {
        String productName = "Sauce Labs Fleece Jacket";
        Boolean isExpectedItemInCart = cartPage.verifyProductDisplay(productName);
        Assert.assertTrue(isExpectedItemInCart);
        checkoutPage = cartPage.goToCheckout();
        checkoutPage.enterCheckoutInformation("Aditya", "Pathak", "474020");
        confirmationPage = checkoutPage.clickFinish();
    }

    @Then("{string} message is displayed")
    public void message_is_displayed(String expectedMessage) {
        String message = confirmationPage.getConfirmationMessage();
        Assert.assertTrue(message.equalsIgnoreCase(expectedMessage));
    }

    @After
    public void closeBrowser() {
        tearDown();
    }
}
