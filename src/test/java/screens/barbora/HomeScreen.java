package screens.barbora;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import screens.Common;

public class HomeScreen extends Common {

    private final By homeTab = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionStartsWith(\"Pradžia\")"
    );

    public boolean isDisplayed() {
        return isElementDisplayed(homeTab);
    }
}
