Feature: Test add new salary

  Scenario Outline: Inputs a new salary
    Given salarios page
    And select <pencil>
    When write new salary <value>
    And verify <check>
    Then check new retirement salary

    Examples:
      | pencil   | value   | check |
      | 32       | 500000  | 32    |