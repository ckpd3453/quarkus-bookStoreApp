package org.raku.controller;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class UsersControllerIT {

    @Test
    void testRegistration() {
        String body = """
                {
                    "userName":"chand",
                    "email":"chand@test.com",
                    "mobileNum":"9876543210",
                    "password":"Password@123",
                    "role":"Admin"
                }
                """;

        given()
                .contentType("application/json")
                .body(body)
                .when()
                .post("/bookstore/user")
                .then()
                .statusCode(200);
    }
}
