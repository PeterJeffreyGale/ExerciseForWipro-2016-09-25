package PageObjects;

import Runtime.UserSession;

/**
 * Created by Peter_000 on 24/09/2016.
 */
public class Sportspage extends BasePage {

    UserSession parentUserSession;

    public Sportspage(UserSession userSession) {
        parentUserSession=userSession;
    }

    public boolean isDisplayed() {
        return parentUserSession.webBrowser.elementIsDisplayedByXpath("//header[@role='banner']//*[.='BBC Sport']");
    }

}
