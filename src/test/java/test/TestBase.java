package test;

import components.CookieBanner;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.Driver;
import utils.PageSourceRecorder;
import utils.ScreenshotRecorder;
import utils.VideoRecorder;

public abstract class TestBase {

    private static boolean startupStateHandled = false;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        try {
            Driver.startDriver();
            VideoRecorder.startRecording();

            if (!startupStateHandled) {
                new CookieBanner().acceptIfDisplayed();
                startupStateHandled = true;
            }
        } catch (Exception e) {
            attachFailureArtifacts("setup");
            throw e;
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {

        try {
            if (!result.isSuccess()) {
                attachFailureArtifacts(result.getName());
            } else {
                VideoRecorder.stopAndDiscard();
            }
        } finally {
            Driver.quitDriver();
        }
    }

    private void attachFailureArtifacts(String name) {
        ScreenshotRecorder.captureAndAttach(name);
        PageSourceRecorder.captureAndAttach(name);

        if (VideoRecorder.isRecordingStarted()) {
            VideoRecorder.stopAndSave(name);
        }
    }
}
