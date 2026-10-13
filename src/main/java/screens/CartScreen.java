package screens;

import core.BaseScreen;
import org.openqa.selenium.By;

import java.util.List;

/**
 * Screen Object: "My Cart" screen.
 */
public class CartScreen extends BaseScreen {

    private final By itemNames = id("titleTV");
    private final By itemCount = id("itemsTV");
    private final By totalPrice = id("totalPriceTV");
    private final By checkoutButton = id("cartBt");

    public List<String> getItemNames() {
        return getTexts(itemNames);
    }

    /** e.g. "2 Items" */
    public String getItemCountText() {
        return getText(itemCount);
    }

    /** e.g. "$ 59.98" */
    public String getTotalPrice() {
        return getText(totalPrice);
    }

    public void proceedToCheckout() {
        tap(checkoutButton);
    }
}
