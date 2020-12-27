Feature: Test QualityPointTech empdetails scenario
@TC_01
  Scenario: Test login with valid credentials 
    Given Open chrome and start application
    When Entering Username,Password and Click on Login button 
    Then user should be able to login
    And user clicks on edit option
    And user update card type
    And user update card expiry date
    And user clicks on update button
    And user verifies all the updates are done or not
    And user clicks on logout
    Then application should be closed
  
  