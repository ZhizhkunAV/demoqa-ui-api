package tests;

import io.restassured.response.Response;
import models.lombok.AddBookRequest;
import models.lombok.AuthRequest;
import models.lombok.AuthResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import pageobjects.ProfilePage;

import static api.specifications.SpecForAllTests.*;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static io.qameta.allure.Allure.step;


public class DeleteBookInStoreTest extends TestBase {
    ProfilePage profilepage = new ProfilePage();

    @DisplayName("Удаление книги из корзины онлайн магазина")
    @Test
    void addBookToCollectionTest() {

// Авторизация пользователя - post

        AuthRequest authRequest = new AuthRequest();
        authRequest.setUserName("First");
        authRequest.setPassword("Kjrj1923@Q");

        AuthResponse authResponse =
                step("Авторизация пользователя", () ->
                        given(LoginRequestSpecification)
                                .body(authRequest)
                                .when()
                                .post("")
                                .then()
                                .spec(LoginResponseSpecification)
                                .extract().as(AuthResponse.class)
                );

        String isbn = "9781449365035";
        String bookData = format("{\"userId\":\"%s\",\"collectionOfIsbns\":[{\"isbn\":\"%s\"}]}",
                authResponse.getUserId(), isbn);

// Добавляем книгу пользователю post
        AddBookRequest addBookRequest = new AddBookRequest();
        addBookRequest.setUserId();
        addBookRequest.setCollectionOfIsbns();

        given(AddOneBookRequestSpecification)

                .header("Authorization", "Bearer " + authResponse.getToken())
                .body(addBookRequest)
                .when()
                .post("")
                .then()
                .spec(AddOneBookResponseSpecification);

// Удаление книг

        given(DeleteOneBookRequestSpecification)
                .header("Authorization", "Bearer " + authResponse.getToken())
                .queryParams("UserId", authResponse.getUserId())
                .when()
                .delete("")
                .then()
                .spec(DeleteOneBookResponseSpecification);

// Открываем браузер вставляем куки

        open("/images/bookimage0.jpg");
        getWebDriver().manage().addCookie(new Cookie("userID", authResponse.getUserId()));
        getWebDriver().manage().addCookie(new Cookie("expires", authResponse.getExpires()));
        getWebDriver().manage().addCookie(new Cookie("token", authResponse.getToken()));

        profilepage.openProfilePage()
                .checkUserName()
                .checkNameBookInList()
                .checkBook();


    }
}