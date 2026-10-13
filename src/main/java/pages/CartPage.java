package pages;

import core.BasePage;
import org.openqa.selenium.By;

import java.util.List;

/**
 * Page Object: Shopping cart page.
 */
public class CartPage extends BasePage {

    private final By itemNames = By.cssSelector("[data-test='inventory-item-name']");
    private final By checkoutButton = By.cssSelector("[data-test='checkout']");

    public List<String> getItemNames() {
        return getTexts(itemNames);
    }

    public void checkout() {
        click(checkoutButton);
    }
}
