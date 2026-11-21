# Author: Tamás András Péter

# ParaBank Test Automation Framework (Playwright Java) 🚀

> A clean, deterministic end-to-end test automation framework for **ParaBank** built with **Playwright Java**, **TestNG**, **Allure Report**, and **Jenkins CI**, backed by a containerized ParaBank instance.

---

## 🏛 Architecture & Design Principles

* **Page Object Model (POM) & Clean Layering:** UI pages (`AdminPage`, `HomePage`, `OverviewPage`, `TransferPage`), framework utilities (`ParaBankUtils`), and support helpers live under `src/main/java`, keeping test classes (`BaseTest`, `LoginTest`, `TransferTest`) concise and readable.
* **Deterministic Execution & State Management:** Uses Playwright’s auto-waiting, persistent authentication state (`auth.json`), and controlled database initialization (`Initialize` → `Clean` sequence) against a local Dockerized ParaBank instance (`127.0.0.1:8081`).
* **Exact Financial Assertions:** Uses `BigDecimal` for account balance verification across fund transfer workflows to prevent floating-point rounding discrepancies.
* **CI/CD & Observability:** Configured with TestNG suite ordering (`testng.xml`), Allure step/test-case traceability (`TC-POS-*`), and automated Jenkins execution pulled directly from GitHub.

---

## 🛠 Tech Stack

* **Language:** Java
* **Browser Automation:** Microsoft Playwright (Java)
* **Test Runner:** TestNG
* **Reporting:** Allure Report
* **Environment & CI/CD:** Docker (ParaBank container) & Jenkins

---

## 📁 Repository Structure

```text
playwright/
src/
├── main/
│   ├── java/
│   │   └── com.playwright.framework/
│   │       ├── pages/                  # Page Object Model (POM) classes
│   │       │   ├── AdminPage.java
│   │       │   ├── HomePage.java
│   │       │   ├── OverviewPage.java
│   │       │   └── TransferPage.java
│   │       ├── support/                # Browser, session & framework support helpers
│   │       └── util/
│   │           └── ParaBankUtils.java  # URLs, constants & shared utility helpers
│   └── resources/                      # Main application & framework resources
└── test/
    ├── java/
    │   └── tests/                      # TestNG test classes & suite lifecycle
    │       ├── BaseTest.java           # @BeforeSuite DB setup, auth state & teardown
    │       ├── LoginTest.java          # Home page & authentication test scenarios
    │       └── TransferTest.java       # Balance verification & fund transfer scenarios
    └── resources/
        ├── data/
        │   └── testData.json           # Externalized test data (credentials, accounts, amounts)
        └── testng.xml                  # TestNG regression suite & execution order definition
```

---

## 🧪 Covered Test Scenarios

### `LoginTest`
1. **`navigateToHomePageTest`** — Verifies navigation and visibility of the ParaBank home page.
2. **`successfulLoginTest`** — Validates user authentication and redirection to the Overview page.

### `TransferTest`
1. **`checkAccountBalanceTest`** (`TC-POS-11`, `TC-POS-13`, `TC-POS-14`) — Verifies Overview page visibility and initial balances for source and destination accounts.
2. **`transferAmountTest`** (`TC-POS-21` – `TC-POS-24`, `TC-POS-11`) — Executes an inter-account fund transfer and validates updated `BigDecimal` balances on the Overview page.

---

## 🚀 Getting Started

### 1. Prerequisites
* Java (JDK 17+)
* Maven or Gradle
* Docker (running ParaBank locally on `http://127.0.0.1:8081`)
* Allure CLI (optional, for viewing reports locally)

### 2. Run the Test Suite
Execute the TestNG suite defined in `src/test/resources/testng.xml`:

```bash
mvn clean test
```
*(Or `./gradlew clean test` if using Gradle.)*

### 3. Generate & View Allure Report
```bash
allure serve target/allure-results
```

---

## ⚙️ CI/CD Pipeline (Jenkins)

* Jenkins pulls the latest changes directly from GitHub.
* Executes the TestNG regression suite against the Dockerized ParaBank environment.
* Publishes interactive Allure test reports with step-level execution timings and test case traceability.