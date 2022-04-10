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
    public void userUpdateViaPatchTest(){
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
    public void userDeleteTest(){
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
    public void successfulRegisterTest(){
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
    public void unsuccessfulRegisterTest(){
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
}
