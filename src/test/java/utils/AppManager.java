package utils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.appmanagement.ApplicationState;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public final class AppManager {

    private static final Duration APP_START_TIMEOUT = Duration.ofSeconds(10);

    private AppManager() {
    }

    public static void terminateApp() {
        Driver.getDriver()
                .terminateApp(ConfigReader.get("app.package"));
    }

    public static void activateApp() {
        AndroidDriver driver = Driver.getDriver();
        String appPackage = ConfigReader.get("app.package");

        driver.activateApp(appPackage);

        new WebDriverWait(driver, APP_START_TIMEOUT).until(
                ignored -> driver.queryAppState(appPackage)
                        == ApplicationState.RUNNING_IN_FOREGROUND
        );
    }

    public static void restartApp() {
        terminateApp();
        activateApp();
    }

    public static boolean isAppInstalled() {
        return Driver.getDriver()
                .isAppInstalled(ConfigReader.get("app.package"));
    }
}