package steps.mobile;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import screens.CatalogScreen;

import java.util.Comparator;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CatalogSteps {

    @Given("I open the app on the catalog screen")
    public void iOpenTheAppOnTheCatalogScreen() {
        // The app is launched by MobileHooks; this step waits until the catalog is ready
        assertEquals(new CatalogScreen().getScreenTitle(), "Products");
    }

    @Then("I should see the {string} screen")
    public void iShouldSeeTheScreen(String title) {
        assertEquals(new CatalogScreen().getScreenTitle(), title);
    }

    @Then("the catalog should show {string}")
    public void theCatalogShouldShow(String productName) {
        List<String> names = new CatalogScreen().getVisibleProductNames();
        assertTrue(names.contains(productName), productName + " not found in " + names);
    }

    @When("I sort the catalog by {string}")
    public void iSortTheCatalogBy(String option) {
        new CatalogScreen().sortBy(option);
    }

    @Then("the visible product prices should be sorted in ascending order")
    public void theVisibleProductPricesShouldBeSortedInAscendingOrder() {
        List<Double> actual = new CatalogScreen().getVisibleProductPrices();
        assertEquals(actual, actual.stream().sorted().toList(), "Prices are not sorted ascending");
    }

    @Then("the visible product names should be sorted in descending order")
    public void theVisibleProductNamesShouldBeSortedInDescendingOrder() {
        List<String> actual = new CatalogScreen().getVisibleProductNames();
        assertEquals(actual, actual.stream().sorted(Comparator.reverseOrder()).toList(), "Names are not sorted Z to A");
    }
}
