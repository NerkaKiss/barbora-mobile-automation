package test.barbora;

import org.testng.Assert;
import org.testng.annotations.Test;
import screens.barbora.HomeScreen;
import test.TestBase;

public class HomeTest extends TestBase {
    @Test(groups = "smoke")
    public void homeScreenShouldBeDisplayed(){
        HomeScreen homeScreen = new HomeScreen();
        Assert.assertTrue(homeScreen.isDisplayed(), "Home screen should be displayed after app launch");
    }
}
