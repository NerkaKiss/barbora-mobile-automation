package components;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import screens.Common;

import java.time.Duration;
import java.util.Map;

public class PromoOverlay extends Common {

    private final By promoCloseButton = AppiumBy.xpath(
            "//android.view.View[@content-desc='Atsisakyti' "
                    + "and @dismissable='true']"
                    + "//android.widget.Button[@content-desc='Uždaryti']"
    );

    private static final Duration PROMO_WAIT = Duration.ofSeconds(3);

    public void dismissIfDisplayed() {
        try {
            WebElement closeButton = new WebDriverWait(driver, PROMO_WAIT)
                    .until(d -> d.findElements(promoCloseButton)
                            .stream()
                            .filter(WebElement::isDisplayed)
                            .filter(WebElement::isEnabled)
                            .findFirst()
                            .orElse(null));

            tap(closeButton);

        } catch (TimeoutException ignored) {
            // Promo overlay did not appear
        }
    }

    private void tap(WebElement element) {
        try {
            element.click();
        } catch (Exception e) {
            Rectangle rect = element.getRect();
            driver.executeScript(
                    "mobile: clickGesture",
                    Map.of(
                            "x", rect.getX() + rect.getWidth() / 2,
                            "y", rect.getY() + rect.getHeight() / 2
                    )
            );
        }
    }
}
