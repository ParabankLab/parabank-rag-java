# Author: Tamás András Péter

# ParaBank Test Automation Architecture (Playwright Java) 🚀

> A production-grade test automation framework for ParaBank built with **Playwright Java**, featuring an MVC architectural layout to separate test orchestration, API/data models, and Page Object views.

---

## 🏛 Architecture & Design Principles

* **Strict MVC Segregation:** UI pages act as Views, test suites and flow handlers act as Controllers, and API contracts/DTOs live in the Model layer.
* **Deterministic Execution:** Uses Playwright’s auto-waiting and event-driven synchronization, eliminating non-deterministic delays or `Thread.sleep()`.
* **Hybrid API & UI Layering:** Integrates direct API model validation alongside full browser-based end-to-end flows.

---

## 📁 Repository Structure

```text
src/
├── main/
│   ├── java/
│   │   └── com.playwright.framework/   # Core framework drivers, browser factories & setup
│   │       ├── Main.java
│   │       └── Test.java
│   └── resources/                      # Application & framework configuration files
└── test/
    ├── java/                           # Test suite implementation (MVC Pattern)
    │   ├── controller/                 # Test execution controllers & flow orchestrators
    │   ├── model.api/                  # API data models, DTOs & response schemas
    │   └── view.pages/                 # Page Object Model (POM) UI view abstractions
    └── resources/                      # Test data, environment configs & resources