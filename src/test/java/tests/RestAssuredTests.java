package tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import models.UpdateUserModel;
import org.testng.annotations.Test;
import utilities.GenerateFakeMessage;

import java.io.File;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

public class RestAssuredTests {

    @Test
    public void checkResponseCodeTest() {
        RestAssured
                .given()
                .log()
                .all()
                .when()
                .get("https://reqres.in/api/unknown/23")
                .then()
                .log()
                .all()
                .statusCode(404);
    }

    @Test
    public void checkFieldsInResponse() {
        RestAssured
                .given()
                .log()
                .all()
                .when()
                .get("https://reqres.in/api/unknown")
                .then()
                .log()
                .all()
                .statusCode(200)
                .body("per_page", equalTo(6))
                .body("total", equalTo(12))
                .body("total_pages", instanceOf(Integer.class))
                .body("data[0].id", instanceOf(Integer.class));
    }

    @Test
    public void checkBodyTest() {
        JsonPath expectedJson = new JsonPath(new File("src/test/resources/resource.json"));
        RestAssured
                .given()
                .log()
                .all()
                .when()
                .get("https://reqres.in/api/unknown/2")
                .then()
                .log()
                .all()
                .body("", equalTo(expectedJson.getMap("")));
    }

    @Test
    public void userUpdateViaPutTest() {
        UpdateUserModel updateUserModel = new UpdateUserModel();
        updateUserModel.setName(GenerateFakeMessage.getFirstName());
        updateUserModel.setJob(GenerateFakeMessage.getFirstName());
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .and()
                .body(updateUserModel)
                .when()
                .put("https://reqres.in/api/users/2")
                .then()
                .log()
                .all()
                .statusCode(200);
    }

    @Test
    public void userUpdateViaPatchTest() {
        UpdateUserModel updateUserModel = new UpdateUserModel();
        updateUserModel.setName(GenerateFakeMessage.getFirstName());
        updateUserModel.setJob(GenerateFakeMessage.getFirstName());
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .and()
                .body(updateUserModel)
                .when()
                .patch("https://reqres.in/api/users/2")
                .then()
                .log()
                .all()
                .statusCode(200);
    }


    @Test
    public void userDeleteTest() {
        RestAssured
                .given()
                .log()
                .all()
                .when()
                .delete("https://reqres.in/api/users/2")
                .then()
                .log()
                .all()
                .statusCode(204);
    }

    @Test
    public void successfulRegisterTest() {
        JsonPath expectedJson = new JsonPath(new File("src/test/resources/token.json"));
        UpdateUserModel updateUserModel = new UpdateUserModel();
//        updateUserModel.setEmail(GenerateFakeMessage.getEmail());
//        updateUserModel.setPassword(GenerateFakeMessage.getPassword());
        updateUserModel.setEmail("eve.holt@reqres.in");
        updateUserModel.setPassword("pistol");
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .and()
                .body(updateUserModel)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log()
                .all()
                .body("", equalTo(expectedJson.getMap("")))
                .statusCode(200);

    }

    @Test
    public void unsuccessfulRegisterTest() {
        UpdateUserModel updateUserModel = new UpdateUserModel();
        JsonPath expectedJson = new JsonPath(new File("src/test/resources/missingpassword.json"));
        updateUserModel.setEmail(GenerateFakeMessage.getEmail());
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .and()
                .body(updateUserModel)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log()
                .all()
                .body("", equalTo(expectedJson.getMap("")))
                .statusCode(400);

    }

    @Test
    public void successfulLoginTest() {
        UpdateUserModel updateUserModel = new UpdateUserModel();
        JsonPath expectedJson = new JsonPath(new File("src/test/resources/logintoken.json"));
        updateUserModel.setEmail("eve.holt@reqres.in");
        updateUserModel.setPassword("cityslicka");
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .and()
                .body(updateUserModel)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log()
                .all()
                .body("", equalTo(expectedJson.getMap("")))
                .statusCode(200);

    }

    @Test
    public void unsuccessfulLoginTest() {
        UpdateUserModel updateUserModel = new UpdateUserModel();
        JsonPath expectedJson = new JsonPath(new File("src/test/resources/missingpassword.json"));
        updateUserModel.setEmail(GenerateFakeMessage.getEmail());
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .and()
                .body(updateUserModel)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log()
                .all()
                .body("", equalTo(expectedJson.getMap("")))
                .statusCode(400);
    }

    @Test
    public void delayedResponseTest() {
        JsonPath expectedJson = new JsonPath(new File("src/test/resources/delayedresponse.json"));
        RestAssured
                .given()
                .log()
                .all()
                .when()
                .get("https://reqres.in/api/users?delay=3")
                .then()
                .log()
                .all()
                .body("", equalTo(expectedJson.getMap("")));

    }

}
