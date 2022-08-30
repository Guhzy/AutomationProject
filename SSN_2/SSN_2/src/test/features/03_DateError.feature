Feature: Test dates to check if find an error

  Scenario Outline: Check if you can not retire
    Given simulacao page
    And insert retirement <year>
    When click on salarios
    And click resultado da simulacao
    Then check if <error> is expected

    Examples:
      | year     |                                          error                                                                            |
      | 20281212 | Não é possível simular porque à data da reforma indicada não tem condições para atribuição da pensão (idade não cumprida). |