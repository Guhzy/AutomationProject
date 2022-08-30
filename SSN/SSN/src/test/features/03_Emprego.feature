Feature: Test if we can get to carreira contributiva

  Scenario: Navigate to carreira contributiva once in home page
    Given logged in
    And on homepage
    When click on emprego
    And click on carreira contributiva
    Then check if in right place