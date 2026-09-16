package screens.barbora;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import screens.Common;
import utils.UiSelectorUtils;

public class SearchScreen extends Common {

    public boolean areResultsDisplayed(String query) {
        By results = AppiumBy.androidUIAutomator(
                "new UiSelector().descriptionMatches(\"(?i).*" +
                        UiSelectorUtils.escapeRegexLiteral(query) +
                        ".*\")"
        );
        return areElementsDisplayed(results);
    }

    public void openFirstProduct(String query) {
        By products = AppiumBy.androidUIAutomator(
                "new UiSelector()" +
                        ".className(\"android.widget.ImageView\")" +
                        ".descriptionMatches(\"(?i).*" +
                        UiSelectorUtils.escapeRegexLiteral(query) +
                        ".*\")"
        );

        clickNthElement(products, 0);
    }
}
