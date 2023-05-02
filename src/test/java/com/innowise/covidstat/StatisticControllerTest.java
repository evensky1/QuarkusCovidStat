package com.innowise.covidstat;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.innowise.covidstat.model.StatisticRequest;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;

@QuarkusTest
class StatisticControllerTest {

    @Test
    void createStatisticTest() {

        var from = Instant.parse("2023-03-01T00:00:00.00Z");
        var to = Instant.parse("2023-03-02T00:00:00.00Z");

        var requestEntity = new StatisticRequest(List.of("lithuania"), from, to);

        var mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        given()
            .when()
                .body(requestEntity)
                .contentType(ContentType.JSON)
                .post("/api/stat")
            .then()
                .statusCode(201)
                .body("country", contains("Lithuania"))
                .body("maxNewCases", contains(380))
                .body("maxNewCasesDate", contains("2023-03-01T00:00:00Z"))
                .body("minNewCases", contains(359))
                .body("minNewCasesDate", contains("2023-03-02T00:00:00Z"));
    }

    @Test
    void emptyRequestTest() {

        var from = Instant.parse("2023-03-01T00:00:00.00Z");
        var to = Instant.parse("2023-03-02T00:00:00.00Z");

        var requestEntity = new StatisticRequest(List.of(), from, to);

        var mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        given()
            .when()
                .body(requestEntity)
                .contentType(ContentType.JSON)
            .post("/api/stat")
                .then()
                .statusCode(201)
                .body("", empty());
    }

    @Test
    void corruptedCountryNameTest() {

        var from = Instant.parse("2023-03-01T00:00:00.00Z");
        var to = Instant.parse("2023-03-02T00:00:00.00Z");

        var requestEntity = new StatisticRequest(List.of("lithuania", "nonexisting"), from, to);

        var mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        given()
            .when()
                .body(requestEntity)
                .contentType(ContentType.JSON)
                .post("/api/stat")
            .then()
                .statusCode(201)
                .body("country", contains("Lithuania"))
                .body("maxNewCases", contains(380))
                .body("maxNewCasesDate", contains("2023-03-01T00:00:00Z"))
                .body("minNewCases", contains(359))
                .body("minNewCasesDate", contains("2023-03-02T00:00:00Z"));
    }
}
