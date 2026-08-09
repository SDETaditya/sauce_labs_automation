package com.aditya.page_objects;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.aditya.abstract_components.AbstractComponents;

public class CartPage extends AbstractComponents {

    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@data-test='inventory-item-name']")
    List<WebElement> cartProducts;

    @FindBy(id = "checkout")
    WebElement checkoutButton;

    public Boolean verifyProductDisplay(String productName) {
        return cartProducts.stream()
                .anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase(productName));
    }

    public CheckoutPage goToCheckout() {
        checkoutButton.click();
        return new CheckoutPage(driver);
    }
}
