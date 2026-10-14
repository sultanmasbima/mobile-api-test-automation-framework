# Running the Tests: Step-by-Step Guide

This guide covers everything needed to run the suite on a fresh machine, plus a short
live-demo script for walking someone through the framework.

- [1. Prerequisites](#1-prerequisites)
- [2. First run (API + Web)](#2-first-run-api--web)
- [3. Running a subset with tags](#3-running-a-subset-with-tags)
- [4. Mobile tests (Android)](#4-mobile-tests-android)
- [5. Reports](#5-reports)
- [6. Running from IntelliJ IDEA](#6-running-from-intellij-idea)
- [7. Live demo script (~10 minutes)](#7-live-demo-script-10-minutes)
- [8. Troubleshooting](#8-troubleshooting)

---

## 1. Prerequisites

| Tool | Needed for | Check it with |
|---|---|---|
| JDK 21+ | everything | `java -version` |
| Maven 3.9+ | everything | `mvn -v` |
| Google Chrome | web tests | open Chrome → *About* |
| Node.js | Allure CLI, Appium | `node -v` |
| Android SDK + emulator (or a real device) | mobile tests | `adb devices` |
| Appium 2 + UiAutomator2 driver | mobile tests | `appium -v` and `appium driver list --installed` |

ChromeDriver is **not** needed: Selenium Manager downloads the matching driver automatically.

## 2. First run (API + Web)

```bash
git clone https://github.com/sultanmasbima/mobile-api-test-automation-framework.git
cd mobile-api-test-automation-framework

# 1. Quick sanity check (~5 s): every Gherkin step has a step definition
mvn test -Dsuite=testng-dryrun.xml

# 2. Full API + Web regression (~1-2 min, headless Chrome)
mvn test
```

Expected result at the end of the output:

```
Tests run: 23, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

## 3. Running a subset with tags

Every scenario carries tags, so any combination can be selected without touching the code:

```bash
mvn test -Dcucumber.filter.tags="@api"                    # API only
mvn test -Dcucumber.filter.tags="@web"                    # Web only
mvn test -Dcucumber.filter.tags="@smoke"                  # smoke tests of every layer
mvn test -Dcucumber.filter.tags="@negative"               # negative cases only
mvn test -Dcucumber.filter.tags="@TC-WEB-007"             # one specific test case
mvn test -Dcucumber.filter.tags="@web and not @negative"  # tag expressions work too
```

See the browser instead of running headless:

```bash
mvn test -Dcucumber.filter.tags="@TC-WEB-007" -Dweb.headless=false
```

## 4. Mobile tests (Android)

Mobile tests need three things running: an emulator, the Appium server and the app file.

```bash
# Terminal 1 - start the emulator (list names with: $ANDROID_HOME/emulator/emulator -list-avds)
$ANDROID_HOME/emulator/emulator -avd Medium_Phone_API_35

# Terminal 2 - start the Appium server and leave it running
appium

# Terminal 3 - download the demo app once, then run the suite (~5-10 min)
./scripts/download-demo-app.sh
adb devices                                   # must show: emulator-5554   device
mvn test -Dsuite=testng-mobile.xml
```

Run a single mobile scenario:

```bash
mvn test -Dsuite=testng-mobile.xml -Dcucumber.filter.tags="@TC-MOB-006"
```

Using a different device or emulator port:

```bash
mvn test -Dsuite=testng-mobile.xml -Dmobile.udid=<id from adb devices>
```

> Tip: give the emulator 4 GB of RAM (Android Studio → Device Manager → Edit → Advanced) and let it
> finish its first-boot background updates for a few minutes before running the suite.

## 5. Reports

| Report | How to open |
|---|---|
| Allure (interactive, local) | `npx allure-commandline serve target/allure-results` |
| Allure (single file to share) | `npx allure-commandline generate target/allure-results --clean --single-file -o target/allure-report`, then open `target/allure-report/index.html` |
| Cucumber HTML | open `target/cucumber-reports/api.html`, `web.html` or `mobile.html` |
| CI | GitHub → **Actions** → latest run → **Artifacts** → download `allure-report` → unzip → open `index.html` |

Failed web/mobile scenarios include a **screenshot** in both reports.

> `target/` is wiped by `mvn clean`. Results of several runs accumulate in `target/allure-results`
> until then, which is how retries show up in Allure.

## 6. Running from IntelliJ IDEA

1. *File → Open* the project folder and let Maven import the dependencies.
2. Install the **Cucumber for Java** and **Gherkin** plugins (feature files get highlighting and
   *Ctrl/Cmd + click* from a step to its Java code).
3. Open `src/test/java/runners/ApiRunner.java` (or `WebRunner` / `MobileRunner`) → click the green ▶ next to the class.
4. To pass options, edit the run configuration → *VM options*, e.g. `-Dweb.headless=false`.

## 7. Live demo script (~10 minutes)

A suggested order when showing the framework to someone, e.g. in an interview.

| # | Show | Command / file | Point to make |
|---|---|---|---|
| 1 | README | GitHub page | Scope (3 layers, 32 scenarios), CI badge, architecture diagram |
| 2 | A feature file | `features/mobile/login_checkout.feature` | Test cases are readable Gherkin with traceable IDs |
| 3 | Dry run | `mvn test -Dsuite=testng-dryrun.xml` | Fast quality gate, no browser or API involved |
| 4 | Smoke run, visible browser | `mvn test -Dcucumber.filter.tags="@web and @smoke" -Dweb.headless=false` | Real UI automation |
| 5 | API run | `mvn test -Dcucumber.filter.tags="@api"` | 12 scenarios in seconds, schema validation, cleanup |
| 6 | **A failing test** | `mvn clean test -Dcucumber.filter.tags="@TC-WEB-001" -Dweb.password=wrong_password` | Config override from the command line; failure produces a clear assertion + screenshot |
| 7 | Retry | `mvn test -Dsuite=testng-rerun.xml` | Only the failed scenario is re-run (it passes now that the password is back to normal) |
| 8 | Report | `npx allure-commandline serve target/allure-results` | Steps, attachments, screenshot of the failure, retry history |
| 9 | Code path of one step | feature → `steps/` → `pages/` or `screens/` → `BasePage`/`BaseScreen` | Layered architecture, explicit waits |
| 10 | CI | GitHub → Actions | Pipeline: dry run → tests → retry → reports, nightly schedule |

Optional: if an emulator is ready, run `@TC-MOB-006` (end-to-end checkout, ~1-2 min) and let it play on screen.

## 8. Troubleshooting

| Symptom | Likely cause | Fix |
|---|---|---|
| `Missing configuration key: ...` | Typo in a `-D` option name | Check the key in `src/test/resources/config.properties` |
| Web tests: `SessionNotCreatedException` | Chrome missing, or blocked from downloading the driver | Install/update Chrome, check the internet connection |
| API tests fail with `418` | `Accept` header changed | Restful Booker only accepts exactly `application/json` (see `ApiClient`) |
| API tests fail with 5xx / timeouts | Public demo API is down or resetting | Retry later; check `https://restful-booker.herokuapp.com/ping` |
| Mobile: `Connection refused` | Appium server not running | Start `appium` in a separate terminal |
| Mobile: `instrumentation process cannot be initialized` | Emulator still busy (first boot) or low on RAM | Wait a few minutes, give the emulator more RAM, then retry |
| Mobile: `Could not tap ...` | Wrong screen or slow device | Look at the screenshot in the report; increase `-Dmobile.timeout.seconds=30` |
| `adb: command not found` | Android platform-tools not on `PATH` | Add `$HOME/Library/Android/sdk/platform-tools` to `PATH` |
| `Undefined step` in the dry run | Gherkin text and step definition differ | Match the text exactly (Cucumber expressions: `{string}`, `{int}`, optional `(s)`) |
