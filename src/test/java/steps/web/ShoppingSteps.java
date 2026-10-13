package steps.web;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.CartPage;
import pages.CheckoutPage;
import pages.InventoryPage;

import java.util.Comparator;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class ShoppingSteps {

    @When("I add {string} to the cart")
    public void iAddToTheCart(String productName) {
        new InventoryPage().addProductToCart(productName);
    }

    @When("I add the following products to the cart:")
    public void iAddTheFollowingProductsToTheCart(List<String> productNames) {
        InventoryPage inventoryPage = new InventoryPage();
        productNames.forEach(inventoryPage::addProductToCart);
    }

    @When("I sort products by {string}")
    public void iSortProductsBy(String option) {
        new InventoryPage().sortBy(option);
    }

    @When("I proceed to checkout")
    public void iProceedToCheckout() {
        new InventoryPage().openCart();
        new CartPage().checkout();
    }

    @When("I fill in checkout information {string} {string} {string}")
    public void iFillInCheckoutInformation(String firstName, String lastName, String postalCode) {
        new CheckoutPage().fillInformation(firstName, lastName, postalCode);
    }

    @When("I finish the order")
    public void iFinishTheOrder() {
        new CheckoutPage().finish();
    }

    @Then("the cart badge should show {int}")
    public void theCartBadgeShouldShow(int expectedCount) {
        assertEquals(new InventoryPage().getCartBadgeCount(), expectedCount);
    }

    @Then("the cart should contain:")
    public void theCartShouldContain(List<String> expectedProducts) {
        new InventoryPage().openCart();
        assertEquals(new CartPage().getItemNames(), expectedProducts);
    }

    @Then("the product prices should be sorted in ascending order")
    public void theProductPricesShouldBeSortedInAscendingOrder() {
        List<Double> actual = new InventoryPage().getProductPrices();
        List<Double> expected = actual.stream().sorted().toList();
        assertEquals(actual, expected, "Prices are not sorted ascending");
    }

    @Then("the product names should be sorted in descending order")
    public void theProductNamesShouldBeSortedInDescendingOrder() {
        List<String> actual = new InventoryPage().getProductNames();
        List<String> expected = actual.stream().sorted(Comparator.reverseOrder()).toList();
        assertEquals(actual, expected, "Names are not sorted Z to A");
    }

    @Then("I should see the order confirmation {string}")
    public void iShouldSeeTheOrderConfirmation(String expectedMessage) {
        assertEquals(new CheckoutPage().getCompleteHeader(), expectedMessage);
    }

    @Then("I should see the checkout error {string}")
    public void iShouldSeeTheCheckoutError(String expectedError) {
        assertEquals(new CheckoutPage().getErrorMessage(), expectedError);
    }
}
