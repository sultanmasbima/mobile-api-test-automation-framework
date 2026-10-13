package screens;

import core.BaseScreen;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

import java.util.List;

/**
 * Screen Object: Product catalog (home screen) of Sauce Labs "My Demo App" for Android.
 * Locators were taken from the app's resource-ids (Appium Inspector / page source).
 */
public class CatalogScreen extends BaseScreen {

    private final By screenTitle = id("productTV");
    private final By productNames = id("titleTV");
    private final By productPrices = id("priceTV");
    private final By sortButton = id("sortIV");
    private final By menuButton = id("menuIV");
    private final By menuList = id("menuRV");
    private final By cartButton = id("cartRL");
    private final By cartBadge = id("cartTV");

    public String getScreenTitle() {
        return getText(screenTitle);
    }

    public List<String> getVisibleProductNames() {
        return getTexts(productNames);
    }

    /** Prices are shown as "$ 29.99". */
    public List<Double> getVisibleProductPrices() {
        return getTexts(productPrices).stream()
                .map(price -> Double.parseDouble(price.replace("$", "").trim()))
                .toList();
    }

    /** The product title is not clickable, its image is: tap the image inside the same card. */
    public void openProduct(String productName) {
        scrollToText(productName);
        tap(AppiumBy.xpath("//*[@resource-id='" + packageId("titleTV") + "' and @text='" + productName + "']"
                + "/../*[@content-desc='Product Image']"));
    }

    /**
     * @param option text shown in the sort sheet, e.g. "Price - Ascending", "Name - Descending"
     */
    public void sortBy(String option) {
        tap(sortButton);
        tap(AppiumBy.androidUIAutomator("new UiSelector().text(\"" + option + "\")"));
    }

    public void openMenuItem(String menuItem) {
        tap(menuButton);
        waitVisible(menuList); // drawer slides in with an animation
        tap(AppiumBy.androidUIAutomator("new UiSelector().text(\"" + menuItem + "\")"));
    }

    public void openCart() {
        tap(cartButton);
    }

    public int getCartBadgeCount() {
        return isPresent(cartBadge) ? Integer.parseInt(getText(cartBadge)) : 0;
    }
}
