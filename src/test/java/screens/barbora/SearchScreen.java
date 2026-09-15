package screens.barbora;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import screens.Common;

public class SearchScreen extends Common {
    public boolean areResultsDisplayed(String query) {
        By searchQuery = AppiumBy.accessibilityId(query);
        if (!isElementDisplayed(searchQuery)) {
            return false;
        }
        By results = AppiumBy.androidUIAutomator(
                "new UiSelector().descriptionMatches(\"(?i).*" + query + ".*\")"
        );
        return !getElements(results).isEmpty();
    }
}
