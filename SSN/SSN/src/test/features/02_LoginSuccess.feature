Feature: Test SSN website login successful functionality

  Scenario Outline: Check if login successful
    Given SSN website opens
    And on login page
    When entered username and password
    And click on login
    Then check <message> for login successful

    Examples:
      | message    |
      | NISS valid |