package com.innowise.covidstat;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.innowise.covidstat.model.CountryStatistic;
import com.innowise.covidstat.model.StatisticRequest;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;

@QuarkusTest
class StatisticControllerTest {

    @Test
    void createStatisticTest() throws JsonProcessingException {

        var from = Instant.parse("2023-03-01T00:00:00.00Z");
        var to = Instant.parse("2023-03-02T00:00:00.00Z");

        var requestEntity = new StatisticRequest(List.of("lithuania"), from, to);

        var expectedEntity = List.of(new CountryStatistic("Lithuania", 774, to, 43, from));
        var mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        given()
            .when()
                .body(requestEntity)
                .contentType(ContentType.JSON)
                .post("/api/stat")
            .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body(is(mapper.writeValueAsString(expectedEntity)));
    }
}
