Feature: Test SSN website login error functionality

  Scenario Outline: Check if login unsuccessful
    Given SSN website is open
    And is on login page
    When enters <username> and <password>
    And clicks on login
    Then checks <message> for login incorrect

    Examples:
      | username   | password  | message                                           |
      |            |           | Introduza a palavra-passe Introduza o seu NISS    |
      | invalid    |           | Introduza a palavra-passe                         |
      |            | invalid   | Introduza o seu NISS                              |
      | invalid    | invalid   | Dados de acesso incorretos                        |

