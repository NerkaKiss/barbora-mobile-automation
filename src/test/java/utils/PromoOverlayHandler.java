package utils;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public final class PromoOverlayHandler {

    private static final By OVERLAY =
            AppiumBy.accessibilityId("Atsisakyti");

    private static final By CLOSE_BUTTON =
            AppiumBy.accessibilityId("Uždaryti");

    private PromoOverlayHandler() {
    }

    public static boolean dismissIfPresent(AndroidDriver driver) {
        List<WebElement> overlays = driver.findElements(OVERLAY);

        if (overlays.isEmpty()) {
            return false;
        }

        List<WebElement> closeButtons = driver.findElements(CLOSE_BUTTON);

        if (closeButtons.isEmpty()) {
            return false;
        }

        closeButtons.getFirst().click();
        return true;
    }
}