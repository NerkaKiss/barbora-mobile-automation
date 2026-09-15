package test.barbora;

import components.BottomNavigation;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import screens.barbora.LoginScreen;
import test.TestBase;
import testdata.LoginTestData;
import utils.EnvReader;
import utils.JsonDataReader;

public class LoginTest extends TestBase {

    private void ensureLoggedOut() {
        BottomNavigation navigation = new BottomNavigation();
        LoginScreen loginScreen = new LoginScreen();

        navigation.openProfile();

        if (loginScreen.isUserLoggedIn()) {
            loginScreen.logout();
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void prepareLoginState() {
        ensureLoggedOut();
    }

    @AfterMethod(alwaysRun = true)
    public void cleanLoginState() {
        ensureLoggedOut();
    }

    @Test(groups = "smoke")
    public void shouldLoginSuccessfully() {

        BottomNavigation navigation = new BottomNavigation();
        LoginScreen loginScreen = new LoginScreen();

        navigation.openProfile();
        loginScreen.login(
                EnvReader.getRequired("BARBORA_LOGIN_EMAIL"),
                EnvReader.getRequired("BARBORA_LOGIN_PASSWORD")
        );

        Assert.assertTrue(
                loginScreen.isUserLoggedIn(),
                "User should be logged in after entering valid credentials"
        );
    }

    @Test(
            groups = "regression",
            dataProvider = "invalidCredentials"
    )
    public void loginShouldFailWithInvalidCredentials(LoginTestData data) {

        BottomNavigation navigation = new BottomNavigation();
        LoginScreen loginScreen = new LoginScreen();

        navigation.openProfile();

        loginScreen.login(
                data.email(),
                data.password()
        );

        Assert.assertTrue(
                loginScreen.isLoginErrorDisplayed(),
                "Login should fail for scenario: " + data.scenario()
        );
    }

    @DataProvider(name = "invalidCredentials")
    public Object[][] invalidCredentials() {
        return JsonDataReader.readAsDataProvider(
                "testdata/invalid-login.json",
                LoginTestData.class
        );
    }
}
