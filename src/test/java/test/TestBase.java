package test;

import components.CookieBanner;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.Driver;
import utils.ScreenshotRecorder;
import utils.VideoRecorder;

public abstract class TestBase {

    private static boolean startupStateHandled = false;

    @BeforeMethod
    public void setUp() {
        Driver.startDriver();
        if (!startupStateHandled) {
            new CookieBanner().acceptIfDisplayed();
            startupStateHandled = true;
        }
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
