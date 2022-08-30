Feature: Test pensao simulation error

  Scenario Outline: Check simulation error
    Given inside SSN website
    And click on pensoes
    When click on simulador de pensoes
    And choose the simulation
    And input <date>
    Then check if date is <valid>

    Examples:
      | date     |                             valid                                      |
      | 20201231 | Não é possível simular para uma data da reforma anterior à data atual. |
      |          | Preenchimento obrigatório.                                             |
      | 20201450 | Data inválida                                                          |