package utils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.net.MalformedURLException;
import java.net.URI;

public final class Driver {

    private static AndroidDriver driver;

    private Driver() {
    }

    public static void startDriver() {

        UiAutomator2Options options = new UiAutomator2Options()
                .setUdid(ConfigReader.get("android.udid"))
                .setPlatformName(ConfigReader.get("android.platform.name"))
                .setAppPackage(ConfigReader.get("app.package"))
                .setAppActivity(ConfigReader.get("app.activity"))
                .setNoReset(ConfigReader.getBoolean("app.noReset"));
        options.setCapability(
                "appium:forceAppLaunch",
                ConfigReader.getBoolean("app.forceLaunch"));
        options.setCapability(
                "appium:settings[limitXPathContextScope]",
                false
        );

        try {
            driver = new AndroidDriver(
                    URI.create(
                            ConfigReader.get("appium.server.url")
                    ).toURL(),
                    options
            );
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(
                    "Invalid Appium server URL: "
                            + ConfigReader.get("appium.server.url"),
                    e
            );
        }
    }

    public static AndroidDriver getDriver() {

        if (driver == null) {
            throw new IllegalStateException(
                    "Driver has not been initialized"
            );
        }

        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                System.err.println(
                        "Failed to quit driver: " + e.getMessage()
                );
            } finally {
                driver = null;
            }
        }
    }
}