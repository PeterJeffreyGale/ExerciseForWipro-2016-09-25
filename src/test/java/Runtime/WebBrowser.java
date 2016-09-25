package Runtime;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by Peter_000 on 25/09/2016.
 */

public class WebBrowser {

    private WebDriver driver;

    public WebBrowser(String driverType) {
        if (driverType.equalsIgnoreCase("Firefox")) {
            this.driver = new FirefoxDriver();
        }
    }

    public void goToURL(String url) {
        driver.get(url);
    }

    public void clickOnElementByXpath(String xpath) {
        waitForElementVisibleByXpath(xpath).click();
    }

    public void enterTextByXpath(String xpath, String textToEnter) {
        WebElement inputBox = driver.findElement(By.xpath(xpath));
        inputBox.clear();
        inputBox.sendKeys(textToEnter);
    }

    public boolean elementIsDisplayedByXpath(String xpath) {
        //System.out.println("xpath="+xpath);
        boolean elementIsDisplayed=waitForElementVisibleByXpath(xpath).isDisplayed();
        //System.out.println("elementIsDisplayed="+elementIsDisplayed);
        return elementIsDisplayed;
    }

    public WebElement waitForElementVisibleByXpath(String xpath) {
        WebDriverWait wait = new WebDriverWait(driver, 15,100);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    public boolean waitForElementNotVisibleByXpath(String xpath) {
        WebDriverWait wait = new WebDriverWait(driver, 15,100);
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
    }

    public void waitForAngularToFinish() {
        JavascriptExecutor js =  (JavascriptExecutor) driver;
        String strReturnValue="";
        // See Mark Collins: http://ardesco.lazerycode.com/index.php/2014/02/waiting-angular/#comment-9222
        String strJavaScript="return (window.angular != null) && (angular.element(document).injector() != null) && (angular.element(document).injector().get('$http').pendingRequests.length === 0)";
        strReturnValue = js.executeScript(strJavaScript).toString();
    }

    public int countElementsByXpath(String xpath) {
        return driver.findElements(By.xpath(xpath)).size();
    }

    public void closeWebBrowser() {
        driver.quit();
    }



}
