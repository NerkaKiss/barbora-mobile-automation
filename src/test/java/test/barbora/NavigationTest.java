package test.barbora;

import components.BottomNavigation;
import org.testng.Assert;
import org.testng.annotations.Test;
import screens.barbora.CartScreen;
import screens.barbora.ProductsScreen;
import test.TestBase;

public class NavigationTest extends TestBase {

    @Test(groups = "smoke")
    public void shouldNavigateToProductsScreen() {

        BottomNavigation navigation = new BottomNavigation();
        ProductsScreen productsScreen = new ProductsScreen();

        navigation.openProducts();

        Assert.assertTrue(
                productsScreen.isDisplayed(),
                "Products screen should be displayed"
        );
    }

    @Test(groups = "smoke")
    public void shouldNavigateToCartScreen() {

        BottomNavigation navigation = new BottomNavigation();
        CartScreen cartScreen = new CartScreen();

        navigation.openCart();

        Assert.assertTrue(
                cartScreen.isDisplayed(),
                "Cart screen should be displayed"
        );
    }
}