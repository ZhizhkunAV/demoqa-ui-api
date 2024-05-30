package tests;

import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import models.lombok.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.openqa.selenium.Cookie;
import pages.ProfilePage;

import java.util.ArrayList;
import java.util.List;

import static api.specifications.SpecForAllTests.*;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static tests.TestData.password;
import static tests.TestData.username;

@DisplayName("Автотестирование UI+API")
public class DeleteBookInStoreTest extends TestBase {
    public String isbn = "9781449365035";
    public String userID, token;

    @DisplayName("Удаление книги из корзины онлайн магазина")
    @Severity(SeverityLevel.BLOCKER)
    @Tag("alls")
    @Owner("ZhizhkunAV")
    @org.junit.jupiter.api.Test
    void addBookToCollectionTest() {
        // Авторизация пользователя - post
        step("Авторизация пользователя через API с добавлением cookies авторизации через UI", () -> {

            AuthRequest authRequest = new AuthRequest();
            authRequest.setUserName(username);
            authRequest.setPassword(password);

            AuthResponse authResponse =
                    step("Отправка запроса на авторизацию", () ->
                            given(LoginRequestSpecification)
                                    .body(authRequest)
                                    .when()
                                    .post("")
                                    .then()
                                    .spec(LoginResponseSpecification)
                                    .extract().as(AuthResponse.class)
                    );
            open("/favicon.png");
            getWebDriver().manage().addCookie(new Cookie("userID", authResponse.getUserId()));
            getWebDriver().manage().addCookie(new Cookie("expires", authResponse.getExpires()));
            getWebDriver().manage().addCookie(new Cookie("token", authResponse.getToken()));

            userID = authResponse.getUserId();
            token = authResponse.getToken();

        });

        step("Удаление книги из профиля(корзины) через API", () -> {

            step("Удаление книг через отправку API запросн, на случай если книги были добавлены ранее", () ->
                    given(DeleteOneBookRequestSpecification)
                            .header("Authorization", "Bearer " + token)
                            .queryParams("UserId", userID)
                            .when()
                            .delete("")
                            .then()
                            .spec(DeleteOneBookResponseSpecification)
            );
        });


        step("Добавление книги", () -> {
            IsbnRequest isbnRequest = new IsbnRequest(); //добавляем взаимодействие с моделью isbn чтобы получить isbn значение.
            isbnRequest.setIsbn(isbn);

            AddBookRequest addBookRequest = new AddBookRequest(); //добавляем взаимодействие с моделью общего запроса.
            List<IsbnRequest> isbnList = new ArrayList<>();
            isbnList.add(isbnRequest);

            addBookRequest.setUserId(userID);
            addBookRequest.setCollectionOfIsbns(isbnList);

            AddBookResponse addBookResponse =
                    given(AddOneBookRequestSpecification)
                            .header("Authorization", "Bearer " + token)
                            .body(addBookRequest)
                            .when()
                            .post("")
                            .then()
                            .spec(AddOneBookResponseSpecification)
                            .extract().as(AddBookResponse.class);
        });


        // Удаление книг
/*        step("Удаление ранее добавленной книги", () -> {
            given(DeleteOneBookRequestSpecification)
                    .header("Authorization", "Bearer " + token)
                    .queryParams("UserId", userID)
                    .when()
                    .delete("")
                    .then()
                    .spec(DeleteOneBookResponseSpecification);
        });
*/
        step("Проверка отсутствия книги в профиле клиента (корзине) через UI", () -> {
            ProfilePage profilepage = new ProfilePage();
            profilepage.openProfilePage()
                    .checkUserName(username)
                    .checkHaveBook("Speaking JavaScript")
                    .deleteBook()
                    .checkBookDeleted("Speaking JavaScript");;
        });
    }
}