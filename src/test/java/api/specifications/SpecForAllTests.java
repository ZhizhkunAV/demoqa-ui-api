package api.specifications;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.*;
import static io.restassured.http.ContentType.JSON;

public class SpecForAllTests {

    public static RequestSpecification LoginRequestSpecification = with()
            .filter(withCustomTemplates())
            .contentType(JSON)
            .log().all()
            .basePath("/Account/v1/Login");

    public static ResponseSpecification LoginResponseSpecification = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(ALL)
            .build();


    public static RequestSpecification AddOneBookRequestSpecification = with()
            .filter(withCustomTemplates())
            .log().uri()
            .log().method()
            .log().body()
            .contentType(JSON)
            .basePath("/BookStore/v1/Books");

    public static ResponseSpecification AddOneBookResponseSpecification = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .log(ALL)
            .build();

    public static RequestSpecification DeleteOneBookRequestSpecification = with()
            .filter(withCustomTemplates())
            .log().uri()
            .log().method()
            .log().body()
            .contentType(JSON)
            .basePath("/BookStore/v1/Books");

    public static ResponseSpecification DeleteOneBookResponseSpecification = new ResponseSpecBuilder()
            .expectStatusCode(204)
            .log(ALL)
            .build();
}