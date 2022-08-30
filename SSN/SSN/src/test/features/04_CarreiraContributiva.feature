Feature: Test if we can search by year in carreira contributiva

  Scenario Outline: Search by year in carreira contributiva
    Given carreira contributiva web page
    And click on see details
    When click on date dropdown
    And selects <year>
    Then check total salary of that <result>

    Examples:
    | year | result |
    | 2022 | 2022   |
    | 2021 | 2021   |
    | 2020 | 2020   |