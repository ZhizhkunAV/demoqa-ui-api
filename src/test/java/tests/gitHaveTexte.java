package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;


@DisplayName("тест")
public class gitHaveTexte{
    @Test
    void loginWithApiTest() {
        step("Get authorization cookie by api and set it to browser", () -> {
            String authCookieKey = "NOPCOMMERCE.AUTH";
            String authCookieValue = given()
                    .contentType("application/x-www-form-urlencoded")
                    .formParam("UserName", "testtestov31")
                    .formParam("Password", "Testtestov31_%")
                    .when()
                    .post("https://demoqa.com/profile")
                    .then()
                    .log().all()
                    .statusCode(200)
                    .extract()
                    .cookie(authCookieKey);


        });
    }
}