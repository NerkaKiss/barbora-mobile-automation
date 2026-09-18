# BARBORA Mobile Appium Automation Test Suite

End-to-end Android mobile test automation for the BARBORA application. Built as a QA Automation portfolio project with Java, Appium, TestNG, Maven, GitHub Actions, and Allure reporting.

![Java](https://img.shields.io/badge/Java-21+-007396?logo=openjdk&logoColor=white)
![Appium](https://img.shields.io/badge/Appium-Mobile_Automation-662d91?logo=appium&logoColor=white)
![TestNG](https://img.shields.io/badge/TestNG-Test_Framework-f2c811)
![Maven](https://img.shields.io/badge/Maven-Build_Tool-c71a36?logo=apachemaven&logoColor=white)
[![BARBORA Mobile Tests](https://github.com/NerkaKiss/barbora-mobile-automation/actions/workflows/mobile-tests.yml/badge.svg)](https://github.com/NerkaKiss/barbora-mobile-automation/actions/workflows/mobile-tests.yml)
[![Allure Report](https://img.shields.io/badge/Allure-View_Report-orange)](https://nerkakiss.github.io/barbora-mobile-automation/)

> The suite runs against a live mobile application backed by external services. Failures are intentionally left visible and supported by screenshots, video, Android page source, Appium logs, Surefire reports, and Allure results to help distinguish application, backend, UI, automation, and CI infrastructure issues.

---

## About This Project

This project demonstrates practical Android mobile automation against a real e-commerce application.

It covers:

- App launch
- Bottom navigation
- Valid and invalid login scenarios
- Search input, suggestions, and submitted results
- Product details
- Cart operations
- Cart persistence after navigation and app restart
- User session persistence after app restart

The project focuses on maintainable test automation architecture rather than placing Appium details directly in test methods.

Key design choices include:

- Screen objects for full-screen application areas
- Reusable UI components
- Focused test-state helpers for scenario setup
- Per-test Appium session isolation
- Explicit business-state preparation
- JSON-driven test data
- Environment-based credentials
- Explicit waits instead of implicit waits
- TestNG smoke and regression groups
- Failure diagnostics for both test and configuration failures
- Android emulator execution in GitHub Actions
- Public Allure reporting through GitHub Pages
- No automatic test retries

---

## Tech Stack

| Area | Technology |
| --- | --- |
| Language | Java 21 |
| Mobile automation | Appium Java Client 10.1.1 |
| WebDriver layer | Selenium 4.43.0 |
| Test framework | TestNG 7.12.0 |
| Build tool | Maven |
| Test data | Gson / JSON |
| Reporting | Allure TestNG + Allure Maven |
| CI/CD | GitHub Actions |
| CI Android environment | Android 15 / API 35, `google_atd`, x86_64 |
| Local/CI automation driver | UiAutomator2 |

---

## Testing Strategy

- **Smoke suite** - critical checks for app launch, navigation, search input, search suggestions, valid login, and adding a searched product to cart.
- **Regression suite** - broader coverage for submitted search results, invalid login data, cart quantity changes, cart persistence, product removal, and session persistence.
- **Data-driven login tests** - invalid credential scenarios are stored in JSON and supplied through a TestNG `DataProvider`.
- **Test isolation** - every TestNG test method starts a fresh Appium session while keeping `app.noReset=true`.
- **Business-state preparation** - tests prepare the state they require instead of depending on execution order or a previous test.
- **Product/cart state helper** - `ProductCartTestState` prepares login and empty-cart preconditions outside the test class.
- **Failure-preserving cleanup** - cart cleanup runs after successful product/cart tests; failed flows keep their state and evidence for investigation.
- **Overlay handling** - cookie startup handling and promotional overlay handling stay outside individual test scenarios.
- **Configuration-failure diagnostics** - failures in setup/teardown can still produce screenshot, page-source, and video evidence.
- **No automatic retries** - a failure remains visible instead of being hidden by generic retry logic.

---

## Responsible Testing Against a Live App

The suite targets a live application, so its scope is intentionally controlled.

The goal is to demonstrate mobile automation design and realistic E2E testing practices without producing unnecessary traffic or performing destructive actions.

Key decisions:

- Keep the suite focused on high-value user journeys.
- Run tests sequentially by default.
- Avoid placing real orders.
- Avoid payment flows.
- Clean test-created cart state after successful cart scenarios.
- Avoid excessive retries against the live service.
- Preserve the original failure state and diagnostics whenever possible.

---

## Project Structure

```text
.
|-- .github/
|   `-- workflows/
|       `-- mobile-tests.yml                # Manual suites, nightly full run, Allure Pages deployment
|-- src/
|   `-- test/
|       |-- java/
|       |   |-- components/
|       |   |   |-- BottomNavigation.java   # Bottom navigation actions
|       |   |   |-- CookieBanner.java       # First-start cookie handling
|       |   |   |-- PromoOverlay.java       # Explicit promo handling for flows that need it
|       |   |   `-- SearchBar.java          # Search input and suggestions
|       |   |-- screens/
|       |   |   |-- Common.java             # Shared waits, interactions, and UI helpers
|       |   |   `-- barbora/
|       |   |       |-- CartScreen.java
|       |   |       |-- HomeScreen.java
|       |   |       |-- LoginScreen.java
|       |   |       |-- ProductDetailsScreen.java
|       |   |       |-- ProductsScreen.java
|       |   |       |-- ProfileScreen.java
|       |   |       `-- SearchScreen.java
|       |   |-- state/
|       |   |   `-- ProductCartTestState.java
|       |   |-- test/
|       |   |   |-- TestBase.java           # Per-test Appium lifecycle and failure artifacts
|       |   |   `-- barbora/
|       |   |       |-- HomeTest.java
|       |   |       |-- LoginTest.java
|       |   |       |-- NavigationTest.java
|       |   |       |-- ProductCartFlowTest.java
|       |   |       |-- SearchTest.java
|       |   |       `-- SessionPersistenceTest.java
|       |   |-- testdata/
|       |   |   `-- LoginTestData.java
|       |   `-- utils/
|       |       |-- AppManager.java
|       |       |-- ConfigReader.java
|       |       |-- Driver.java
|       |       |-- EnvReader.java
|       |       |-- JsonDataReader.java
|       |       |-- PageSourceRecorder.java
|       |       |-- PromoOverlayHandler.java
|       |       |-- ScreenshotRecorder.java
|       |       |-- TestListener.java
|       |       |-- UiSelectorUtils.java
|       |       `-- VideoRecorder.java
|       `-- resources/
|           |-- allure.properties
|           |-- config.properties
|           `-- testdata/
|               `-- invalid-login.json
|-- testng.xml
|-- pom.xml
`-- README.md
```

---

## Architecture

### Screen Objects + Reusable Components + Test State Helpers + Base Test

Tests use screen objects for full-screen areas, reusable components for shared UI behavior, and focused state helpers for scenario preparation.

This keeps test methods focused on user behavior while locators, waits, Appium interactions, state preparation, and infrastructure concerns remain outside the test flow where possible.

```text
Test classes
  `-- TestBase
        |-- Driver
        |-- CookieBanner
        |-- ScreenshotRecorder
        |-- PageSourceRecorder
        `-- VideoRecorder

TestNG suite
  `-- TestListener
        `-- configuration-failure diagnostics

Scenario state preparation
  `-- ProductCartTestState
        |-- BottomNavigation
        |-- ProfileScreen
        |-- LoginScreen
        |-- CartScreen
        |-- PromoOverlay
        `-- EnvReader

Reusable application interaction
  |-- components
  |     |-- BottomNavigation
  |     |-- SearchBar
  |     |-- CookieBanner
  |     `-- PromoOverlay
  |
  `-- screens
        |-- HomeScreen
        |-- ProductsScreen
        |-- ProfileScreen
        |-- LoginScreen
        |-- SearchScreen
        |-- ProductDetailsScreen
        `-- CartScreen

Shared UI interaction
  `-- Common
        `-- PromoOverlayHandler
```

### Test State Preparation

`ProductCartTestState` owns the preconditions required by the product/cart scenarios.

It:

1. Opens the profile screen.
2. Verifies that profile navigation actually succeeded.
3. Dismisses a promotional overlay when present.
4. Logs in if the user is not already authenticated.
5. Opens the cart and removes existing products when necessary.
6. Returns the application to Home before the test begins.

The helper fails setup immediately with a clear exception when a required navigation or business state cannot be reached. This is deliberately different from a test assertion: the test scenario itself has not started successfully because its preconditions were not established.

### Promo Overlay Handling

The project uses one underlying promo-dismissal implementation in `PromoOverlayHandler`, exposed in two ways:

- `PromoOverlay` is a reusable component for flows that explicitly want to dismiss the promo.
- `Common` calls `PromoOverlayHandler` before normal shared UI actions so unexpected promotional UI does not block ordinary interactions.

Both paths delegate to the same handler rather than maintaining two independent dismissal implementations.

### Driver Lifecycle

`TestBase` starts and quits one Appium session for every test method.

```text
@BeforeMethod
  -> Driver.startDriver()
  -> start screen recording
  -> handle first-start cookie state when needed
  -> test-specific setup
  -> test
  -> failure diagnostics or discard successful video
  -> Driver.quitDriver()
```

`app.noReset=true` preserves application data between sessions, while each test still prepares the business state it needs.

The driver also prints lightweight lifecycle timing:

```text
[PERF] Appium session start: 7.1 s
[PERF] Appium session quit: 0.5 s
```

### Key Conventions

- Screen and component classes encapsulate locators and UI actions.
- Test outcome assertions stay in test classes.
- Focused state helpers may fail setup with clear exceptions when required preconditions cannot be established.
- Tests do not use `AndroidDriver` directly.
- One Appium session is created per test method.
- `app.noReset=true` preserves application data between sessions.
- `app.forceLaunch=true` launches the application for the new session.
- Shared interactions use explicit waits.
- No implicit wait is configured.
- `TestBase` owns normal per-test setup, teardown, recording, and failure artifacts.
- `TestListener` captures diagnostics for TestNG configuration failures.
- Shared promotional overlay handling is centralized through `PromoOverlayHandler`.
- Invalid-login test data lives in JSON.
- Credentials are read from environment variables first and `.env` second.
- TestNG groups control smoke, regression, and full execution.
- Automatic retries are intentionally not enabled.

---

## Test Coverage

| Feature | File | Groups | Coverage |
| --- | --- | --- | --- |
| App launch | `HomeTest.java` | `smoke` | Home screen visibility after launch |
| Navigation | `NavigationTest.java` | `smoke` | Products, cart, and profile screen navigation |
| Search | `SearchTest.java` | `smoke`, `regression` | Search input, suggestions, and submitted search results |
| Login | `LoginTest.java` | `smoke`, `regression` | Valid login and JSON-driven invalid credential scenarios |
| Product/cart flow | `ProductCartFlowTest.java` | `smoke`, `regression` | Add product, increase quantity, persistence after navigation/restart, remove product |
| Session persistence | `SessionPersistenceTest.java` | `regression` | User remains logged in after app restart |

### Current Suite Size

| Suite | Test invocations |
| --- | ---: |
| Full | 17 |
| Smoke | 8 |
| Regression | 9 |

The regression suite includes three data-driven invalid-login invocations.

---

## Test Groups

| Group | Purpose |
| --- | --- |
| `smoke` | Critical app launch, navigation, login, search, and cart happy-path checks |
| `regression` | Broader functional coverage, negative login scenarios, cart behavior, and session persistence |

Run groups locally:

```bash
# Smoke
mvn clean test -Dgroups=smoke

# Regression
mvn clean test -Dgroups=regression

# Full suite
mvn clean test
```

---

## Running Locally

### Prerequisites

- Java 21+
- Maven 3.9+
- Android Studio or Android SDK
- Android emulator or compatible Android device
- Appium 3+
- Appium UiAutomator2 driver
- BARBORA Android application installed on the target device

The APK itself is not stored in this public automation repository.

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

Create a `.env` file in the project root or provide real environment variables:

```env
BARBORA_LOGIN_EMAIL=your-email@example.com
BARBORA_LOGIN_PASSWORD=your-password
```

The framework first reads real environment variables and then falls back to `.env`.

### Device and App Configuration

Default settings are stored in:

```text
src/test/resources/config.properties
```

Current default configuration:

```properties
appium.server.url=http://127.0.0.1:4723

android.udid=emulator-5554
android.platform.name=Android

app.package=lt.barbora
app.activity=barbora.next.DefaultIcon

app.noReset=true
app.forceLaunch=true
```

When using another emulator or a real device, update `android.udid` as required.

### Run Tests

```bash
# Full suite
mvn clean test

# Smoke
mvn clean test -Dgroups=smoke

# Regression
mvn clean test -Dgroups=regression

# One class
mvn clean test -Dtest=LoginTest
```

The TestNG suite definition is stored in `testng.xml`.

---

## CI/CD

GitHub Actions workflow:

```text
.github/workflows/mobile-tests.yml
```

### Triggers

| Trigger | Suite | Behavior |
| --- | --- | --- |
| Manual dispatch | `smoke` | Runs smoke group |
| Manual dispatch | `regression` | Runs regression group |
| Manual dispatch | `full` | Runs complete TestNG suite |
| Nightly schedule | `full` | Runs daily at `00:37 UTC` |

The nightly schedule intentionally uses minute `37` instead of the top of the hour.

### Required GitHub Secrets

| Secret | Purpose |
| --- | --- |
| `BARBORA_LOGIN_EMAIL` | Test account email |
| `BARBORA_LOGIN_PASSWORD` | Test account password |
| `BARBORA_APK_TOKEN` | Token used by CI to download the BARBORA APK release asset |

### CI Environment

The workflow:

- Checks out the repository.
- Sets up Java 21.
- Sets up Node.js 22.
- Installs Appium 3.
- Installs the UiAutomator2 Appium driver.
- Downloads the BARBORA split APK bundle from a separate release repository.
- Enables KVM on the GitHub-hosted Linux runner.
- Starts Appium and waits for `/status`.
- Starts an Android 15 / API 35 `google_atd` x86_64 Pixel 7 emulator.
- Disables animations and the Android spellchecker.
- Runs the emulator without a visible window, audio, cameras, metrics, snapshots, or boot animation.
- Disables Android crash and ANR dialogs before running the application.
- Installs the split APK bundle with `adb install-multiple`.
- Grants notification permission.
- Executes the selected TestNG suite.
- Stops Appium after execution.
- Uploads diagnostic artifacts after every run.
- Publishes eligible full-suite Allure reports to GitHub Pages.

### CI Artifacts

The workflow uploads the following for 14 days:

```text
target/allure-results/
target/surefire-reports/
screenshots/
page-sources/
videos/
appium.log
appium-console.log
```

These artifacts help distinguish between:

```text
Test automation failure
Application/UI failure
Backend or external service issue
Android/Appium issue
CI/emulator infrastructure failure
```

---

## Allure Reporting

### Public Report

**View the latest available full-suite Allure report:**

https://nerkakiss.github.io/barbora-mobile-automation/

GitHub Pages publishing follows these rules:

- A **GREEN full run** with Allure results publishes its report.
- A **RED full run** with Allure results also publishes its report.
- Smoke and regression-only runs do not replace the public report.
- If CI fails before tests produce Allure results, Pages is not overwritten with an empty report.
- A failed test run remains failed in GitHub Actions even if report generation and Pages deployment succeed.

This keeps the public report transparent while avoiding meaningless empty reports after pure infrastructure failures.

### Local Allure Results

Raw Allure results are written to:

```text
target/allure-results
```

Generate the HTML report:

```bash
mvn allure:report
```

Generated report:

```text
target/site/allure-maven-plugin
```

Serve it locally:

```bash
mvn allure:serve
```

### Attachment Policy

- Passed tests do not keep diagnostic attachments.
- Successful test videos are discarded.
- Failed tests save and attach a screenshot.
- Failed tests save and attach Android page source XML.
- Failed tests save and attach screen recording when recording started successfully.
- TestNG configuration failures also attempt to capture screenshot, page source, and video.
- Diagnostic collection is defensive so an artifact failure does not replace the original test/configuration failure.

---

## Failure Diagnostics

On failed tests and TestNG configuration failures, the framework can provide:

- Screenshot in `screenshots/`
- Android page source XML in `page-sources/`
- Screen recording in `videos/`
- Screenshot attachment in Allure
- Page-source attachment in Allure
- Video attachment in Allure
- Appium server logs from CI
- Surefire reports from CI
- Appium session timing in standard output

Automatic test retries are intentionally not enabled.

When the live application, backend, Android emulator, or automation environment behaves unexpectedly, the original failure remains visible and can be investigated using the collected evidence rather than being hidden by repeated execution.

Generated local files such as `.env`, `target/`, `screenshots/`, `page-sources/`, `videos/`, downloaded APK files, and generated Allure reports are excluded from version control.

---

## Known Limitations

- Tests depend on the availability and performance of the live BARBORA application and its backend services.
- Live pages or backend responses can occasionally load inconsistently.
- UI changes can require locator or flow updates.
- Authenticated scenarios require a valid test account.
- Cart and session scenarios interact with real account state.
- Promotional overlays can appear asynchronously.
- GitHub-hosted Android emulator provisioning can fail independently of the test suite.
- Android emulator and UiAutomator2 stability can vary between CI runs.
- The project intentionally avoids checkout, payment, and other destructive production actions.
- Automatic retries are disabled by design.

---

## Future Improvements

Potential improvements that preserve the current isolation and failure transparency:

- Add more explicit screen-load markers where stable accessibility identifiers are available.
- Run selected scenarios on a real-device cloud such as BrowserStack.
- Profile slow test flows and reduce unnecessary waiting without weakening per-test isolation.
- Add automated failure classification for automation, application, backend, Android/Appium, and CI infrastructure failures.
- Expand coverage where additional production-safe scenarios can be tested responsibly.
