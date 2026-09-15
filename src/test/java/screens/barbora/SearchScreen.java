package screens.barbora;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import screens.Common;

public class SearchScreen extends Common {
    private final By firstAvailableProduct =
            AppiumBy.accessibilityId("TODO: replace with first available product card accessibility id");
    private final By firstAvailableProductName =
            AppiumBy.accessibilityId("TODO: replace with first available product name accessibility id");

    public boolean areResultsDisplayed(String query) {
        By searchQuery = AppiumBy.accessibilityId(query);
        if (!isElementDisplayed(searchQuery)) {
            return false;
        }
        By results = AppiumBy.androidUIAutomator(
                "new UiSelector().descriptionMatches(\"(?i).*" + query + ".*\")"
        );
        return areElementsDisplayed(results);
    }

    public void openFirstProduct(String query) {
        By products = AppiumBy.androidUIAutomator(
                "new UiSelector()" +
                        ".className(\"android.widget.ImageView\")" +
                        ".descriptionMatches(\"(?i).*" + query + ".*\")"
        );

        clickNthElement(products, 0);
    }
}
