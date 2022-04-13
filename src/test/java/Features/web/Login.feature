Feature: Login

    Background: Access to login page
        Given User goes to "$[LOGIN_URL]" page
        And User is on Login page

    @Regression @solo
    Scenario: Login with correct username and password
        When Tries to log in with the username "$[USER]" and password "$[PASSWORD]"

    @Regression
    Scenario Outline: Login with wrong username or password
        When Tries to log in with the username "<username>" and password "<password>"
        Then User gets an error message saying "<message>"

        Examples:
              |username 				|password 				|message 					                                                                               |
              |$[USER] 				    |						|Please enter your password.                                                                               |
              |${!test,a4,@,a8,!.com}	|$[PASSWORD] 			|Please check your username and password. If you still can't log in, contact your Salesforce administrator.|
              |$[USER] 				    |${N8} 					|Please check your username and password. If you still can't log in, contact your Salesforce administrator.|


    @Cpq @Regression @test
    Scenario: Verify that the application can be changed through the URL
        Given User goes to "$[LOGIN_URL]" page
        And User is on Login page
        When Tries to log in with the username "$[USER_CPQ]" and password "$[PASSWORD]"
        Then User is logged in
        When User change the current application to "$[SALESFORCE_CPQ_APP_URL]"
        Then Application name will be "Salesforce CPQ"
        When User change the current application to "$[LIGHTNING_SALES_APP_URL]"
        Then Application name will be "Sales"
        When User goes to "$[QUOTE_OBJECT_URL]"
        And User change the list view to "All"


