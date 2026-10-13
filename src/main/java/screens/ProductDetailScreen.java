package screens;

import core.BaseScreen;
import org.openqa.selenium.By;

/**
 * Screen Object: Product detail screen.
 */
public class ProductDetailScreen extends BaseScreen {

    private final By productName = id("productTV");
    private final By increaseQuantityButton = id("plusIV");
    private final By quantity = id("noTV");
    private final By addToCartButton = id("cartBt");

    public String getProductName() {
        return getText(productName);
    }

    /** Quantity starts at 1, so the "+" button is tapped (quantity - 1) times. */
    public void setQuantity(int targetQuantity) {
        for (int current = getQuantity(); current < targetQuantity; current++) {
            tap(increaseQuantityButton);
        }
    }

    public int getQuantity() {
        return Integer.parseInt(getText(quantity));
    }

    public void addToCart() {
        tap(addToCartButton);
    }
}
