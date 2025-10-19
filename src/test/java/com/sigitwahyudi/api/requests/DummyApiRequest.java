package com.sigitwahyudi.api.requests;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class DummyApiRequest {
    private static final String BASE_URL = "https://dummyapi.io/data/v1/";
    private static final String APP_ID = "63a804408eb0cb069b57e43a";

    // GET - List Users
    public static Response getUsers(int limit) {
        return RestAssured
                .given()
                .header("app-id", APP_ID)
                .queryParam("limit", limit)
                .get(BASE_URL + "user");
    }

    // GET - Detail User by ID
    public static Response getUserById(String id) {
        return RestAssured
                .given()
                .header("app-id", APP_ID)
                .get(BASE_URL + "user/" + id);
    }

    // POST - Create User
    public static Response createUser(String bodyJson) {
        return RestAssured
                .given()
                .header("app-id", APP_ID)
                .contentType("application/json")
                .body(bodyJson)
                .post(BASE_URL + "user/create");
    }

    // DELETE - Delete User by ID
    public static Response deleteUser(String id) {
        return RestAssured
                .given()
                .header("app-id", APP_ID)
                .delete(BASE_URL + "user/" + id);
    }
}
