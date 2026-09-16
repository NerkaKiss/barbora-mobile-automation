package components;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import screens.Common;
import utils.UiSelectorUtils;

public class SearchBar extends Common {
    private final By searchInput =
            AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\")");

    public void enterSearchQuery(String productName) {
        clickOnElement(searchInput);
        sendKeysToElement(searchInput, productName);
    }

    public String getSearchQuery() {
        return getTextFromElement(searchInput);
    }

    public boolean areSuggestionsDisplayed(String productName) {
        By suggestions = AppiumBy.androidUIAutomator(
                "new UiSelector().descriptionMatches(\"(?i).*" +
                        UiSelectorUtils.escapeRegexLiteral(productName) +
                        ".*\")"
        );

        return areElementsDisplayed(suggestions);
    }

    public void submitSearch() {
        performEditorAction("search");
    }
}
