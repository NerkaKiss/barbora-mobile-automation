package utils;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ScreenshotRecorder {

    private static final Path SCREENSHOT_DIRECTORY = Path.of("screenshots");

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss-SSS");

    private ScreenshotRecorder() {
    }

    public static void captureAndAttach(String testName) {

        try {
            if (!(Driver.getDriver() instanceof TakesScreenshot screenshot)) {
                return;
            }

            byte[] screenshotBytes =
                    screenshot.getScreenshotAs(OutputType.BYTES);

            Files.createDirectories(SCREENSHOT_DIRECTORY);

            String timestamp =
                    LocalDateTime.now().format(DATE_FORMAT);

            String fileName = "%s_%s_screenshot.png"
                    .formatted(timestamp, testName);

            Files.write(
                    SCREENSHOT_DIRECTORY.resolve(fileName),
                    screenshotBytes
            );

            Allure.addAttachment(
                    "Screenshot on failure",
                    "image/png",
                    new ByteArrayInputStream(screenshotBytes),
                    ".png"
            );

        } catch (Exception e) {
            System.err.println(
                    "Failed to capture screenshot for test '%s': %s"
                            .formatted(testName, e.getMessage())
            );
        }
    }
}
