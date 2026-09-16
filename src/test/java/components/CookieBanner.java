package components;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import screens.Common;

import java.time.Duration;

public class CookieBanner extends Common {

    private final By acceptCookiesButton =
            AppiumBy.accessibilityId("Uždaryti");

    public void acceptIfDisplayed() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.elementToBeClickable(acceptCookiesButton))
                    .click();
        } catch (TimeoutException ignored) {
            // Banner already handled or not displayed
        }
    }
}