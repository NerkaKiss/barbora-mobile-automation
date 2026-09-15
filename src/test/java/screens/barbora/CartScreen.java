package screens.barbora;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import screens.Common;

public class CartScreen extends Common {

    private final By cartTitle = AppiumBy.accessibilityId("Krepšelis");

    public boolean isDisplayed() {
        return isElementDisplayed(cartTitle);
    }
}
