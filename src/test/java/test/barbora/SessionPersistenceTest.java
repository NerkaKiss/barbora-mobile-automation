package test.barbora;

import components.BottomNavigation;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import screens.barbora.LoginScreen;
import test.TestBase;
import utils.AppManager;
import utils.EnvReader;

public class SessionPersistenceTest extends TestBase {
    @BeforeMethod(alwaysRun = true)
    public void prepareLoggedInState() {

        BottomNavigation navigation = new BottomNavigation();
        LoginScreen loginScreen = new LoginScreen();

        navigation.openProfile();

        if (!loginScreen.isUserLoggedIn()) {
            loginScreen.login(
                    EnvReader.getRequired("BARBORA_LOGIN_EMAIL"),
                    EnvReader.getRequired("BARBORA_LOGIN_PASSWORD")
            );
        }

        navigation.openHome();
    }

    @Test(groups = "regression")
    public void shouldKeepUserLoggedInAfterAppRestart() {

        BottomNavigation navigation = new BottomNavigation();
        LoginScreen loginScreen = new LoginScreen();

        navigation.openProfile();

        Assert.assertTrue(
                loginScreen.isUserLoggedIn(),
                "User should be logged in before app restart"
        );

        AppManager.restartApp();

        navigation.openProfile();

        Assert.assertTrue(
                loginScreen.isUserLoggedIn(),
                "User should remain logged in after app restart"
        );
    }
}
