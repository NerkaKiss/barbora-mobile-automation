package components;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import screens.Common;

public class BottomNavigation extends Common {
    private final By productsTab =
            AppiumBy.androidUIAutomator(
                    "new UiSelector().descriptionStartsWith(\"Prekės\")"
            );
    private final By cartTab =
            AppiumBy.androidUIAutomator(
                    "new UiSelector().descriptionMatches(\"(?i).*Krepšelis.*\")"
            );
    private final By profileTab =
            AppiumBy.androidUIAutomator(
                    "new UiSelector().descriptionStartsWith(\"Profilis\")"
            );
    private final By homeTab = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionStartsWith(\"Pradžia\")"
    );

    public void openProducts() {
        new PromoOverlay().dismissIfDisplayed();
        clickOnElement(productsTab);
    }

    public void openCart() {
        new PromoOverlay().dismissIfDisplayed();
        clickOnElement(cartTab);
    }

    public void openProfile() {
        new PromoOverlay().dismissIfDisplayed();
        clickOnElement(profileTab);
    }

    public void openHome() {
        new PromoOverlay().dismissIfDisplayed();
        clickOnElement(homeTab);
    }
}
