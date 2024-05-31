package tests;

import api.specifications.BookApi;
import helpers.WithLogin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.ProfilePage;

import static api.specifications.AuthApi.authorizeRequest;
import static tests.TestData.bookName;
import static tests.TestData.username;

@DisplayName("Автотестирование UI+API")
public class DeleteBooksTests extends TestBase {
    ProfilePage profilePage = new ProfilePage();
    BookApi bookApi = new BookApi();

    @Test
    @Tag("alls")
    @WithLogin
    @DisplayName("Удаление книги из корзины онлайн магазина")
    void deleteAllBooksTest() {
        bookApi.deleteAllBooks(authorizeRequest().getUserId());
        bookApi.addBooks();
        profilePage.openProfilePage()
                .checkUserName(username)
                .checkHaveBook(bookName)
                .deleteBook()
                .checkBookDeleted(bookName);
    }
}