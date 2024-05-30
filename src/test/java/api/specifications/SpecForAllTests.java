package api.specifications;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;

public class SpecForAllTests {

    public static RequestSpecification requestSpecification = with()
            .filter(withCustomTemplates())
            .log().uri()
            .log().method()
            .log().all()
            .contentType(JSON);

    public static ResponseSpecification responseSpecStatusCode200 = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(ALL)
            .build();


    public static ResponseSpecification responseSpecStatusCode201 = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .log(ALL)
            .build();


    public static ResponseSpecification responseSpecStatusCode204 = new ResponseSpecBuilder()
            .expectStatusCode(204)
            .log(ALL)
            .build();
}