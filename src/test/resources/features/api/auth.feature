@api
Feature: Authentication API - Restful Booker
  A token is required for every update and delete request

  @TC-API-009 @smoke
  Scenario: Create a token with valid credentials
    When I request a token with valid admin credentials
    Then the response status code should be 200
    And the response field "token" should not be empty

  @TC-API-010 @negative
  Scenario Outline: Token is not created with invalid credentials - <case>
    When I request a token with username "<username>" and password "<password>"
    Then the response status code should be 200
    And the response field "reason" should be "Bad credentials"
    And the response field "token" should not exist

    Examples:
      | case            | username | password    |
      | wrong password  | admin    | wrong-pass  |
      | unknown user    | ghost    | password123 |
      | empty fields    |          |             |
