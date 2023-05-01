package com.innowise.covidstat.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

public record DayInfo(
    @JsonProperty(value = "Country") String country,
    @JsonProperty(value = "Cases") int cases,
    @JsonProperty(value = "Status") String status,
    @JsonProperty(value = "Date") Instant date
) {

}
