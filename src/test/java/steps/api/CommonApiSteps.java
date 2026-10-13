package steps.api;

import context.ScenarioContext;
import io.cucumber.java.en.Then;
import io.restassured.module.jsv.JsonSchemaValidator;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;

/**
 * Generic response checks reusable by any API feature.
 */
public class CommonApiSteps {

    private final ScenarioContext context;

    public CommonApiSteps(ScenarioContext context) {
        this.context = context;
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatusCode) {
        assertEquals(context.getLastResponse().statusCode(), expectedStatusCode,
                "Unexpected status code. Body: " + context.getLastResponse().asString());
    }

    @Then("the response should match the {string} JSON schema")
    public void theResponseShouldMatchTheJsonSchema(String schemaName) {
        context.getLastResponse().then()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/" + schemaName + ".json"));
    }

    @Then("the response field {string} should be {string}")
    public void theResponseFieldShouldBe(String field, String expectedValue) {
        assertEquals(context.getLastResponse().jsonPath().getString(field), expectedValue);
    }

    @Then("the response field {string} should not be empty")
    public void theResponseFieldShouldNotBeEmpty(String field) {
        String value = context.getLastResponse().jsonPath().getString(field);
        assertFalse(value == null || value.isBlank(), "Field '" + field + "' is empty");
    }

    @Then("the response field {string} should not exist")
    public void theResponseFieldShouldNotExist(String field) {
        assertNull(context.getLastResponse().jsonPath().get(field), "Field '" + field + "' should not exist");
    }
}
