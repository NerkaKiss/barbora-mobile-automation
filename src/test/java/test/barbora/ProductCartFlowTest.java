package test.barbora;

import components.BottomNavigation;
import components.SearchBar;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import screens.barbora.CartScreen;
import screens.barbora.LoginScreen;
import screens.barbora.ProductDetailsScreen;
import screens.barbora.SearchScreen;
import test.TestBase;
import utils.EnvReader;

public class ProductCartFlowTest extends TestBase {

    private void ensureUserIsLoggedIn() {
        BottomNavigation navigation = new BottomNavigation();
        LoginScreen loginScreen = new LoginScreen();

        navigation.openProfile();

        if (!loginScreen.isUserLoggedIn()) {
            loginScreen.login(
                    EnvReader.getRequired("BARBORA_LOGIN_EMAIL"),
                    EnvReader.getRequired("BARBORA_LOGIN_PASSWORD")
            );
        }
    }

    private void ensureCartIsEmpty() {
        BottomNavigation navigation = new BottomNavigation();
        CartScreen cartScreen = new CartScreen();

        navigation.openCart();

        if (!cartScreen.isCartEmpty()) {
            cartScreen.removeAllProducts();
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void prepareTestState() {
        BottomNavigation navigation = new BottomNavigation();

        ensureUserIsLoggedIn();
        ensureCartIsEmpty();

        navigation.openHome();
    }

    @AfterMethod(alwaysRun = true)
    public void cleanCartState() {
        ensureCartIsEmpty();
    }

    @Test(groups = "smoke")
    public void shouldAddSearchedProductToCart() {

        SearchBar searchBar = new SearchBar();
        SearchScreen searchScreen = new SearchScreen();
        ProductDetailsScreen productDetailsScreen = new ProductDetailsScreen();
        BottomNavigation navigation = new BottomNavigation();
        CartScreen cartScreen = new CartScreen();

        String query = "pienas";

        searchBar.enterSearchQuery(query);
        searchBar.submitSearch();

        searchScreen.openFirstProduct(query);

        String productName = productDetailsScreen.getProductName(query);

        productDetailsScreen.addProductToCart();

        navigation.openCart();

        Assert.assertTrue(
                cartScreen.containsProduct(productName),
                "Cart should contain added product: " + productName
        );
    }
}