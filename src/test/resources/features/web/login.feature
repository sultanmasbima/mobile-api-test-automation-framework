@web
Feature: Login - Sauce Demo
  As a registered customer
  I want to log in to the store
  So that I can shop for products

  Background:
    Given I am on the login page

  @TC-WEB-001 @smoke
  Scenario: Successful login with a valid user
    When I log in as "standard_user"
    Then I should see the "Products" page

  @TC-WEB-002 @negative
  Scenario Outline: Login is rejected - <case>
    When I log in with username "<username>" and password "<password>"
    Then I should see the login error "<error>"

    Examples:
      | case             | username        | password     | error                                                                     |
      | locked out user  | locked_out_user | secret_sauce | Epic sadface: Sorry, this user has been locked out.                       |
      | wrong password   | standard_user   | wrong_pass   | Epic sadface: Username and password do not match any user in this service |
      | empty username   |                 | secret_sauce | Epic sadface: Username is required                                        |
      | empty password   | standard_user   |              | Epic sadface: Password is required                                        |
