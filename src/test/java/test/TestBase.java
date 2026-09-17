package test;

import components.CookieBanner;
import components.PromoOverlay;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import utils.Driver;
import utils.PageSourceRecorder;
import utils.ScreenshotRecorder;
import utils.VideoRecorder;

public abstract class TestBase {

    private static boolean startupStateHandled = false;

    @BeforeClass(alwaysRun = true)
    public void startDriverSession() {
        Driver.startDriver();
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        try {
            if (!startupStateHandled) {
                new CookieBanner().acceptIfDisplayed();
                startupStateHandled = true;
            }

            VideoRecorder.startRecording();

        } catch (Exception e) {
            attachFailureArtifacts("setup");
            throw e;
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            attachFailureArtifacts(result.getName());
        } else {
            VideoRecorder.stopAndDiscard();
        }
    }

    @AfterClass(alwaysRun = true)
    public void stopDriverSession() {
        Driver.quitDriver();
    }

    private void attachFailureArtifacts(String name) {
        ScreenshotRecorder.captureAndAttach(name);
        PageSourceRecorder.captureAndAttach(name);

        if (VideoRecorder.isRecordingStarted()) {
            VideoRecorder.stopAndSave(name);
        }
    }
}