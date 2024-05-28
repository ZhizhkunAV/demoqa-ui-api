package tests;

import io.restassured.response.Response;
import models.lombok.AuthRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import pageobjects.ProfilePage;

import static api.specifications.SpecForAllTests.*;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;


public class DeleteBookInStoreTest extends TestBase {
    ProfilePage profilepage = new ProfilePage();

    @DisplayName("Авторизация пользователя")
    @Test
    void addBookToCollectionTest() {

// Авторизация пользователя - post

        AuthRequest authRequest = new AuthRequest();
        authRequest.setUserName("First");
        authRequest.setPassword("Kjrj1923@Q");

        Response authResponse = given(LoginRequestSpecification)
                .body(authRequest)
                .when()
                .post("")
                .then()
                .spec(LoginResponseSpecification)
                .extract().response();

        String isbn = "9781449365035";
        String bookData = format("{\"userId\":\"%s\",\"collectionOfIsbns\":[{\"isbn\":\"%s\"}]}",
                authResponse.path("userId"), isbn);

// Добавляем книгу пользователю post

        given(AddOneBookRequestSpecification)

                .header("Authorization", "Bearer " + authResponse.path("token"))
                .body(bookData)
                .when()
                .post("")
                .then()
                .spec(AddOneBookResponseSpecification);

// Удаление книг

        given(DeleteOneBookRequestSpecification)
                .header("Authorization", "Bearer " + authResponse.path("token"))
                .queryParams("UserId", authResponse.path("userId"))
                .when()
                .delete("")
                .then()
                .spec(DeleteOneBookResponseSpecification);

// Открываем браузер вставляем куки

        open("/images/bookimage0.jpg");
        getWebDriver().manage().addCookie(new Cookie("userID", authResponse.path("userId")));
        getWebDriver().manage().addCookie(new Cookie("expires", authResponse.path("expires")));
        getWebDriver().manage().addCookie(new Cookie("token", authResponse.path("token")));

        profilepage.openProfilePage()
                .checkUserName()
                .checkNameBookInList()
                .checkBook();
    }
}