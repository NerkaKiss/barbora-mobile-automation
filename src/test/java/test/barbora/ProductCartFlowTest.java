package test.barbora;

import components.BottomNavigation;
import components.SearchBar;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import screens.barbora.CartScreen;
import screens.barbora.ProductDetailsScreen;
import screens.barbora.SearchScreen;
import state.ProductCartTestState;
import test.TestBase;
import utils.AppManager;
import utils.Driver;

public class ProductCartFlowTest extends TestBase {

    private ProductCartTestState testState;

    @BeforeMethod(alwaysRun = true)
    public void prepareTestState() {
        testState = new ProductCartTestState();
        testState.prepareLoggedInWithEmptyCart();
    }

    @AfterMethod(alwaysRun = true)
    public void cleanCartState(ITestResult result) {
        if (!Driver.isInitialized() || !result.isSuccess()) {
            return;
        }

        testState.ensureCartIsEmpty();
    }

    private String addSearchedProductToCart(String query) {

        SearchBar searchBar = new SearchBar();
        SearchScreen searchScreen = new SearchScreen();
        ProductDetailsScreen productDetailsScreen = new ProductDetailsScreen();
        BottomNavigation navigation = new BottomNavigation();

        searchBar.enterSearchQuery("pienas");
        searchBar.submitSearch();

        searchScreen.openFirstProduct(query);

        String productName = productDetailsScreen.getProductName(query);

        productDetailsScreen.addProductToCart();

        navigation.openCart();

        return productName;
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

    @Test(groups = "regression")
    public void shouldIncreaseProductQuantityInCart() {

        CartScreen cartScreen = new CartScreen();

        String productName = addSearchedProductToCart("pienas");

        int initialQuantity = cartScreen.getProductQuantity(productName);

        cartScreen.increaseProductQuantity(productName);

        Assert.assertEquals(
                cartScreen.getProductQuantity(productName),
                initialQuantity + 1,
                "Product quantity should increase by one in cart: " + productName
        );
    }

    @Test(groups = "regression")
    public void shouldKeepProductInCartAfterNavigation() {

        BottomNavigation navigation = new BottomNavigation();
        CartScreen cartScreen = new CartScreen();

        String productName = addSearchedProductToCart("pienas");

        navigation.openHome();
        navigation.openProducts();
        navigation.openCart();

        Assert.assertTrue(
                cartScreen.containsProduct(productName),
                "Product should remain in cart after navigation: " + productName
        );
    }

    @Test(groups = "regression")
    public void shouldRemoveProductFromCart() {

        CartScreen cartScreen = new CartScreen();

        String productName = addSearchedProductToCart("pienas");

        cartScreen.removeProduct(productName);

        Assert.assertTrue(
                cartScreen.isCartEmpty(),
                "Cart should be empty after removing product: " + productName
        );
    }

    @Test(groups = "regression")
    public void shouldKeepProductInCartAfterAppRestart() {

        BottomNavigation navigation = new BottomNavigation();
        CartScreen cartScreen = new CartScreen();

        String productName = addSearchedProductToCart("pienas");

        AppManager.restartApp();

        navigation.openCart();

        Assert.assertTrue(
                cartScreen.containsProduct(productName),
                "Product should remain in cart after app restart: " + productName
        );
    }
}
