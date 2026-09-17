package utils;

import org.testng.IConfigurationListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener, IConfigurationListener {

    @Override
    public void onConfigurationFailure(ITestResult result) {
        String name = result.getName();

        captureSafely(() -> ScreenshotRecorder.captureAndAttach(name));
        captureSafely(() -> PageSourceRecorder.captureAndAttach(name));

        if (VideoRecorder.isRecordingStarted()) {
            captureSafely(() -> VideoRecorder.stopAndSave(name));
        }
    }

    private void captureSafely(Runnable capture) {
        try {
            capture.run();
        } catch (Exception e) {
            System.err.println(
                    "Failed to collect configuration failure artifact: "
                            + e.getMessage()
            );
        }
    }
}
