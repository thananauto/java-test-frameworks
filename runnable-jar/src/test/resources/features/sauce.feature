@UI @all
Feature: Validate the login scenario of sauce lab

  Scenario: Log in to sauce lab
    Given Launch the application
    And Enter the username as 'standard_user'
    And Enter the password as 'secret_sauce'
    When Click on Login button
    Then User should be in 'product list' page