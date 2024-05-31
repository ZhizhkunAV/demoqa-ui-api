package api.specifications;

import lombok.EqualsAndHashCode;
import models.lombok.AuthRequest;
import models.lombok.AuthResponse;
import tests.TestBase;

import static api.specifications.SpecForAllTests.requestSpecification;
import static io.restassured.RestAssured.given;
import static tests.TestData.password;
import static tests.TestData.username;

@EqualsAndHashCode(callSuper = true)
public class AuthApi extends TestBase {
    public static AuthResponse authorizeRequest() {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUserName(username);
        authRequest.setPassword(password);

        return given(requestSpecification)
                .body(authRequest)
                .when()
                .post("/Account/v1/Login")
                .then()
                .extract().as(AuthResponse.class);
    }
}
