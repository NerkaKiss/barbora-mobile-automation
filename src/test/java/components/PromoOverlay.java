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

    private final By promoOverlay =
            AppiumBy.accessibilityId("Atsisakyti");

    private final By promoCloseButton =
            AppiumBy.accessibilityId("Uždaryti");

    public void dismissIfDisplayed() {
        WebDriverWait promoWait =
                new WebDriverWait(driver, PROMO_WAIT);

        try {
            promoWait.until(
                    ExpectedConditions.visibilityOfElementLocated(promoOverlay)
            );
        } catch (TimeoutException ignored) {
            return;
        }

        try {
            promoWait.until(
                    ExpectedConditions.visibilityOfElementLocated(promoCloseButton)
            );

            clickOnElement(promoCloseButton);

            promoWait.until(
                    ExpectedConditions.invisibilityOfElementLocated(promoOverlay)
            );

        } catch (TimeoutException e) {
            throw new IllegalStateException(
                    "Promo overlay was detected but did not close",
                    e
            );
        }
    }
}