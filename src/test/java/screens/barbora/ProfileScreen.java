package screens.barbora;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import screens.Common;

public class ProfileScreen extends Common {

    private final By loginScreenElement =
            AppiumBy.accessibilityId("Profilis");

    public boolean isLoginDisplayed() {
        return isElementDisplayed(loginScreenElement);
    }
}
