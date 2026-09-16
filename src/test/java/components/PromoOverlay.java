package components;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import screens.Common;

import java.util.Map;

public class PromoOverlay extends Common {

    private final By promoCloseButton = AppiumBy.xpath(
            "//android.view.View[@content-desc='Atsisakyti' "
                    + "and @dismissable='true']"
                    + "//android.widget.Button[@content-desc='Uždaryti']"
    );

    public void dismissIfDisplayed() {
        for (WebElement closeButton : driver.findElements(promoCloseButton)) {
            if (!closeButton.isDisplayed()) {
                continue;
            }

            tap(closeButton);
            return;
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
