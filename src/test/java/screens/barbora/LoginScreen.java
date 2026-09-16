package screens.barbora;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import screens.Common;

public class LoginScreen extends Common {

    private final By emailInput =
            AppiumBy.androidUIAutomator(
                    "new UiSelector().resourceId(\"textLabel[<'loginPageEmailTextField'>]\")");
    private final By passwordInput =
            AppiumBy.androidUIAutomator(
                    "new UiSelector().resourceId(\"textLabel[<'loginPagePasswordTextField'>]\")");
    private final By loginButton =
            AppiumBy.accessibilityId("Prisijungti su el. paštu");
    private final By logoutButton =
            AppiumBy.accessibilityId("Atsijungti");
    private final By loginError =
            AppiumBy.accessibilityId("Neteisingi prisijungimo duomenys!");

    public void login(String email, String password) {
        clickOnElement(emailInput);
        sendKeysActiveElement(email);

        if (isInputEmpty(emailInput)) {
            clickOnElement(emailInput);
            sendKeysActiveElement(email);
        }

        if (isInputEmpty(emailInput)) {
            throw new IllegalStateException("Email was not entered");
        }

        clickOnElement(passwordInput);
        sendKeysActiveElement(password);

        if (isInputEmpty(passwordInput)) {
            clickOnElement(passwordInput);
            sendKeysActiveElement(password);
        }

        if (isInputEmpty(passwordInput)) {
            throw new IllegalStateException("Password was not entered");
        }

        clickOnElement(loginButton);
    }

    private boolean isInputEmpty(By input) {
        String text = driver.findElement(input).getText();
        return text == null || text.isBlank();
    }

    public boolean isUserLoggedIn() {
        return isElementDisplayed(logoutButton);
    }

    public boolean isLoginErrorDisplayed() {
        return isElementDisplayed(loginError);
    }

    public void logout() {
        clickOnElement(logoutButton);
        clickOnElement(logoutButton);
        waitForElement(loginButton);
    }
}
