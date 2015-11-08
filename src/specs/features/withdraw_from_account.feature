Feature: Withdraw from account
  As a client of the bank
  I want to withdraw money from my account
  In order to have cash

  @ui
  Scenario: An existing client withdraws from his account
    Given an existing client named "pierre-jean" with 100.0 EUR in his account
    When he withdraws 10.0 EUR from his account
    Then the new balance is 90.0 EUR

  @ui
  Scenario: An existing client withdraws overdraft from his account
    Given an existing client named "paul-guillaume" with 100.0 EUR in his account
    When he withdraws 110.0 EUR from his account
    Then the new balance is 0.0 EUR

  @ui
  Scenario: An existing client withdraws a negative or null amount from his account
    Given an existing client named "Aurélie" with 100.0 EUR in her account
    When she withdraws 0.0 EUR from her account
    Then the new balance is 100.0 EUR
    When she withdraws -10.0 EUR from her account
    Then the new balance is 100.0 EUR

  @ui
  Scenario: An existing client withdraws a negative or null amount from his account
    Given an existing client named "Aurélie" with 100.0 EUR in her account
    When she inputs 0.0 EUR
    Then there is an alert "zéro n'est pas autorisé"
    When she inputs -10.0 EUR
    Then there is an alert "les valeurs négatives ne sont pas autorisées"