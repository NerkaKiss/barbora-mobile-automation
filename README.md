# BARBORA Mobile Appium Automation Test Suite

End-to-end mobile test automation for the BARBORA Android application. Built as a QA Automation portfolio project with Java, Appium, TestNG, Maven, GitHub Actions, and Allure reporting.

![Java](https://img.shields.io/badge/Java-21+-007396?logo=openjdk&logoColor=white)
![Appium](https://img.shields.io/badge/Appium-Mobile_Automation-662d91?logo=appium&logoColor=white)
![TestNG](https://img.shields.io/badge/TestNG-Test_Framework-f2c811)
![Maven](https://img.shields.io/badge/Maven-Build_Tool-c71a36?logo=apachemaven&logoColor=white)
![Allure](https://img.shields.io/badge/Allure-Reports-orange)
[![BARBORA Mobile Tests](https://github.com/NerkaKiss/barbora-mobile-automation/actions/workflows/mobile-tests.yml/badge.svg)](https://github.com/NerkaKiss/barbora-mobile-automation/actions/workflows/mobile-tests.yml)

> The suite runs against a live mobile application backed by external services. Failures are intentionally left visible and supported by screenshots, video, Android page source, Appium logs, and test reports to help distinguish application, backend, UI, automation, and CI infrastructure issues.

---

## About This Project

This project demonstrates practical Android mobile automation against a real e-commerce application.

It covers app launch, bottom navigation, login, invalid login validation, search, product details, cart operations, and session persistence after app restart.

The focus is on maintainable QA automation architecture:

- Screen objects
- Reusable UI components
- TestNG groups
- JSON-driven test data
- Environment-based credentials
- Per-test Appium session isolation
- Explicit business-state preparation
- CI execution on an Android emulator
- Failure diagnostics through screenshots, videos, page source files, Appium logs, Surefire reports, and Allure results

---

## Testing Strategy

- **Smoke suite** - critical checks for app launch, navigation, search input, search suggestions, valid login, and adding a searched product to cart.
- **Regression suite** - broader coverage for submitted search results, invalid login data, cart quantity changes, cart persistence, product removal, and session persistence.
- **Data-driven login tests** - invalid credential scenarios are stored in JSON and supplied through a TestNG `DataProvider`.
- **Test isolation** - each TestNG test method starts a fresh Appium session while keeping `app.noReset=true`; tests prepare their own required business state.
- **State preparation** - login and cart state are prepared explicitly before cart flow tests and cleaned only after successful tests to preserve failure evidence.
- **Overlay handling** - startup cookie handling and promotional overlays are handled outside the main test logic through reusable components and shared infrastructure.
- **Failure diagnostics** - failed setup or test methods capture screenshots, Android page source, video, and reporting artifacts.
- **No automatic retries** - failures remain visible instead of being hidden by generic retry logic.

---

## Responsible Testing Against a Live App

This project targets a live mobile application, so the suite is intentionally limited.

The goal is to demonstrate mobile automation design and E2E testing practices without generating unnecessary traffic or performing destructive actions.

Key decisions:

- Keep the suite focused on high-value user journeys.
- Avoid placing real orders or reaching payment flows.
- Use sequential TestNG execution by default.
- Clean cart state after successful cart tests.
- Avoid excessive retries against the live service.
- Preserve failure evidence instead of automatically repeating failed scenarios.

---

## Project Structure

```text
.
|-- .github/workflows/
|   `-- mobile-tests.yml                # GitHub Actions: manual suite selection and nightly full run
|-- src/test/java/
|   |-- components/
|   |   |-- BottomNavigation.java       # Bottom navigation actions
|   |   |-- CookieBanner.java           # Cookie banner handling
|   |   |-- PromoOverlay.java           # Explicit promo overlay component used in test flows
|   |   `-- SearchBar.java              # Search field and suggestions
|   |-- screens/
|   |   |-- Common.java                 # Shared waits, clicks, and helper methods
|   |   `-- barbora/
|   |       |-- CartScreen.java
|   |       |-- HomeScreen.java
|   |       |-- LoginScreen.java
|   |       |-- ProductDetailsScreen.java
|   |       |-- ProductsScreen.java
|   |       |-- ProfileScreen.java
|   |       `-- SearchScreen.java
|   |-- test/
|   |   |-- TestBase.java               # Per-test driver lifecycle and failure artifacts
|   |   `-- barbora/
|   |       |-- HomeTest.java
|   |       |-- LoginTest.java
|   |       |-- NavigationTest.java
|   |       |-- ProductCartFlowTest.java
|   |       |-- SearchTest.java
|   |       `-- SessionPersistenceTest.java
|   |-- testdata/
|   |   `-- LoginTestData.java          # Invalid login data model
|   `-- utils/
|       |-- AppManager.java             # App restart helper
|       |-- ConfigReader.java           # config.properties reader
|       |-- Driver.java                 # AndroidDriver setup and lifecycle
|       |-- EnvReader.java              # Environment and .env credentials reader
|       |-- JsonDataReader.java         # JSON test data reader
|       |-- PageSourceRecorder.java     # Page source file and Allure attachment
|       |-- PromoOverlayHandler.java    # Lightweight automatic promo dismissal from shared actions
|       |-- ScreenshotRecorder.java     # Screenshot file and Allure attachment
|       |-- TestListener.java           # TestNG configuration failure artifact capture
|       |-- UiSelectorUtils.java        # Android UiSelector escaping helpers
|       `-- VideoRecorder.java          # Appium screen recording handling
|-- src/test/resources/
|   |-- allure.properties
|   |-- config.properties               # Appium, device, package, and app launch config
|   `-- testdata/
|       `-- invalid-login.json          # Data-driven invalid login scenarios
|-- testng.xml                          # TestNG suite configuration
|-- pom.xml                             # Dependencies and Maven plugins
`-- README.md
```

---

## Architecture

### Screen Objects + Reusable Components + Base Test

Tests use screen objects for full-screen areas and components for reusable UI elements.

This keeps test classes focused on user scenarios while locators, waits, Appium interactions, and infrastructure concerns remain outside the tests.

```text
Test class
  `-- TestBase
        |-- Driver
        |-- CookieBanner
        |-- ScreenshotRecorder
        |-- PageSourceRecorder
        `-- VideoRecorder

TestNG suite
  `-- TestListener

Test flow
  |-- components
  |     |-- BottomNavigation
  |     |-- SearchBar
  |     `-- PromoOverlay
  |
  `-- screens
        |-- LoginScreen
        |-- SearchScreen
        |-- ProductDetailsScreen
        `-- CartScreen

Shared UI interaction
  `-- Common
        `-- PromoOverlayHandler
```

### Promo Overlay Handling

The project uses two related but separate promo-handling layers:

- `PromoOverlay` is a reusable UI component for flows that explicitly need to check or dismiss a promo overlay.
- `PromoOverlayHandler` provides lightweight automatic dismissal from shared `Common` UI actions.

This keeps promotional UI behavior outside the individual test methods while still allowing explicit handling when a specific flow requires it.

### Key Conventions

- Screen and component classes encapsulate locators and UI actions.
- Assertions stay in test classes.
- Tests do not directly use `AndroidDriver`.
- `TestBase` starts and quits one Appium session per test method.
- `app.noReset=true` preserves application data while each test still prepares its own required business state.
- `TestBase` owns startup handling, video recording, and normal test failure artifacts.
- `TestListener` captures artifacts for TestNG configuration failures such as failed `@BeforeMethod` setup.
- Shared promo handling is centralized rather than duplicated across tests.
- `Driver` prints lightweight Appium session start and quit timings with `[PERF]` log lines.
- Test data for invalid login scenarios is stored in JSON.
- Credentials are read from real environment variables first and `.env` second.
- TestNG groups control smoke, regression, and full execution.
- Automatic retries are intentionally not used.

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

### Current Suite Size

| Suite | Test invocations |
| --- | ---: |
| Full | 17 |
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

GitHub Actions workflow is configured in:

```text
.github/workflows/mobile-tests.yml
```

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

### CI Behavior

The workflow:

- Sets up Java and Node.js.
- Installs Appium 3 and the UiAutomator2 driver.
- Downloads the BARBORA APK release asset.
- Starts Appium before the Android emulator test step.
- Creates a Pixel 7 Android 15/API 35 `google_atd` emulator.
- Uses hardware virtualization through KVM on the GitHub-hosted Linux runner.
- Runs the emulator with 2 CPU cores.
- Disables emulator animations.
- Disables the Android spellchecker.
- Disables emulator metrics.
- Disables audio and cameras.
- Disables snapshots and boot animation.
- Disables Android system crash and ANR dialogs before installing and running BARBORA.
- Installs the split APK bundle.
- Executes the selected TestNG suite.
- Uploads reports and diagnostic artifacts after every run.

### Uploaded CI Artifacts

- `target/allure-results/`
- `target/surefire-reports/`
- `screenshots/`
- `page-sources/`
- `videos/`
- `appium.log`
- `appium-console.log`

The collected evidence helps distinguish between:

```text
Test automation failure
Application/UI failure
Backend or external service issue
Android/Appium issue
CI/emulator infrastructure failure
```

---

## Allure Reporting

Allure results are written to:

```text
target/allure-results
```

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

### Attachment Policy

- Passed tests attach nothing.
- Successful test videos are discarded.
- Failed tests save and attach a screenshot.
- Failed tests save and attach Android page source XML.
- Failed tests save and attach a screen recording when recording was started.
- TestNG configuration failures, including setup failures, also attempt to attach screenshot, page source, and video artifacts.
- Screenshots are also written to `screenshots/`.
- Page sources are also written to `page-sources/`.
- Failed-test videos are written to `videos/`.

---

## Failure Diagnostics

On failed tests and TestNG configuration failures, the framework provides:

- Screenshot in `screenshots/`
- Android page source XML in `page-sources/`
- Screen recording in `videos/`
- Screenshot attachment in Allure
- Page source attachment in Allure
- Video attachment in Allure
- Appium server logs in CI artifacts
- Surefire reports in CI artifacts

Appium session lifecycle timing is printed to standard output:

```text
[PERF] Appium session start: 12.4 s
[PERF] Appium session quit: 1.8 s
```

Automatic test retries are intentionally not enabled.

If the live application, backend, Android emulator, or automation environment behaves unexpectedly, the original failure remains visible and can be investigated using the collected evidence instead of being hidden by repeated execution.

Generated local files such as `.env`, `target/`, `screenshots/`, `page-sources/`, `videos/`, downloaded APK files, and Allure reports are excluded from version control.

---

## Known Limitations

- Tests depend on the availability and performance of the live BARBORA application and its backend services.
- Random page loading failures can happen when the live app or backend is unstable.
- UI changes in the mobile app can require locator or flow updates.
- Authenticated tests require a valid test account.
- Cart and session tests depend on the behavior of a real account and real application state.
- Promotional overlays may appear asynchronously and can affect normal user flows.
- GitHub-hosted Android emulator provisioning can occasionally fail independently of the test suite.
- Android emulator and UiAutomator2 stability can vary between CI runs.
- The suite intentionally avoids destructive checkout and payment actions.
- Automatic test retries are not enabled by design.

---

## Future Improvements

Potential improvements that would keep the suite maintainable and responsible against a live application:

- Publish selected Allure reports to GitHub Pages for portfolio review.
- Add more explicit screen load markers where the application exposes stable accessibility identifiers.
- Run selected scenarios on a real-device cloud such as BrowserStack.
- Profile CI runtime and reduce unnecessary waiting without weakening test isolation.
- Add automated failure classification for test automation, application, backend, Android/Appium, and CI infrastructure issues.