package org.com.auth;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.ws.rs.core.Response;
import org.com.entity.User;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class AuthTest {
    private static final String AUTH_REQUEST_JSON = "{\"username\": \"liljohn\", \"password\": \"maryjane\"}";
    
    @Test
    public void testInvalidCredentials() {
        given()
            .contentType(ContentType.JSON)
            .body(AUTH_REQUEST_JSON)
            .when()
            .post("/auth/token")
            .then()
            .statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }
    
    @Test
    public void testRegister() {
        given()
            .contentType(ContentType.JSON)
            .body(AUTH_REQUEST_JSON)
            .when()
            .post("/auth/register")
            .then()
            .statusCode(Response.Status.CREATED.getStatusCode());
        given()
            .contentType(ContentType.JSON)
            .body(AUTH_REQUEST_JSON)
            .when()
            .post("/auth/token")
            .then()
            .statusCode(Response.Status.OK.getStatusCode());
        System.out.println(User.count());
        System.out.println("test");
        // Uses new db for the test. Need to set the junit to use dev db
    }
}