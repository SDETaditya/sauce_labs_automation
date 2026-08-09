package com.aditya.page_objects;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.aditya.abstract_components.AbstractComponents;

public class CataloguePage extends AbstractComponents {

    public CataloguePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@data-test='inventory-item']")
    List<WebElement> products;

    @FindBy(xpath = "//a[@data-test='shopping-cart-link']")
    WebElement shoppingCart;

    By productByNameLocator = By.xpath(".//div[@data-test='inventory-item-name']");
    By addToCartLocator = By.xpath(".//button[text()='Add to cart']");

    public List<WebElement> getProductList() {
        return products;
    }

    public WebElement getProductByName(String productName) {
        return getProductList().stream()
                .filter(product -> product.findElement(productByNameLocator)
                        .getText().equals(productName))
                .findFirst().orElse(null);
    }

    public void addProductToCart(String productName) {
        WebElement product = getProductByName(productName);
        if (product != null) {
            product.findElement(addToCartLocator).click();
        } else {
            throw new RuntimeException("Product '" + productName + "' not found in catalogue.");
        }
    }

    public CartPage goToCart() {
        waitForElementToBeClickable(shoppingCart);
        shoppingCart.click();
        return new CartPage(driver);
    }
}
