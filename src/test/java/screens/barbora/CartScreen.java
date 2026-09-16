package screens.barbora;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import screens.Common;
import utils.UiSelectorUtils;

public class CartScreen extends Common {

    private final By cartTitle = AppiumBy.accessibilityId("Krepšelis");
    private final By emptyCartTitle = AppiumBy.accessibilityId("Tavo krepšelis tuščias");
    private final By cleanCart = AppiumBy.accessibilityId("Išvalyti krepšelį");
    private final By cleanCartConfirmation = AppiumBy.accessibilityId("Pašalinti");
    private final By increaseButton = AppiumBy.accessibilityId("Didinti kiekį");
    private final By amountText = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionMatches(\"[0-9]+ vnt[.]\")");
    private final By removeItem = AppiumBy.accessibilityId("Pašalinti iš krepšelio");


    public boolean isDisplayed() {
        return isElementDisplayed(cartTitle);
    }

    public boolean containsProduct(String productName) {
        By product = AppiumBy.androidUIAutomator(
                "new UiSelector().descriptionContains(\"" +
                        UiSelectorUtils.escapeText(productName) +
                        "\")"
        );

        return isElementDisplayed(product);
    }

    public boolean isCartEmpty() {
        return isElementDisplayed(emptyCartTitle);
    }

    public void removeAllProducts() {
        clickOnElement(cleanCart);
        clickOnElement(cleanCartConfirmation);
    }

    private WebElement getProductCard(String productName) {

        By productTitle = AppiumBy.androidUIAutomator(
                "new UiSelector().descriptionContains(\"" +
                        UiSelectorUtils.escapeText(productName) +
                        "\")"
        );
        return getParentElement(productTitle);
    }

    public void increaseProductQuantity(String productName) {
        WebElement productCard = getProductCard(productName);

        productCard.findElement(increaseButton).click();
    }

    public int getProductQuantity(String productName) {
        WebElement productCard = getProductCard(productName);
        String text = productCard.findElement(amountText).getAttribute("content-desc");
        return Integer.parseInt(text.split(" ")[0]);
    }

    public void removeProduct(String productName) {
        WebElement productCard = getProductCard(productName);
        productCard.findElement(removeItem).click();
    }
}
