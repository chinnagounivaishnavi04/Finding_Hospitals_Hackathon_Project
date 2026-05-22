Feature: Corporate Wellness Form Validation

  Scenario Outline: Validate Wellness form using Excel data
    Given the user navigates to the Corporate Wellness page
    When the user reads wellness data from excel row <ExcelRow> and fills the form
    And the user checks the Schedule button status
    Then the system should record the result in Excel

    Examples:
      | ExcelRow |
      | 2        |
      | 3        |
