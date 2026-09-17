package screens;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Driver;
import utils.PromoOverlayHandler;

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

    private void handlePromoOverlay() {
        PromoOverlayHandler.dismissIfPresent(driver);
    }

    protected void clearInputElement(By locator) {
        handlePromoOverlay();

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        ).clear();
    }

    protected boolean isElementDisplayed(By locator) {
        handlePromoOverlay();

        try {
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(locator)
            ).isDisplayed();

        } catch (TimeoutException e) {
            return false;
        }
    }

    protected void clickOnElement(By locator) {
        handlePromoOverlay();

        wait.until(
                ExpectedConditions.elementToBeClickable(locator)
        ).click();
    }

    protected void sendKeysToElement(By locator, String text) {
        handlePromoOverlay();

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        ).sendKeys(text);
    }

    protected String getTextFromElement(By locator) {
        handlePromoOverlay();

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        ).getText();
    }

    protected List<WebElement> getElements(By locator) {
        handlePromoOverlay();

        return driver.findElements(locator);
    }

    protected void performEditorAction(String action) {
        driver.executeScript(
                "mobile: performEditorAction",
                Map.of("action", action)
        );
    }

    protected boolean areElementsDisplayed(By locator) {
        handlePromoOverlay();

        try {
            return wait.until(driver ->
                    driver.findElements(locator)
                            .stream()
                            .anyMatch(WebElement::isDisplayed)
            );

        } catch (TimeoutException e) {
            return false;
        }
    }

    protected void sendKeysActiveElement(String text) {
        handlePromoOverlay();

        WebElement activeElement =
                driver.switchTo().activeElement();

        activeElement.sendKeys(text);
    }

    protected void waitForElement(By locator) {
        handlePromoOverlay();

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );
    }

    protected void clickNthElement(By locator, int index) {
        handlePromoOverlay();

        WebElement element = wait.until(driver -> {
            List<WebElement> elements =
                    driver.findElements(locator);

            return elements.size() > index
                    ? elements.get(index)
                    : null;
        });

        element.click();
    }

    protected String getContentDescription(By locator) {
        handlePromoOverlay();

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        ).getAttribute("content-desc");
    }

    protected void waitForElementToDisappear(By locator) {
        wait.until(
                ExpectedConditions.invisibilityOfElementLocated(locator)
        );
    }

    protected WebElement getParentElement(By childLocator) {
        handlePromoOverlay();

        WebElement child = wait.until(
                ExpectedConditions.visibilityOfElementLocated(childLocator)
        );

        return child.findElement(By.xpath(".."));
    }
}