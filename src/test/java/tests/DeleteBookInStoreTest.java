package tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static java.lang.String.format;

public class DeleteBookInStoreTest extends TestBase{

    @DisplayName("Авторизация пользователя")
    @Test
    void addBookToCollectionTest()   {

// Авторизация пользователя - post

        String authData = "{\"userName\":\"First\",\"password\":\"Kjrj1923@Q\"}";

        Response authResponse = given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(authData)
                .when()
                .post("/Account/v1/Login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().response();


        String isbn = "9781449365035";
        String bookData = format("{\"userId\":\"%s\",\"collectionOfIsbns\":[{\"isbn\":\"%s\"}]}",
                authResponse.path("userId") , isbn);

// Добавляем книгу пользователю post

        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .header("Authorization", "Bearer " + authResponse.path("token"))
                .body(bookData)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .log().status()
                .log().body()
                .statusCode(201);


        // Удаление книг

        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .header("Authorization", "Bearer " + authResponse.path("token"))
                .queryParams("UserId", authResponse.path("userId"))
                .when()
                .delete("/BookStore/v1/Books")
                .then()
                .log().status()
                .log().body()
                .statusCode(204);

// Открываем браузер вставляем куки

        open("/images/bookimage0.jpg");
        getWebDriver().manage().addCookie(new Cookie("userID", authResponse.path("userId")));
        getWebDriver().manage().addCookie(new Cookie("expires", authResponse.path("expires")));
        getWebDriver().manage().addCookie(new Cookie("token", authResponse.path("token")));

        open("/profile");
        $("#userName-value").shouldHave(text("First"));
        $(".rt-tbody").shouldNotHave(text("Speaking JavaScript"));
        $("[href='/profile?book=9781449325862']").shouldHave(hidden);

    }
}