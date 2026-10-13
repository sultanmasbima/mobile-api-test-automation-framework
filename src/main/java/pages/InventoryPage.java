package pages;

import core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * Page Object: Products (inventory) page, shown after a successful login.
 */
public class InventoryPage extends BasePage {

    private final By pageTitle = By.cssSelector("[data-test='title']");
    private final By itemNames = By.cssSelector("[data-test='inventory-item-name']");
    private final By itemPrices = By.cssSelector("[data-test='inventory-item-price']");
    private final By sortDropdown = By.cssSelector("[data-test='product-sort-container']");
    private final By cartBadge = By.cssSelector("[data-test='shopping-cart-badge']");
    private final By cartLink = By.cssSelector("[data-test='shopping-cart-link']");

    public String getPageTitle() {
        return getText(pageTitle);
    }

    /**
     * Product buttons follow the pattern add-to-cart-sauce-labs-backpack,
     * so the locator is built from the product name.
     */
    public void addProductToCart(String productName) {
        click(By.cssSelector("[data-test='add-to-cart-" + toSlug(productName) + "']"));
    }

    public int getCartBadgeCount() {
        return isPresent(cartBadge) ? Integer.parseInt(getText(cartBadge)) : 0;
    }

    public void openCart() {
        click(cartLink);
    }

    /**
     * @param option visible text, e.g. "Price (low to high)", "Name (Z to A)"
     */
    public void sortBy(String option) {
        new Select(waitVisible(sortDropdown)).selectByVisibleText(option);
    }

    public List<String> getProductNames() {
        return getTexts(itemNames);
    }

    public List<Double> getProductPrices() {
        return getTexts(itemPrices).stream()
                .map(price -> Double.parseDouble(price.replace("$", "")))
                .toList();
    }

    private String toSlug(String productName) {
        return productName.toLowerCase().replaceAll("[^a-z0-9.()]+", "-");
    }
}
