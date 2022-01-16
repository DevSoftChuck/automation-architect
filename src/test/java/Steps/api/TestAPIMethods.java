package Steps.api;

import Setup.TestEnvironment;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;

import static io.restassured.RestAssured.given;

public class TestAPIMethods extends TestEnvironment {

    private Response response;

    @When("User tries to delete a record {string}")
    public void userTriesToDeleteARecord(String endpoint){
        this.response = given()
                .when()
                .delete(endpoint);
    }

    @Then("Record is {string}")
    public void recordIs(String result){
        switch (result) {
            case "created":
                Assert.assertEquals(response.getStatusCode(), 201, "Error, record was not created");
                break;
            case "updated":
                Assert.assertEquals(response.getStatusCode(), 200, "Error, record was not updated");
                break;
            case "deleted":
                Assert.assertEquals(response.getStatusCode(), 204, "Error, record was not deleted");
                break;
            default:
                throw new IllegalStateException("This status code is not supported yet!");
        }
    }

    @When("User perform GET request for {string} endpoint")
    public void userPerformGETRequest(String endpoint){
        this.response = given()
                .when()
                .get(endpoint);
    }

    @Then("User get a {int} http status code")
    public void userGetHttpStatusCode(int expectedCode){
        Assert.assertEquals(response.getStatusCode(), expectedCode, "Error message is empty or incorrect");
    }

    @When("User tries to update a record {string}")
    public void userTriesToUpdateARecord(String endpoint){
        this.response = given()
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"zion resident\"\n" +
                        "}")
                .when()
//                .put(endpoint); optional
                .patch(endpoint);

    }

    @When("User tries to create a new record {string}")
    public void userTriesToCreateARecord(String endpoint){
        this.response = given()
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"leader\"\n" +
                        "}")
                .when()
                .post(endpoint);
    }
}
