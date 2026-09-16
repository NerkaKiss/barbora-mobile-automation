package test;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.Driver;
import utils.ScreenshotRecorder;
import utils.VideoRecorder;

public abstract class TestBase {

    @BeforeMethod
    public void setUp() {
        Driver.startDriver();
        VideoRecorder.startRecording();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {

        if (result.getStatus() == ITestResult.FAILURE) {
            ScreenshotRecorder.captureAndAttach(result.getName());
            VideoRecorder.stopAndSave(result.getName());
        } else {
            VideoRecorder.stopAndDiscard();
        }

        Driver.quitDriver();
    }
}
