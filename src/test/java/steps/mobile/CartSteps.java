package steps.mobile;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import screens.CartScreen;
import screens.CatalogScreen;
import screens.ProductDetailScreen;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CartSteps {

    @Given("I have {string} in my mobile cart")
    public void iHaveInMyMobileCart(String productName) {
        iOpenTheProduct(productName);
        iAddItemsToTheCart(1);
        iOpenTheMobileCart();
    }

    @When("I open the product {string}")
    public void iOpenTheProduct(String productName) {
        new CatalogScreen().openProduct(productName);
        assertEquals(new ProductDetailScreen().getProductName(), productName);
    }

    @When("I add {int} item(s) to the cart")
    public void iAddItemsToTheCart(int quantity) {
        ProductDetailScreen detailScreen = new ProductDetailScreen();
        detailScreen.setQuantity(quantity);
        detailScreen.addToCart();
    }

    @When("I open the mobile cart")
    public void iOpenTheMobileCart() {
        new CatalogScreen().openCart();
    }

    @Then("the mobile cart badge should show {int}")
    public void theMobileCartBadgeShouldShow(int expectedCount) {
        assertEquals(new CatalogScreen().getCartBadgeCount(), expectedCount);
    }

    @Then("the mobile cart should contain {string}")
    public void theMobileCartShouldContain(String productName) {
        assertTrue(new CartScreen().getItemNames().contains(productName), productName + " is not in the cart");
    }

    @Then("the mobile cart should show {string} with total {string}")
    public void theMobileCartShouldShowWithTotal(String itemCount, String totalPrice) {
        CartScreen cartScreen = new CartScreen();
        assertEquals(cartScreen.getItemCountText(), itemCount);
        assertEquals(cartScreen.getTotalPrice(), totalPrice);
    }
}
