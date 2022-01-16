@api
Feature: template to use rest-assured for API testing

    Scenario: API_DELETE Method
        When User tries to delete a record "/api/users/2"
        Then Record is "deleted"

    Scenario: API_GET Method
        When User perform GET request for "/api/users?page=2" endpoint
        Then User get a 200 http status code

    Scenario: API_GET Method - Not found
        When User perform GET request for "/api/users/23" endpoint
        Then User get a 404 http status code


    Scenario: API_PATCH/PUT Method
        When User tries to update a record "/api/users/2"
        Then Record is "updated"

    Scenario: API_POST Method
        When User tries to create a new record "/api/users"
        Then Record is "created"
