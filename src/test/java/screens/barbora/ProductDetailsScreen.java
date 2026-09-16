package screens.barbora;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import screens.Common;
import utils.UiSelectorUtils;

public class ProductDetailsScreen extends Common {

    private final By addToCartButton =
            AppiumBy.accessibilityId("Į krepšelį");

    public void addProductToCart() {
        clickOnElement(addToCartButton);
    }

    public String getProductName(String productName) {
        By product = AppiumBy.androidUIAutomator(
                "new UiSelector()" +
                        ".className(\"android.view.View\")" +
                        ".descriptionMatches(\"(?i).*" +
                        UiSelectorUtils.escapeRegexLiteral(productName) +
                        ".*\")"
        );
        return getContentDescription(product);
    }
}
