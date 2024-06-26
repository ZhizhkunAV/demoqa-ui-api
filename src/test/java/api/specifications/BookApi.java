package api.specifications;

import io.qameta.allure.Step;
import models.lombok.AddBookRequest;
import models.lombok.AddBookResponse;
import models.lombok.AuthResponse;
import models.lombok.IsbnRequest;

import java.util.ArrayList;
import java.util.List;

import static api.specifications.AuthApi.authorizeRequest;
import static api.specifications.SpecForAllTests.*;
import static io.restassured.RestAssured.given;

public class BookApi {
    String isbn = "9781449365035";
    String userID, token;

    @Step("Удаление книг через отправку API запросн, на случай если книги были добавлены ранее")
    public void deleteAllBooks(String userId) {
        given(sendRequestWithoutBodySpec)
                .queryParams("UserId", userId)
                .when()
                .delete("/BookStore/v1/Books")
                .then()
                .spec(responseSpecStatusCode204);
    }

    @Step("Добавление книги")
    public void addBooks() {
        AuthResponse authResponse = new AuthResponse();
        AddBookRequest addBookRequest = new AddBookRequest();

        addBookRequest.setUserId(authorizeRequest().getUserId());
        IsbnRequest isbnRequest = new IsbnRequest(); //добавляем взаимодействие с моделью isbn чтобы получить isbn значение.
        isbnRequest.setIsbn(isbn);
        userID = authResponse.getUserId();
        List<IsbnRequest> isbnList = new ArrayList<>();
        isbnList.add(isbnRequest);
        token = authResponse.getToken();
        addBookRequest.setCollectionOfIsbns(isbnList);

        given(sendRequestWithBodySpec)
                .body(addBookRequest)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .spec(responseSpecStatusCode201)
                .extract().as(AddBookResponse.class);

    }
}