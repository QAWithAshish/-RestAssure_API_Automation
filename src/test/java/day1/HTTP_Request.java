package day1;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;


/*
 Given() --> We will provide Content-type, set cookies, add auth, add param, set Headers info
 When() --> Will provide http methods like GET, POST, PUT, DELETE.
 Then() --> Will validate status code, extract response, extract headers cookies & response body..
*/

public class HTTP_Request {

    int id;

    @Test(description = "Get request to get all data from Page 2")
    public void getUsersRequest() {

        when().get("https://reqres.in/api/users?page=2").

                then().statusCode(200).body("page", equalTo(2)).log().all();

    }

    @Test(priority = 2, description = "Create user using post method ")
    public void createUsers() {

        Map<String, Object> data = new HashMap();
        data.put("name", "Ashish");
        data.put("job", "ATE");


        given()
                .contentType("application/json").body(data)
                .when().post("https://reqres.in/api/users")
                .then().statusCode(201).log().all();

    }

    @Test(priority = 2, description = "get the id from response")
    public void getIdFromResponse() {

        Map<String, Object> data = new HashMap();
        data.put("name", "Ashish");
        data.put("job", "ATE");

        id = given()
                .contentType("application/json").body(data)
                .when().post("https://reqres.in/api/users")
                .jsonPath().getInt("id");
    }

    @Test(priority = 3, dependsOnMethods = {"getIdFromResponse"}, description = "Using id update the data of the user")
    public void updateUsersUsingID() {

        Map<String, Object> data = new HashMap();
        data.put("name", "Rajat");
        data.put("job", "MT");

        given()
                .contentType("application/json").body(data)
                .when().put("https://reqres.in/api/users/" + id)
                .then()
                .statusCode(200)
                .log().all();
    }

    @Test(priority = 4, description = "Using id delete the data of the user")
    public void deleteUsersUsingID() {


        given()

                .when().delete("https://reqres.in/api/users/" + id)
                .then()
                .statusCode(204)
                .log().all();
    }


}
