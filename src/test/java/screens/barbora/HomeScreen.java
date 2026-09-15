package screens.barbora;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import screens.Common;
import utils.Driver;

import java.time.Duration;

public class HomeScreen extends Common {

    private final By homeTab = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionStartsWith(\"Pradžia\")"
    );

    public boolean isDisplayed() {
        return isElementDisplayed(homeTab);
        }
}
