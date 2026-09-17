package utils;

import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

public final class VideoRecorder {

    private static final Path VIDEO_DIRECTORY = Path.of("videos");
    private static boolean recordingStarted = false;

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss-SSS");

    private VideoRecorder() {
    }

    public static void startRecording() {
        if (!Driver.isInitialized()) {
            recordingStarted = false;
            return;
        }

        try {
            Driver.getDriver().startRecordingScreen();
            recordingStarted = true;
        } catch (Exception e) {
            recordingStarted = false;
            System.err.println(
                    "Failed to start video recording: " + e.getMessage()
            );
        }
    }

    public static boolean isRecordingStarted() {
        return recordingStarted;
    }

    public static void stopAndSave(String testName) {
        if (!recordingStarted || !Driver.isInitialized()) {
            recordingStarted = false;
            return;
        }

        recordingStarted = false;

        try {
            String base64Video =
                    Driver.getDriver().stopRecordingScreen();

            byte[] videoBytes =
                    Base64.getDecoder().decode(base64Video);

            Files.createDirectories(VIDEO_DIRECTORY);

            String timestamp =
                    LocalDateTime.now().format(DATE_FORMAT);

            Path destination = VIDEO_DIRECTORY.resolve(
                    "%s_%s.mp4".formatted(timestamp, testName)
            );

            Files.write(destination, videoBytes);

            Allure.addAttachment(
                    "Video on failure",
                    "video/mp4",
                    new ByteArrayInputStream(videoBytes),
                    ".mp4"
            );

        } catch (Exception e) {
            System.err.println(
                    "Failed to save video for test '%s': %s"
                            .formatted(testName, e.getMessage())
            );
        }
    }

    public static void stopAndDiscard() {
        if (!recordingStarted || !Driver.isInitialized()) {
            recordingStarted = false;
            return;
        }

        recordingStarted = false;

        try {
            Driver.getDriver().stopRecordingScreen();
        } catch (Exception e) {
            System.err.println(
                    "Failed to stop video recording: " + e.getMessage()
            );
        }
    }
}
