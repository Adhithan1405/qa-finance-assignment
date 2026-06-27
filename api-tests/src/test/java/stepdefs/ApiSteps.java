package stepdefs;

import io.cucumber.java.en.*;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import utils.SpecBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * ApiSteps — Cucumber step definitions for API feature scenarios.
 */
public class ApiSteps {

    private Response response;
    private Map<String, Object> requestBody = new HashMap<>();

    // ── Given ────────────────────────────────────────────────────────────────

    @Given("I have the following user payload:")
    public void buildUserPayload(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        if (!rows.isEmpty()) {
            requestBody.putAll(rows.get(0));
        }
    }

    @Given("I send a login request with email {string} and no password")
    public void loginWithoutPassword(String email) {
        Map<String, String> body = new HashMap<>();
        body.put("email", email);
        // password deliberately omitted to trigger 400

        response = given()
                .spec(SpecBuilder.getRequestSpec())
                .body(body)
                .when()
                .post("/login");
    }

    // ── When ─────────────────────────────────────────────────────────────────

    @When("I send a GET request to {string}")
    public void sendGetRequest(String endpoint) {
        response = given()
                .spec(SpecBuilder.getRequestSpec())
                .when()
                .get(endpoint);
    }

    @When("I send a POST request to {string}")
    public void sendPostRequest(String endpoint) {
        response = given()
                .spec(SpecBuilder.getRequestSpec())
                .body(requestBody)
                .when()
                .post(endpoint);
    }

    // ── Then ─────────────────────────────────────────────────────────────────

    @Then("the response status code should be {int}")
    public void verifyStatusCode(int expectedStatus) {
        Assertions.assertEquals(
                expectedStatus,
                response.getStatusCode(),
                "Status code mismatch. Response body: " + response.getBody().asString()
        );
    }

    @Then("the response body should contain field {string} with value {string}")
    public void verifyFieldValue(String jsonPath, String expectedValue) {
        Object actualValue = response.jsonPath().get(jsonPath);
        Assertions.assertNotNull(actualValue,
                "Field '" + jsonPath + "' was null in response.");
        Assertions.assertEquals(
                expectedValue,
                String.valueOf(actualValue),
                "Field value mismatch for path: " + jsonPath
        );
    }

    @Then("the response body should contain the field {string}")
    public void verifyFieldExists(String jsonPath) {
        Object value = response.jsonPath().get(jsonPath);
        Assertions.assertNotNull(
                value,
                "Expected field '" + jsonPath + "' to be present in response but it was null/missing."
        );
    }

    @Then("the response body error field should contain {string}")
    public void verifyErrorField(String expectedErrorMessage) {
        String actualError = response.jsonPath().getString("error");
        Assertions.assertNotNull(actualError, "Expected 'error' field in response but it was absent.");
        Assertions.assertTrue(
                actualError.contains(expectedErrorMessage),
                "Error message mismatch. Expected to contain: '" + expectedErrorMessage
                        + "' but was: '" + actualError + "'"
        );
    }
}
