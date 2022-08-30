Feature: Test dates to check if you can retire

  Scenario Outline: Check if you can retire
    Given inside simulacao page
    And inserts retirement <year>
    When clicks on salarios
    And clicks resultado da simulacao
    Then check if <msg> is correct

    Examples:
      | year     | msg      |
      | 20381212 | Imprimir |