@mobile
Feature: Product Catalog - My Demo App (Android)
  As a shopper using the mobile app
  I want to browse and sort the product catalog
  So that I can quickly find what I want to buy

  Background:
    Given I open the app on the catalog screen

  @TC-MOB-001 @smoke
  Scenario: Catalog is displayed when the app is opened
    Then I should see the "Products" screen
    And the catalog should show "Sauce Labs Backpack"

  @TC-MOB-002
  Scenario: Sort products by price from low to high
    When I sort the catalog by "Price - Ascending"
    Then the visible product prices should be sorted in ascending order

  @TC-MOB-003
  Scenario: Sort products by name from Z to A
    When I sort the catalog by "Name - Descending"
    Then the visible product names should be sorted in descending order
