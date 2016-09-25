Feature: DefaultSettings

  The application displays the 5 day weather forecast for a given location.

  Rules:
  - The application defaults to a particular city

  To do:

  Background:
    Given a user
    When the user opens a web browser of type FireFox
    And the user navigates to the Five Day Weather Forecast homepage

  Scenario: the default city is Glasgow
    Then the city shown is Glasgow