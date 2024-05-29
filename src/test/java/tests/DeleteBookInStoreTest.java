package tests;

import io.restassured.response.Response;
import models.lombok.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import pageobjects.ProfilePage;

import java.util.ArrayList;
import java.util.List;

import static api.specifications.SpecForAllTests.*;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static io.qameta.allure.Allure.step;


public class DeleteBookInStoreTest extends TestBase {
    String isbn = "9781449365035";
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

        String userID = authResponse.getUserId();


// Добавляем книгу пользователю post

        IsbnRequest isbnRequest = new IsbnRequest(); //добавляем взаимодействие с моделью isbn чтобы получить isbn.
        isbnRequest.setIsbn(isbn);

        AddBookRequest addBookRequest = new AddBookRequest(); //добавляем взаимодействие с моделью чтобы получить isbn.
        List<AddBookRequest> isbnList = new ArrayList<>();
        isbnList.add(isbnRequest);

        addBookRequest.setUserId(userID);
        addBookRequest.setCollectionOfIsbns(isbnList);


        AddBookResponse addBookResponse =
                step("Добавление 1 книги в спсок - корзину (в профиль клиента)", () ->
                        given(AddOneBookRequestSpecification)
                                .header("Authorization", "Bearer " + authResponse.getToken())
                                .body(addBookRequest)
                                .when()
                                .post("")
                                .then()
                                .spec(AddOneBookResponseSpecification)
                                .extract().as(AddBookResponse.class)
                );


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