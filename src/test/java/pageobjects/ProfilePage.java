package pageobjects;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ProfilePage {

    private final SelenideElement userName = $("#userName-value"),
            tableOfBook = $(".rt-tbody"),
            linkBook = $("[href='/profile?book=9781449325862']");

    @Step("Open profile page")
    public ProfilePage openProfilePage() {
        open("/profile");

        return this;
    }

    @Step("Пользователь авторизован - имя пользователя отображается на странице")
    public ProfilePage checkUserName() {
        userName.shouldHave(text("First"));

        return this;
    }
    @Step("Пользователь авторизован - имя пользователя отображается на странице")
    public ProfilePage checkNameBookInList() {
        tableOfBook.shouldNotHave(text("Speaking JavaScript"));

        return this;
    }

    @Step("Пользователь авторизован - имя пользователя отображается на странице")
    public ProfilePage checkBook() {
        linkBook.shouldHave(hidden);

        return this;
    }
}
