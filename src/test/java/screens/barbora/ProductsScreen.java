package screens.barbora;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import screens.Common;

public class ProductsScreen extends Common {
    private final By productCategories =
            AppiumBy.accessibilityId("Prekių kategorijos");

    public boolean isDisplayed() {
        return isElementDisplayed(productCategories);
    }
}
