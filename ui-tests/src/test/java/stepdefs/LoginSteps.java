package stepdefs;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;
import pages.DashboardPage;
import pages.LoginPage;
import utils.DriverManager;

/**
 * LoginSteps — Cucumber step definitions wired to login.feature.
 * All browser interactions go through Page Objects only.
 */
public class LoginSteps {

    private LoginPage loginPage;
    private DashboardPage dashboardPage;

    @Before
    public void setUp() {
        // Driver is initialised lazily in DriverManager on first getDriver() call
        loginPage     = new LoginPage();
        dashboardPage = new DashboardPage();
    }

    @After
    public void tearDown() {
        DriverManager.quitDriver();
    }

    // ── Given ────────────────────────────────────────────────────────────────

    @Given("the user navigates to the OrangeHRM login page")
    public void navigateToLoginPage() {
        loginPage.navigateTo(DriverManager.getBaseUrl());
    }

    // ── When ─────────────────────────────────────────────────────────────────

    @When("the user enters username {string} and password {string}")
    public void enterCredentials(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @When("the user clicks the Login button")
    public void clickLogin() {
        loginPage.clickLogin();
    }

    // ── Then ─────────────────────────────────────────────────────────────────

    @Then("the user should be redirected to the Dashboard")
    public void verifyRedirectToDashboard() {
        Assertions.assertTrue(
                dashboardPage.isOnDashboard(),
                "Expected URL to contain '/dashboard' but was: " + dashboardPage.getCurrentUrl()
        );
    }

    @Then("the page heading should display {string}")
    public void verifyPageHeading(String expectedHeading) {
        String actualHeading = dashboardPage.getDashboardHeading();
        Assertions.assertEquals(
                expectedHeading, actualHeading,
                "Dashboard heading mismatch."
        );
    }

    @Then("the user profile icon should be visible in the top navigation bar")
    public void verifyUserProfileIcon() {
        Assertions.assertTrue(
                dashboardPage.isUserProfileIconVisible(),
                "User profile icon was not visible after login."
        );
    }

    @Then("an error message {string} should be displayed")
    public void verifyErrorMessage(String expectedMessage) {
        Assertions.assertTrue(
                loginPage.isErrorDisplayed(),
                "Expected error alert to be displayed, but it was not."
        );
        String actualMessage = loginPage.getErrorMessage();
        Assertions.assertEquals(
                expectedMessage, actualMessage,
                "Error message text mismatch."
        );
    }

    @Then("the user should remain on the login page")
    public void verifyStillOnLoginPage() {
        Assertions.assertTrue(
                loginPage.isOnLoginPage(),
                "Expected user to remain on login page but URL was: " + loginPage.getCurrentUrl()
        );
    }

    @Then("a required field validation message should appear for the username")
    public void verifyUsernameRequired() {
        Assertions.assertTrue(
                loginPage.isUsernameValidationVisible(),
                "Expected 'Required' validation under username but none appeared."
        );
        Assertions.assertEquals(
                "Required",
                loginPage.getUsernameValidationText(),
                "Username validation message text mismatch."
        );
    }

    @Then("required field validations should appear for both username and password")
    public void verifyBothFieldsRequired() {
        Assertions.assertTrue(
                loginPage.isUsernameValidationVisible(),
                "Expected 'Required' under username."
        );
        Assertions.assertTrue(
                loginPage.isPasswordValidationVisible(),
                "Expected 'Required' under password."
        );
    }
}
