package pages;

import org.openqa.selenium.By;

/**
 * LoginPage — Page Object for OrangeHRM login screen.
 * All locators are maintained here; step definitions never reference By directly.
 */
public class LoginPage extends BasePage {

    // ── Locators ────────────────────────────────────────────────────────────
    private final By usernameInput   = By.name("username");
    private final By passwordInput   = By.name("password");
    private final By loginButton     = By.cssSelector("button[type='submit']");
    private final By errorAlert      = By.cssSelector(".oxd-alert-content-text");
    private final By usernameError   = By.xpath("(//span[contains(@class,'oxd-input-field-error-message')])[1]");
    private final By passwordError   = By.xpath("(//span[contains(@class,'oxd-input-field-error-message')])[2]");

    // ── Actions ──────────────────────────────────────────────────────────────

    public void navigateTo(String url) {
        driver.get(url);
    }

    public void enterUsername(String username) {
        type(usernameInput, username);
    }

    public void enterPassword(String password) {
        type(passwordInput, password);
    }

    public void clickLogin() {
        click(loginButton);
    }

    public void loginWith(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    // ── Assertions / Getters ─────────────────────────────────────────────────

    public String getErrorMessage() {
        return getText(errorAlert);
    }

    public boolean isErrorDisplayed() {
        return isElementVisible(errorAlert);
    }

    public boolean isUsernameValidationVisible() {
        return isElementVisible(usernameError);
    }

    public boolean isPasswordValidationVisible() {
        return isElementVisible(passwordError);
    }

    public String getUsernameValidationText() {
        return getText(usernameError);
    }

    public String getPasswordValidationText() {
        return getText(passwordError);
    }

    public boolean isOnLoginPage() {
        return getCurrentUrl().contains("/auth/login");
    }
}
