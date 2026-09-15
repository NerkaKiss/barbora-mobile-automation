package screens;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Driver;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class Common {

    protected final AndroidDriver driver;
    protected final WebDriverWait wait;

    public Common() {
        this.driver = Driver.getDriver();
        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(10)
        );
    }

    protected boolean isElementDisplayed(By locator) {
        try {
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(locator)
            ).isDisplayed();

        } catch (TimeoutException e) {
            return false;
        }
    }

    protected void clickOnElement(By locator) {
        wait.until(
                ExpectedConditions.elementToBeClickable(locator)
        ).click();
    }

    protected void sendKeysToElement(By locator, String text) {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        ).sendKeys(text);
    }

    protected String getTextFromElement(By locator) {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        ).getText();
    }

    protected List<WebElement> getElements(By locator) {
        return driver.findElements(locator);
    }

    protected void performEditorAction(String action) {
        driver.executeScript(
                "mobile: performEditorAction",
                Map.of("action", action)
        );
    }
}