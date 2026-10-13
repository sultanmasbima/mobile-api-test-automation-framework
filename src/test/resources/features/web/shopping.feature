@web
Feature: Shopping & Checkout - Sauce Demo
  As a logged in customer
  I want to browse, add products to my cart and check out
  So that I can buy what I need

  Background:
    Given I am logged in as "standard_user"

  @TC-WEB-003 @smoke
  Scenario: Add a single product to the cart
    When I add "Sauce Labs Backpack" to the cart
    Then the cart badge should show 1
    And the cart should contain:
      | Sauce Labs Backpack |

  @TC-WEB-004
  Scenario: Add multiple products to the cart
    When I add the following products to the cart:
      | Sauce Labs Backpack     |
      | Sauce Labs Bike Light   |
      | Sauce Labs Bolt T-Shirt |
    Then the cart badge should show 3
    And the cart should contain:
      | Sauce Labs Backpack     |
      | Sauce Labs Bike Light   |
      | Sauce Labs Bolt T-Shirt |

  @TC-WEB-005
  Scenario: Sort products by price from low to high
    When I sort products by "Price (low to high)"
    Then the product prices should be sorted in ascending order

  @TC-WEB-006
  Scenario: Sort products by name from Z to A
    When I sort products by "Name (Z to A)"
    Then the product names should be sorted in descending order

  @TC-WEB-007 @smoke
  Scenario: Complete checkout end to end
    When I add "Sauce Labs Backpack" to the cart
    And I proceed to checkout
    And I fill in checkout information "Raihan" "Tester" "12345"
    And I finish the order
    Then I should see the order confirmation "Thank you for your order!"

  @TC-WEB-008 @negative
  Scenario: Checkout is rejected without a postal code
    When I add "Sauce Labs Backpack" to the cart
    And I proceed to checkout
    And I fill in checkout information "Raihan" "Tester" ""
    Then I should see the checkout error "Error: Postal Code is required"
