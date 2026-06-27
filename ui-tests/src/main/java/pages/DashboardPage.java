package pages;

import org.openqa.selenium.By;

/**
 * DashboardPage — Page Object for the OrangeHRM dashboard shown after login.
 */
public class DashboardPage extends BasePage {

    // ── Locators ─────────────────────────────────────────────────────────────
    private final By dashboardHeading   = By.cssSelector("h6.oxd-text--h6");
    private final By userProfileIcon    = By.cssSelector("img.oxd-userdropdown-img");

    // ── Assertions / Getters ─────────────────────────────────────────────────

    public String getDashboardHeading() {
        return getText(dashboardHeading);
    }

    public boolean isUserProfileIconVisible() {
        return isElementVisible(userProfileIcon);
    }

    public boolean isOnDashboard() {
        return getCurrentUrl().contains("/dashboard");
    }
}
