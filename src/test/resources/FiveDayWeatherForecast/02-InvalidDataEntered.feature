Feature: InvalidDataEntered

  The application displays the 5 day weather forecast for a given location.

  Rules:
  - Undefined cities generate an error

  Background:
    Given a user
    When the user opens a web browser of type FireFox
    And the user navigates to the Five Day Weather Forecast homepage

  Scenario Outline: the user enters an invalid city name
    When the user enters a city name <invalidCityName>
    Then the city shown is <invalidCityName>
    And a forecast retrieval error is displayed

  Examples:
    | invalidCityName |
    | York |
    | AGlasgowZ |

