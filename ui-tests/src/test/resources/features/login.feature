@login
Feature: OrangeHRM Login

  Background:
    Given the user navigates to the OrangeHRM login page

  # TC-001 — Positive: valid credentials
  @positive @TC001
  Scenario: Successful login with valid credentials
    When the user enters username "Admin" and password "admin123"
    And the user clicks the Login button
    Then the user should be redirected to the Dashboard
    And the page heading should display "Dashboard"

  # TC-002 — Positive: session header
  @positive @TC002
  Scenario: User profile is visible in header after login
    When the user enters username "Admin" and password "admin123"
    And the user clicks the Login button
    Then the user profile icon should be visible in the top navigation bar

  # TC-003 — Negative: wrong password
  @negative @TC003
  Scenario: Login fails with incorrect password
    When the user enters username "Admin" and password "wrongpassword"
    And the user clicks the Login button
    Then an error message "Invalid credentials" should be displayed
    And the user should remain on the login page

  # TC-005 — Negative: empty username
  @negative @TC005
  Scenario: Login fails when username is left empty
    When the user enters username "" and password "admin123"
    And the user clicks the Login button
    Then a required field validation message should appear for the username

  # TC-007 — Negative: both fields empty
  @negative @TC007
  Scenario: Login fails when both fields are empty
    When the user enters username "" and password ""
    And the user clicks the Login button
    Then required field validations should appear for both username and password

  # TC-008 — Boundary: single character password
  @boundary @TC008
  Scenario: Login fails with a single character password
    When the user enters username "Admin" and password "a"
    And the user clicks the Login button
    Then an error message "Invalid credentials" should be displayed
