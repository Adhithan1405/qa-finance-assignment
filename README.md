# QA Engineer Take-Home Assignment
**Avega Business Solutions — Finance Application Testing**

---

## Application Under Test

**App:** OrangeHRM Demo (HR + Payroll)  
**URL:** https://opensource-demo.orangehrmlive.com  
**Reason:** Publicly accessible, has real login/authentication flows, simulates payroll and HR finance workflows. No setup required — works straight in the browser.

**API:** Reqres.in  
**URL:** https://reqres.in  
**Reason:** Clean REST API with stable endpoints that mirror real-world finance user-management patterns (create user, fetch user, auth). Ideal for demonstrating API test design without needing backend access to OrangeHRM.

---


## Tech Stack

| Layer | Framework / Tool | Language |
|-------|-----------------|----------|
| UI Automation | Selenium WebDriver 4 + Cucumber 7 (BDD) | Java 11 |
| API Automation | REST Assured 5 + Cucumber 7 (BDD) | Java 11 |
| Test Runner | JUnit Platform Suite 5 | Java 11 |
| Build | Maven | — |
| Driver Management | WebDriverManager (auto-downloads ChromeDriver) | — |
| Reporting | Cucumber HTML + JSON reports | — |

---

## Project Structure

```
qa-finance-assignment/
├── test-cases/
│   └── manual-test-cases.md          # Task 1 — 8 manual test cases
│
├── ui-tests/                          # Task 2 — Selenium + Cucumber BDD
│   ├── pom.xml
│   └── src/
│       ├── main/java/
│       │   ├── pages/
│       │   │   ├── BasePage.java      # Explicit wait helpers (no Thread.sleep)
│       │   │   ├── LoginPage.java     # Page Object — login screen
│       │   │   └── DashboardPage.java # Page Object — dashboard
│       │   └── utils/
│       │       └── DriverManager.java # ThreadLocal WebDriver + config reader
│       └── test/
│           ├── java/
│           │   ├── runners/TestRunner.java   # JUnit5 Cucumber runner
│           │   └── stepdefs/LoginSteps.java  # Step definitions
│           └── resources/
│               ├── config.properties         # Base URL, browser, wait timeouts
│               └── features/login.feature    # Gherkin scenarios (BDD)
│
└── api-tests/                         # Task 3 — REST Assured + Cucumber BDD
    ├── pom.xml
    └── src/test/
        ├── java/
        │   ├── runners/ApiTestRunner.java  # JUnit5 Cucumber runner
        │   ├── stepdefs/ApiSteps.java      # Step definitions
        │   └── utils/
        │       ├── ApiConfig.java          # Reads api-config.properties
        │       └── SpecBuilder.java        # Reusable RequestSpecification
        └── resources/
            ├── api-config.properties       # Base URL + API key (not hardcoded)
            └── features/api-tests.feature  # Gherkin API scenarios
```

---

## Prerequisites

- **Java 11+** — `java -version`
- **Maven 3.8+** — `mvn -version`
- **Google Chrome** (latest) — ChromeDriver is auto-downloaded by WebDriverManager

---

## How to Run

### Task 2 — UI Tests (Selenium + BDD)

```bash
cd ui-tests
mvn clean test
```

HTML report generated at: `ui-tests/target/cucumber-reports/report.html`

**Run a specific tag only:**
```bash
mvn test -Dcucumber.filter.tags="@positive"
mvn test -Dcucumber.filter.tags="@negative"
mvn test -Dcucumber.filter.tags="@TC001"
```

**Run headless (CI):**  
Edit `src/test/resources/config.properties` → set `headless=true`

---

### Task 3 — API Tests (REST Assured + BDD)

```bash
cd api-tests
mvn clean test
```

HTML report generated at: `api-tests/target/cucumber-reports/api-report.html`

---

## Manual Test Cases

See [`test-cases/manual-test-cases.md`](test-cases/manual-test-cases.md)

8 test cases covering the **Login module** of OrangeHRM:
- 2 positive scenarios (valid login, header verification)
- 5 negative scenarios (wrong password, unknown user, empty fields)
- 1 boundary value (single character password)

---

## Assumptions & Limitations

1. **OrangeHRM demo credentials** (`Admin` / `admin123`) are publicly documented and stable; if the demo site resets them, update `config.properties`.
2. **Reqres.in** is used for API tests as OrangeHRM does not expose a public REST API. The endpoints mirror real-world user/auth patterns.
3. UI tests are written for **Chrome only**. Adding Firefox support requires adding a Firefox branch in `DriverManager.java`.
4. API tests do not require a running local server — all calls hit the hosted `reqres.in` service.
5. `implicit.wait` and `explicit.wait` in `config.properties` can be tuned for slower CI environments.

---

## Skipped Items

- Cross-browser testing (Firefox/Edge) — out of scope for this assignment but easy to extend via DriverManager.
- Parallel execution — not configured but supported by the ThreadLocal driver pattern already in place.
- Screenshots on failure — can be added via a Cucumber `@After` hook using `TakesScreenshot`.
