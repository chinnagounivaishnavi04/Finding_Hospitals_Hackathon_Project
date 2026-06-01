# 🏥 Finding Hospitals – Automation Testing Project

## 📌 Problem Statement
This project automates the process of identifying hospitals based on specific criteria:
- Open 24/7
- Parking facility available
- Rating greater than 3.5

The automation is implemented using a real-time healthcare website such as **Practo.com**.



## 🎯 Objectives

### Scenario 1: Find Hospitals in Bangalore
- Open Practo website
- Search for **Hospitals in Bangalore**
- Apply filters:
    - Open 24/7
    - Parking facility
    - Rating > 3.5
- Extract and display hospital names


### Scenario 2: Diagnostics Page
- Navigate to Diagnostics section
- Capture all **top city names**
- Store them in a **List**
- Display the extracted cities

---

### Scenario 3: Corporate Wellness Form
- Navigate to **Corporate Wellness page**
- Fill the form with **invalid details**
- Submit the form
- Capture and display the **warning/alert message**


## 🧪 Automation Scope Covered

- Handling alerts and popups
- Handling multiple browser windows/tabs
- Page navigation (forward/backward)
- Extracting multiple elements into collections
- Form filling and validation
- Capturing error/warning messages


## 🛠️ Tech Stack

- **Language:** Java
- **Automation Tool:** Selenium WebDriver
- **Framework:** TestNG / JUnit / Cucumber
- **Build Tool:** Maven
- **Reporting:** Extent Reports, Cucumber Reports
- **Version Control:** Git & GitHub
- **IDE:** IntelliJ IDEA



## ▶️ How to Run the Project

1. Clone repository:

git clone https://github.com/chinnagounivaishnavi04/Finding_Hospitals_Hackathon_Project.git

2. Open in IntelliJ / Eclipse

3. Install dependencies:

mvn clean install

4. Run tests:

mvn test

---

## 📊 Outputs

- List of hospitals matching criteria
- List of diagnostic cities
- Captured alert messages
- Generated reports:
    - Extent Report
    - Cucumber HTML Report


## ⚠️ Notes

- Avoid committing logs and reports (`logs/`, `reports/`)
- Use explicit waits instead of Thread.sleep()
- Ensure proper exception handling


## 🚀 Future Enhancements

- Cross-browser testing support
- CI/CD integration (Jenkins/GitHub Actions)
- Data-driven testing using Excel
- Advanced reporting dashboard
