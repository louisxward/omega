package org.com.auth;

import io.restassured.http.ContentType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

class AuthTest {
    
    @Test
    void testInvalidCredentials() {
        given()
            .contentType(ContentType.JSON)
            .body("{\"username\": \"liljohn\", \"password\": \"maryjane\"}")
            .when()
            .post("/auth/token")
            .then()
            .statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }
    
    @Test
    void testRegister() {
        given()
            .contentType(ContentType.JSON)
            .body("{\"username\": \"liljohn\", \"password\": \"maryjane\"}")
            .when()
            .post("/auth/register")
            .then()
            .statusCode(Response.Status.CREATED.getStatusCode());
        given()
            .contentType(ContentType.JSON)
            .body("{\"username\": \"liljohn\", \"password\": \"maryjane\"}")
            .when()
            .post("/auth/token")
            .then()
            .statusCode(Response.Status.OK.getStatusCode());
    }
}