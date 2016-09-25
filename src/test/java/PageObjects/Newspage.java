package PageObjects;

import Runtime.UserSession;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by Peter_000 on 25/09/2016.
 */


public class Newspage extends BasePage {

    UserSession parentUserSession;

    public Newspage(UserSession userSession) {
        parentUserSession=userSession;
    }

    public boolean isDisplayed() {
        return parentUserSession.webBrowser.elementIsDisplayedByXpath("//div[@aria-label='News']");
    }

}
