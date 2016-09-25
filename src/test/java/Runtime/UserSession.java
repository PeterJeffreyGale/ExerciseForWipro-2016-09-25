package Runtime;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

/**
 * Created by Peter Gale on 24/09/2016.
 */

public class UserSession {

    public WebBrowser webBrowser;

    public void UserSession() {
    }

    public void getNewWebBrowserSession(String driverType) {
        webBrowser = new WebBrowser(driverType);
    }

    public void closeSession() {
        webBrowser.closeWebBrowser();
    }


}
