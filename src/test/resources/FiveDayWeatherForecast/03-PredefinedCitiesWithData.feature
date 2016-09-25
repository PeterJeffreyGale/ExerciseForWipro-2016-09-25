Feature: ValidCitiesSelected

  The application displays the 5 day weather forecast for a given location.

  Rules:
  - Enter city name, get 5 day weather forecast
  - Select day, get 3 hourly forecast
  - Select day again, hide 3 hourly forecast
  - Daily forecast should summarise the 3 hour data:
  - Most dominant (or current) condition
  - Most dominant (or current) wind speed and direction
  - Aggregate rainfall
  - Minimum and maximum temperatures
  - All values should be rounded down

  | Aberdeen |
  | Dundee |
  | Edinburgh |
  | Glasgow |
  | Perth |
  | Stirling |

  Background:
    Given a user
    When the user opens a web browser of type FireFox
    And the user navigates to the Five Day Weather Forecast homepage

  Scenario Outline: the user enters a valid city name
    When the user enters a city name <validCityName>
    Then the city shown is <validCityName>
    And a forecast retrieval error is not displayed
    And 5 days of forecast data is shown
    Given today is Tuesday the 20
    Then the next 5 days forecasts are shown
    And each day's 3 hourly forecast can be expanded and contracted and is displayed correctly

  Examples:
    | validCityName |
    | Aberdeen |
    | Dundee |
    | Edinburgh |
    | Glasgow |
    | Perth |
    | Stirling |
