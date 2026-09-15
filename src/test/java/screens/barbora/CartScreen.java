package screens.barbora;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import screens.Common;

public class CartScreen extends Common {

    private final By cartTitle = AppiumBy.accessibilityId("Krepšelis");
    private final By emptyCartTitle = AppiumBy.accessibilityId("Tavo krepšelis tuščias");
    private final By cleanCart = AppiumBy.accessibilityId("Išvalyti krepšelį");
    private final By cleanCartConfirmation = AppiumBy.accessibilityId("Pašalinti");


    public boolean isDisplayed() {
        return isElementDisplayed(cartTitle);
    }

    public boolean containsProduct(String productName) {
        By product = AppiumBy.androidUIAutomator(
                "new UiSelector().descriptionContains(\"" + productName + "\")"
        );

        return isElementDisplayed(product);
    }

    public boolean isCartEmpty() {
        return isElementDisplayed(emptyCartTitle);
    }

    public void removeAllProducts() {
        clickOnElement(cleanCart);
        clickOnElement(cleanCartConfirmation);
    }
}
