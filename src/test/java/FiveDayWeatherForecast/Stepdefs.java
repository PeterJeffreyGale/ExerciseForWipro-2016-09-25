package FiveDayWeatherForecast;

import PageObjects.Homepage;
import PageObjects.Newspage;
import PageObjects.Sportspage;
import Runtime.*;

import cucumber.api.java.Before ;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.en.And;

//http://stackoverflow.com/questions/24013210/assert-in-junit-framework-has-been-deprecated-what-use-next
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.JSONObject;
import org.json.JSONArray;

import static org.junit.Assert.*;

// Refs:
// http://toolsqa.com/cucumber/data-driven-testing-in-cucumber/
// https://cucumber.io/docs/reference
// https://github.com/cucumber/cucumber-jvm/issues/367
// http://www.oracle.com/technetwork/articles/java/json-1973242.html

public class Stepdefs {

    private UserSession userSession;
    private Homepage homePage;

    private Newspage newsPage;
    private Sportspage sportsPage;

    private String currentCityName;
    private String dayOfWeek;
    private int dayOfMonth;

    String jsonData;
    JSONObject jsonObject_Full;

    @Before
    public void setUp() {
    }

    @Given("^a user$")
    public void a_user() throws Throwable {
        userSession = new UserSession();
    }

    @When("^the user opens a web browser of type (\\w+)$")
    public void the_user_opens_a_web_browser_of_type(String driverType) throws Throwable {
        userSession.getNewWebBrowserSession(driverType);
    }

    @And("^the user navigates to the Five Day Weather Forecast homepage$")
    public void the_user_navigates_to_the_Five_Day_Weather_Forecast_homepage() throws Throwable {
        homePage = new Homepage(userSession);
        homePage.goToHomePage();
    }

    @When("^the user enters a city name (\\w+)$")
    public void the_user_enters_a_city_name(String cityName) throws Throwable {
        currentCityName=cityName;
        homePage.enterACityName(cityName);
    }

    @Then("^the city shown is (\\w+)$")
    public void the_city_shown_is(String cityName) throws Throwable {
        assertTrue(homePage.cityDisplayedIs(cityName));
    }

    @And("^a forecast retrieval error is displayed$")
    public void a_forecast_retrieval_error_is_displayed() throws Throwable {
        assertTrue(homePage.forecastRetrievalErrorIsDisplayed());
    }

    @And("^a forecast retrieval error is not displayed$")
    public void a_forecast_retrieval_error_is_not_displayed() throws Throwable {
        assertTrue(homePage.forecastRetrievalErrorIsNotDisplayed());
    }

    @And("^(\\w+) days of forecast data is shown$")
    public void the_city_shown_is(int numberOfDaysForecast) throws Throwable {
        assertEquals(homePage.countNumberOfDaysForecast(), numberOfDaysForecast);
    }

    @Given("^today is (\\w+) the (\\w+)$")
    public void today_is (String dayOfWeek, int dayOfMonth) throws Throwable {
        this.dayOfWeek=dayOfWeek;
        this.dayOfMonth=dayOfMonth;
    }

    @Then("^the next 5 days forecasts are shown$")
    public void the_next_five_days_forecasts_are_shown() throws Throwable {
        assertTrue(homePage.dayIsDisplayedAsTheNumberedRow(1,dayOfWeek,dayOfMonth));
        assertTrue(homePage.dayIsDisplayedAsTheNumberedRow(2,"Wednesday",dayOfMonth+1));
        assertTrue(homePage.dayIsDisplayedAsTheNumberedRow(3,"Thursday",dayOfMonth+2));
        assertTrue(homePage.dayIsDisplayedAsTheNumberedRow(4,"Friday",dayOfMonth+3));
        assertTrue(homePage.dayIsDisplayedAsTheNumberedRow(5,"Saturday",dayOfMonth+4));
    }

    @Then("^each day's 3 hourly forecast can be expanded and contracted and is displayed correctly$")
    public void each_days_3_hourly_forecast_can_be_expanded_and_contracted_and_is_displayed_correctly() throws Throwable {

        // Get the test data for the current city
        jsonData=getJSONDataForCurrentCity();
        jsonObject_Full= new JSONObject(jsonData);

        // Get the name of the city in the current file and make sure it matches what we expect
        String cityNameFromFile=getCityNameFromJSONData();
        assertEquals(currentCityName, cityNameFromFile);

        // Process each data entry
        loopThoughAllForecastEntriesInTheDataFile();

    }

    private String getJSONDataForCurrentCity() throws IOException {
        //http://stackoverflow.com/questions/4871051/getting-the-current-working-directory-in-java
        Path currentRelativePath = Paths.get("");
        String dataPath = currentRelativePath.toAbsolutePath().toString();
        dataPath=dataPath + "/src/data/";
        dataPath=dataPath + currentCityName.toLowerCase() +".json";
        return readFile(dataPath, StandardCharsets.UTF_8);
    }

    private String readFile(String path, Charset encoding) throws IOException  {
        // http://stackoverflow.com/questions/326390/how-do-i-create-a-java-string-from-the-contents-of-a-file
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    private String getCityNameFromJSONData() {
        JSONObject jsonObject_City=jsonObject_Full.getJSONObject("city");
        String cityName=jsonObject_City.getString("name");
        return cityName;
    }

    private void loopThoughAllForecastEntriesInTheDataFile() {

        // Get the list of all weather forecast entries for different times of day from the file
        JSONArray jsonArray_AllForecasts=jsonObject_Full.getJSONArray("list");

        // Check the total number of entries on file matches that displayed on the screen
        int numberOfForecastEntriesOnFile=jsonArray_AllForecasts.length();
        assertEquals(homePage.countNumberOfForecastTimes(), numberOfForecastEntriesOnFile);

        // Loop through each entry
        for (int i = 0; i < numberOfForecastEntriesOnFile; i++) {
            checkASingleSetOfForecastData(jsonArray_AllForecasts.getJSONObject(i));
        }

        //TODO: Summary checks for each day required here

    }

    private void checkASingleSetOfForecastData(JSONObject jsonObject_SingleTimeForecast) {

        String datetimeText=jsonObject_SingleTimeForecast.getString("dt_txt");
        String singleForecastDayOfMonth=datetimeText.substring(8, 10);
        String singleForecastTime=datetimeText.substring(11, 13);
        String singleForecastTimeAdjusted = Integer.toString((Integer.parseInt(singleForecastTime)+1)*100);
        if (singleForecastTimeAdjusted.length()<4) {
            singleForecastTimeAdjusted="0" + singleForecastTimeAdjusted;
        }

        homePage.expandOrContractAForecastDay(singleForecastDayOfMonth);
        assertTrue(homePage.aParticularTimeOfDayIsDisplayed(singleForecastTimeAdjusted));
        //TODO: Further individual checks required here
        homePage.expandOrContractAForecastDay(singleForecastDayOfMonth);

    }

    @After
    public void closeSession(){
      userSession.closeSession();
    }


}
