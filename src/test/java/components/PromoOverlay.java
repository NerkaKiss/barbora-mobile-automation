package components;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import screens.Common;

import java.time.Duration;

public class PromoOverlay extends Common {

    private static final Duration PROMO_WAIT = Duration.ofSeconds(3);

    private final By promoDismiss =
            AppiumBy.accessibilityId("Atsisakyti");

    public void dismissIfDisplayed() {
        WebDriverWait promoWait =
                new WebDriverWait(driver, PROMO_WAIT);

        try {
            promoWait.until(
                    ExpectedConditions.visibilityOfElementLocated(promoDismiss)
            );
        } catch (TimeoutException ignored) {
            return;
        }

        clickOnElement(promoDismiss);

        try {
            promoWait.until(
                    ExpectedConditions.invisibilityOfElementLocated(promoDismiss)
            );
        } catch (TimeoutException e) {
            throw new IllegalStateException(
                    "Promo overlay was detected but did not close",
                    e
            );
        }
    }
}