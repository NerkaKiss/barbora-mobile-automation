# BARBORA Mobile Appium Automation Test Suite

End-to-end mobile test automation for the BARBORA Android application. Built as a QA Automation portfolio project with Java, Appium, TestNG, Maven, GitHub Actions, and Allure reporting.

![Java](https://img.shields.io/badge/Java-21+-007396?logo=openjdk&logoColor=white)
![Appium](https://img.shields.io/badge/Appium-Mobile_Automation-662d91?logo=appium&logoColor=white)
![TestNG](https://img.shields.io/badge/TestNG-Test_Framework-f2c811)
![Maven](https://img.shields.io/badge/Maven-Build_Tool-c71a36?logo=apachemaven&logoColor=white)
![Allure](https://img.shields.io/badge/Allure-Reports-orange)
[![BARBORA Mobile Tests](https://github.com/NerkaKiss/barbora-mobile-automation/actions/workflows/mobile-tests.yml/badge.svg)](https://github.com/NerkaKiss/barbora-mobile-automation/actions/workflows/mobile-tests.yml)

> This suite runs against a live mobile application backed by real external services. Occasional failures may happen due to application availability, slow page loads, backend responses, or UI changes rather than framework issues. Automatic test retries are intentionally not used, so instability stays visible and is supported by failure artifacts.

---

## About This Project

This project demonstrates practical Android mobile automation against a real e-commerce application. It covers app launch, bottom navigation, login, invalid login validation, search, product details, cart operations, and session persistence after app restart.

The focus is on maintainable QA automation architecture: screen objects, reusable components, TestNG groups, JSON-driven test data, environment-based credentials, CI execution on an Android emulator, and useful failure diagnostics through screenshots, videos, page source files, Appium logs, Surefire reports, and Allure results.

---

## Testing Strategy

- **Smoke suite** - critical checks for app launch, navigation, search input, search suggestions, valid login, and adding a searched product to cart.
- **Regression suite** - broader coverage for submitted search results, invalid login data, cart quantity changes, cart persistence, product removal, and session persistence.
- **Data-driven login tests** - invalid credential scenarios are stored in JSON and supplied through a TestNG `DataProvider`.
- **State preparation** - login and cart state are prepared before cart flow tests and cleaned only after successful tests to preserve failure evidence.
- **Overlay handling** - startup cookie banner and promo overlay handling are isolated in reusable components.
- **Failure diagnostics** - failed setup or test methods capture screenshots, Android page source, video, and reporting artifacts.

---

## Responsible Testing Against a Live App

This project targets a live mobile application, so the suite is intentionally limited. The goal is to demonstrate mobile automation design and E2E testing practices without generating unnecessary traffic or performing destructive actions.

Key decisions:

- Keep the suite focused on high-value user journeys.
- Avoid placing real orders or reaching payment flows.
- Use sequential TestNG execution by default.
- Clean cart state after successful cart tests.

---

## Project Structure

```text
.
|-- .github/workflows/
|   `-- mobile-tests.yml                # GitHub Actions: manual suite selection and nightly full run
|-- src/test/java/
|   |-- components/
|   |   |-- BottomNavigation.java        # Bottom navigation actions
|   |   |-- CookieBanner.java            # Cookie banner handling
|   |   |-- PromoOverlay.java            # Promo overlay handling
|   |   `-- SearchBar.java               # Search field and suggestions
|   |-- screens/
|   |   |-- Common.java                  # Shared waits, clicks, and helper methods
|   |   `-- barbora/
|   |       |-- CartScreen.java
|   |       |-- HomeScreen.java
|   |       |-- LoginScreen.java
|   |       |-- ProductDetailsScreen.java
|   |       |-- ProductsScreen.java
|   |       |-- ProfileScreen.java
|   |       `-- SearchScreen.java
|   |-- test/
|   |   |-- TestBase.java                # Driver lifecycle, startup handling, failure artifacts
|   |   `-- barbora/
|   |       |-- HomeTest.java
|   |       |-- LoginTest.java
|   |       |-- NavigationTest.java
|   |       |-- ProductCartFlowTest.java
|   |       |-- SearchTest.java
|   |       `-- SessionPersistenceTest.java
|   |-- testdata/
|   |   `-- LoginTestData.java           # Invalid login data model
|   `-- utils/
|       |-- AppManager.java              # App restart helper
|       |-- ConfigReader.java            # config.properties reader
|       |-- Driver.java                  # AndroidDriver setup and lifecycle
|       |-- EnvReader.java               # Environment and .env credentials reader
|       |-- JsonDataReader.java          # JSON test data reader
|       |-- PageSourceRecorder.java      # Page source file and Allure attachment
|       |-- ScreenshotRecorder.java      # Screenshot file and Allure attachment
|       |-- UiSelectorUtils.java         # Android UiSelector escaping helpers
|       `-- VideoRecorder.java           # Appium screen recording handling
|-- src/test/resources/
|   |-- allure.properties
|   |-- config.properties                # Appium, device, package, and app launch config
|   `-- testdata/
|       `-- invalid-login.json           # Data-driven invalid login scenarios
|-- testng.xml                           # TestNG suite configuration
|-- pom.xml                              # Dependencies and Maven plugins
`-- README.md
```

---

## Architecture

### Screen Objects + Reusable Components + Base Test

Tests use screen objects for full-screen areas and components for shared UI elements. This keeps test classes focused on user scenarios while locators and Appium interactions stay in reusable classes.

```text
Test class
  `-- TestBase
        |-- Driver
        |-- CookieBanner / PromoOverlay
        |-- ScreenshotRecorder
        |-- PageSourceRecorder
        `-- VideoRecorder

Test flow
  |-- components
  |     |-- BottomNavigation
  |     `-- SearchBar
  `-- screens
        |-- LoginScreen
        |-- SearchScreen
        |-- ProductDetailsScreen
        `-- CartScreen
```

Key conventions:

- Screen and component classes encapsulate locators and UI actions.
- Assertions stay in test classes.
- `TestBase` owns driver startup, startup banners, video recording, and failure artifacts.
- Test data for invalid login scenarios is stored in JSON.
- Credentials are read from real environment variables first and `.env` second.
- TestNG groups control smoke, regression, and full execution.

---

## Test Coverage

| Feature | File | Groups | Coverage |
| --- | --- | --- | --- |
| App launch | `HomeTest.java` | `smoke` | Home screen visibility after app launch |
| Navigation | `NavigationTest.java` | `smoke` | Products, cart, and profile/login screen navigation |
| Search | `SearchTest.java` | `smoke`, `regression` | Search input, suggestions, and submitted search results |
| Login | `LoginTest.java` | `smoke`, `regression` | Valid login and JSON-driven invalid credential scenarios |
| Product and cart flow | `ProductCartFlowTest.java` | `smoke`, `regression` | Add product, increase quantity, persistence after navigation/restart, remove product |
| Session persistence | `SessionPersistenceTest.java` | `regression` | User remains logged in after app restart |

Current test count:

| Suite | Count |
| --- | ---: |
| All test invocations | 17 |
| Smoke | 8 |
| Regression | 9 |

Invalid login regression coverage includes 3 data-driven credential scenarios.

---

## Test Groups

| Group | Purpose |
| --- | --- |
| `smoke` | Critical app launch, navigation, login, search, and cart happy-path checks |
| `regression` | Broader functional coverage, negative login checks, cart persistence, and session persistence |

Example usage:

```bash
# Run smoke tests
mvn test -Dgroups=smoke

# Run regression tests
mvn test -Dgroups=regression

# Run the full TestNG suite
mvn test
```

---

## Running Locally

### Prerequisites

- Java 21+
- Maven 3.9+
- Android Studio or Android SDK with an emulator/device available
- Appium 3+
- Appium UiAutomator2 driver
- BARBORA Android APK installed on the emulator/device
- Allure CLI is optional because the project uses the Allure Maven plugin

### Install Appium

```bash
npm install -g appium@3
appium driver install uiautomator2
```

### Start Appium

```bash
appium --log appium.log --log-level info
```

### Environment Variables

Create a `.env` file in the project root or provide real environment variables.

```env
BARBORA_LOGIN_EMAIL=your-email@example.com
BARBORA_LOGIN_PASSWORD=your-password
```

The framework first reads real environment variables and then falls back to `.env` values.

### Device And App Configuration

Default Appium and Android settings are stored in `src/test/resources/config.properties`.

```properties
appium.server.url=http://127.0.0.1:4723
android.udid=emulator-5554
android.platform.name=Android
app.package=lt.barbora
app.activity=barbora.next.DefaultIcon
app.noReset=true
app.forceLaunch=true
```

If using a different emulator or real device, update `android.udid`.

### Run Tests

```bash
# All tests
mvn clean test

# Smoke suite
mvn clean test -Dgroups=smoke

# Regression suite
mvn clean test -Dgroups=regression

# One test class
mvn clean test -Dtest=LoginTest
```

TestNG suite configuration is stored in `testng.xml`.

---

## CI/CD

GitHub Actions workflow is configured in `.github/workflows/mobile-tests.yml`.

| Trigger | Suite | Command behavior |
| --- | --- | --- |
| Manual dispatch | `smoke`, `regression`, or `full` | Suite is selected in the GitHub Actions UI |
| Nightly schedule | Full | Runs every night at `02:00 UTC` |

Required GitHub secrets:

| Secret | Description |
| --- | --- |
| `BARBORA_LOGIN_EMAIL` | Test account email |
| `BARBORA_LOGIN_PASSWORD` | Test account password |
| `BARBORA_APK_TOKEN` | Token used by CI to download the BARBORA APK artifact |

CI behavior:

- Sets up Java and Node.js.
- Installs Appium 3 and the UiAutomator2 driver.
- Downloads the BARBORA APK release asset.
- Starts Appium before the Android emulator test step.
- Runs tests on a Pixel 7 Android emulator.
- Uploads reports and diagnostic artifacts after every run.

Uploaded artifacts:

- `target/allure-results/`
- `target/surefire-reports/`
- `screenshots/`
- `page-sources/`
- `videos/`
- `appium.log`
- `appium-console.log`

---

## Allure Reporting

Allure results are written to `target/allure-results`.

Generate a local HTML report:

```bash
mvn allure:report
```

Generated report location:

```text
target/site/allure-maven-plugin
```

Serve the report directly:

```bash
mvn allure:serve
```

Attachment policy:

- Passed tests attach nothing, and successful test videos are discarded.
- Failed tests save and attach a screenshot.
- Failed tests save and attach Android page source XML.
- Failed tests save and attach a screen recording when recording was started.
- Setup failures also attempt to attach failure artifacts.
- Screenshots are also written to `screenshots/`.
- Page sources are also written to `page-sources/`.
- Failed-test videos are written to `videos/`.

---

## Failure Diagnostics

On failed tests, the framework provides:

- Screenshot in `screenshots/`
- Android page source XML in `page-sources/`
- Screen recording in `videos/`
- Screenshot attachment in Allure
- Page source attachment in Allure
- Video attachment in Allure
- Appium server logs in CI artifacts
- Surefire reports in CI artifacts

Automatic test retries are intentionally not enabled for this portfolio project. If the live application randomly fails to load a page, the failure remains visible and can be investigated with the attached evidence.

Generated local files such as `.env`, `target/`, `screenshots/`, `page-sources/`, `videos/`, downloaded APK files, and Allure reports are excluded from version control.

---

## Known Limitations

- Tests depend on the availability and performance of the live BARBORA application and its backend services.
- Random page loading failures can happen when the live app or backend is unstable.
- UI changes in the mobile app can require locator or flow updates.
- Authenticated tests require a valid test account.
- Cart and session tests depend on the behavior of a real account and real application state.
- The suite intentionally avoids destructive checkout/payment actions.
- Automatic test retries are not enabled by design.

---

## Future Improvements

Potential improvements that would keep the suite responsible against a live app:

- Publish selected Allure reports to GitHub Pages for portfolio review.
- Add an architecture diagram for `TestBase`, `Driver`, screen objects, and reusable components.
- Add a small local setup checklist for emulator creation and APK installation.
- Add more explicit screen load markers where the app exposes stable accessibility identifiers.
