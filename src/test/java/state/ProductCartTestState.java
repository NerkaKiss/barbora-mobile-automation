package state;

import components.BottomNavigation;
import components.PromoOverlay;
import screens.barbora.CartScreen;
import screens.barbora.LoginScreen;
import screens.barbora.ProfileScreen;
import utils.EnvReader;

public class ProductCartTestState {

    private final BottomNavigation navigation = new BottomNavigation();
    private final ProfileScreen profileScreen = new ProfileScreen();
    private final LoginScreen loginScreen = new LoginScreen();
    private final CartScreen cartScreen = new CartScreen();

    public void prepareLoggedInWithEmptyCart() {
        ensureUserIsLoggedIn();
        ensureCartIsEmpty();
        navigation.openHome();
    }

    public void ensureUserIsLoggedIn() {
        navigation.openProfile();

        if (!profileScreen.isProfileDisplayed()) {
            throw new IllegalStateException("Profile screen did not open after tapping Profile");
        }

        new PromoOverlay().dismissIfDisplayed();

        if (!loginScreen.isUserLoggedIn()) {
            loginScreen.login(
                    EnvReader.getRequired("BARBORA_LOGIN_EMAIL"),
                    EnvReader.getRequired("BARBORA_LOGIN_PASSWORD")
            );

            if (!loginScreen.isUserLoggedIn()) {
                throw new IllegalStateException("User should be logged in before cart flow setup");
            }
        }
    }

    public void ensureCartIsEmpty() {
        navigation.openCart();

        if (!cartScreen.isCartEmpty()) {
            cartScreen.removeAllProducts();
        }
    }
}
