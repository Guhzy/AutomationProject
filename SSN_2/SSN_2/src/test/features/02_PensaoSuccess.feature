Feature: Test pensao simulation success

  Scenario Outline: Check simulation success
    Given SSN website
    And clicks on pensoes
    When clicks on simulador de pensoes
    And chooses the simulation
    And inputs <date>
    When  clicks prosseguir
    Then checks <output>

    Examples:
      | date         |           output              |
      |  20381231    | Está a pensar reformar-se aos |