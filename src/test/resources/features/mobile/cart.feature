@mobile
Feature: Shopping Cart - My Demo App (Android)
  As a shopper using the mobile app
  I want to add products with a chosen quantity to my cart
  So that I can buy several items at once

  Background:
    Given I open the app on the catalog screen

  @TC-MOB-004 @smoke
  Scenario: Add a product with quantity 2 to the cart
    When I open the product "Sauce Labs Backpack"
    And I add 2 items to the cart
    Then the mobile cart badge should show 2
    When I open the mobile cart
    Then the mobile cart should contain "Sauce Labs Backpack"
    And the mobile cart should show "2 Items" with total "$ 59.98"
