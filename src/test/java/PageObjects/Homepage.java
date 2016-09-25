package PageObjects;

import Runtime.UserSession;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.Assert.assertTrue;

/**
 * Created by Peter Gale on 25/09/2016.
 * Page Object for the Five Day Weather Forecast homepage
 */

public class Homepage {

    UserSession parentUserSession;
    String homepageURL = "https://weather-acceptance.herokuapp.com/";
    String pageHeaderXPath = "//h1[.='Five Day Weather Forecast for']";
    String cityNameInputElementXPath = pageHeaderXPath + "//input";
    String forecastRetrievalErrorElementXPath = pageHeaderXPath + "//following-sibling::*[1][.='Error retrieving the forecast']";

    String dayRowsXPath="//div[@data-reactroot]/div";
    String timeRowsXPath="//div[@class='detail']";

    String currentDayOfMonthRowXPath;


    public Homepage(UserSession userSession) {
        parentUserSession=userSession;
    }

    public void goToHomePage() {
        parentUserSession.webBrowser.goToURL(homepageURL);
    }

    public void enterACityName(String cityName) {
        parentUserSession.webBrowser.enterTextByXpath(cityNameInputElementXPath, cityName + Keys.ENTER);
    }

    public boolean cityDisplayedIs(String cityName) {
        return parentUserSession.webBrowser.elementIsDisplayedByXpath(cityNameInputElementXPath + "[@value='" + cityName + "' and @placeholder='city']");
    }

    public boolean forecastRetrievalErrorIsDisplayed() {
        return parentUserSession.webBrowser.elementIsDisplayedByXpath(forecastRetrievalErrorElementXPath);
    }

    public boolean forecastRetrievalErrorIsNotDisplayed() {
        return parentUserSession.webBrowser.waitForElementNotVisibleByXpath(forecastRetrievalErrorElementXPath);
    }

    public int countNumberOfDaysForecast() {
        return parentUserSession.webBrowser.countElementsByXpath(dayRowsXPath);
    }

    public boolean dayIsDisplayedAsTheNumberedRow(int rowNumber, String dayOfWeek, int dayOfMonth) {
        // Assumption: The day is specified in the first column, with two child elements - one showing the day of week, another the day of month
        // Only the first three letters of the day of week is displayed, hence 'substring(0,3)'
        String currentXPath=dayRowsXPath + "[" + rowNumber + "]//*[@class='cell'][1]//*[1][.='" + dayOfWeek.substring(0,3) + "']//following-sibling::*[1][.=" + dayOfMonth + "]";
        return parentUserSession.webBrowser.elementIsDisplayedByXpath(currentXPath);
    }

    public int countNumberOfForecastTimes() {
        return parentUserSession.webBrowser.countElementsByXpath(timeRowsXPath);
    }

    public void expandOrContractAForecastDay(String dayOfMonth) {
        currentDayOfMonthRowXPath=dayRowsXPath+ "//*[@class='cell'][1]//*[.='" + dayOfMonth + "']//ancestor::div[@class='summary']";
        parentUserSession.webBrowser.clickOnElementByXpath(currentDayOfMonthRowXPath);
    }

    public boolean aParticularTimeOfDayIsDisplayed(String timeOfDay) {
        return parentUserSession.webBrowser.elementIsDisplayedByXpath(currentDayOfMonthRowXPath + "//following::div[@class='details'][1]//div[@class='detail']/span[1][.='" + timeOfDay +"']/..");
    }



        // count(//div[@data-reactroot]/div)

//    public void goToSubSection(String subSectionName) {
//        //WebElement subHeaderNavigationElement = driver.findElement(By.xpath("//nav[@aria-label='BBC' and @role='navigation']//li//a[.='News']"));
//       //parentUserSession.webBrowser.clickOnElementByXpath("//nav[@aria-label='BBC' and @role='navigation']//li//a[.='" + subSectionName + "']");
//        parentUserSession.webBrowser.waitForAngularToFinish();
//        if ("News".equalsIgnoreCase(subSectionName)) {
//            newPage=new Newspage(parentUserSession);
//        } else if ("Sport".equalsIgnoreCase(subSectionName)) {
//            newPage = new Sportspage(parentUserSession);
//        } else {
//            newPage=this;
//        }
//        return newPage;
//    }


}
