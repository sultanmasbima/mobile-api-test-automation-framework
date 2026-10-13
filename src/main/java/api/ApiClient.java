package api;

import core.ConfigReader;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

/**
 * Base request settings shared by every API call (base URL, headers, reporting).
 * Same idea as BasePage for UI: one place to change common behaviour.
 */
public abstract class ApiClient {

    protected RequestSpecification baseSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigReader.get("api.base.url"))
                .setContentType(ContentType.JSON)
                // Must be exactly "application/json": Restful Booker answers 418 to RestAssured's
                // default multi-value JSON Accept header
                .setAccept("application/json")
                // Attaches every request & response to the Allure report
                .addFilter(new AllureRestAssured())
                .build();
    }
}
