@mobile
Feature: Login & Checkout - My Demo App (Android)
  As a registered shopper using the mobile app
  I want to log in and check out my cart
  So that my order is placed and shipped to me

  Background:
    Given I open the app on the catalog screen

  @TC-MOB-005 @negative
  Scenario Outline: Login is rejected - <case>
    Given I open the login screen from the menu
    When I log in on mobile with username "<username>" and password "<password>"
    Then I should see the mobile login error "<error>"

    Examples:
      | case            | username          | password | error                                |
      | empty username  |                   | 10203040 | Username is required                 |
      | empty password  | bod@example.com   |          | Enter Password                       |
      | locked out user | alice@example.com | 10203040 | Sorry this user has been locked out. |

  @TC-MOB-006 @smoke
  Scenario: Complete checkout end to end
    Given I have "Sauce Labs Backpack" in my mobile cart
    When I proceed to checkout from the mobile cart
    And I log in on mobile as the default user
    And I submit the shipping address:
      | fullName | Raihan Tester  |
      | address  | Jl. Sudirman 1 |
      | city     | Jakarta        |
      | zipCode  | 10220          |
      | country  | Indonesia      |
    And I submit the payment details:
      | cardHolder     | Raihan Tester    |
      | cardNumber     | 4111111111111111 |
      | expirationDate | 0330             |
      | securityCode   | 123              |
    And I place the order
    Then I should see the "Checkout Complete" confirmation

  @TC-MOB-007 @negative
  Scenario: Checkout is rejected when the shipping address is empty
    Given I have "Sauce Labs Backpack" in my mobile cart
    When I proceed to checkout from the mobile cart
    And I log in on mobile as the default user
    And I submit an empty shipping address
    Then I should see the shipping error "Please provide your full name."
