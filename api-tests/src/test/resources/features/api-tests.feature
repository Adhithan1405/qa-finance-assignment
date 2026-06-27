@api
Feature: Reqres.in User API — Finance-adjacent REST API Tests

  # API-TC-001 — Positive: fetch a single user
  @positive @API_TC001
  Scenario: GET single user returns 200 with correct fields
    When I send a GET request to "/users/2"
    Then the response status code should be 200
    And the response body should contain field "data.id" with value "2"
    And the response body should contain the field "data.email"
    And the response body should contain the field "data.first_name"

  # API-TC-002 — Positive: create a new user
  @positive @API_TC002
  Scenario: POST create user returns 201 with generated id
    Given I have the following user payload:
      | name | job          |
      | John | QA Engineer  |
    When I send a POST request to "/users"
    Then the response status code should be 201
    And the response body should contain the field "id"
    And the response body should contain the field "createdAt"

  # API-TC-003 — Negative: request non-existent user returns 404
  @negative @API_TC003
  Scenario: GET non-existent user returns 404
    When I send a GET request to "/users/9999"
    Then the response status code should be 404

  # API-TC-004 — Negative: login with missing password returns 400
  @negative @API_TC004
  Scenario: POST login without password field returns 400
    Given I send a login request with email "eve.holt@reqres.in" and no password
    Then the response status code should be 400
    And the response body error field should contain "Missing password"

  # API-TC-005 — Positive: list users returns paginated results
  @positive @API_TC005
  Scenario: GET list users returns correct pagination metadata
    When I send a GET request to "/users?page=1"
    Then the response status code should be 200
    And the response body should contain the field "total"
    And the response body should contain the field "data"
