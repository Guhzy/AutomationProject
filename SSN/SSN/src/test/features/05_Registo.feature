Feature: Test if we can create a new register when we don't have one

  Scenario Outline: Check if we can create a new account register
    Given SSN web page
    When click on register
    And accept terms and conditions
    And insert <ssn> and <birthdate>
    Then check <message> for register successful or not

    Examples:
      | ssn         | birthdate | message                                   |
      | 32176368218 | 19891023  | Erro captcha                              |
#      | 11926487407 | 19970612  | O NISS inserido já se encontra registado. |
#      | 11924417402 | 19980813  | O NISS indicado encontra-se Inativo       |
#      | 32176368218 | 19891023  | O NISS inserido não é válido.             |