@api
Feature: Booking API - Restful Booker
  As a hotel staff member
  I want to manage bookings through the API
  So that guest reservations are always up to date

  @TC-API-001 @smoke
  Scenario: Create a new booking with valid data
    When I create a booking with the following details:
      | firstName       | Jim        |
      | lastName        | Brown      |
      | totalPrice      | 250        |
      | depositPaid     | true       |
      | checkIn         | 2027-01-10 |
      | checkOut        | 2027-01-15 |
      | additionalNeeds | Breakfast  |
    Then the response status code should be 200
    And the response should match the "create-booking" JSON schema
    And the created booking should contain the submitted details

  @TC-API-002 @smoke
  Scenario: Retrieve an existing booking by id
    Given a booking exists for guest "Sally" "Brown"
    When I retrieve the booking by its id
    Then the response status code should be 200
    And the response should match the "booking" JSON schema
    And the booking first name should be "Sally"

  @TC-API-003
  Scenario: Search bookings by guest name
    Given a booking exists for guest "Raihan" with a unique last name
    When I search bookings by the guest name
    Then the response status code should be 200
    And the search result should contain the booking id

  @TC-API-004
  Scenario: Fully update a booking with a valid token
    Given a booking exists for guest "Mark" "Twain"
    And I am authenticated as admin
    When I update the booking total price to 999 and additional needs to "Late checkout"
    Then the response status code should be 200
    And the booking total price should be 999
    And the booking additional needs should be "Late checkout"

  @TC-API-005
  Scenario: Partially update the guest first name
    Given a booking exists for guest "Mark" "Twain"
    And I am authenticated as admin
    When I partially update the booking first name to "Samuel"
    Then the response status code should be 200
    And the booking first name should be "Samuel"

  @TC-API-006 @negative
  Scenario: Update is rejected when the token is invalid
    Given a booking exists for guest "Mark" "Twain"
    When I update the booking total price to 1 using token "invalid-token"
    Then the response status code should be 403

  @TC-API-007
  Scenario: Delete a booking
    Given a booking exists for guest "Delete" "Me"
    And I am authenticated as admin
    When I delete the booking
    # Restful Booker returns 201 (not 200/204) on a successful delete - documented API behaviour
    Then the response status code should be 201
    And retrieving the booking should return status code 404

  @TC-API-008 @negative
  Scenario: Retrieve a booking that does not exist
    When I retrieve the booking with id 999999999
    Then the response status code should be 404
