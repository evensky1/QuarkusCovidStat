package com.innowise.covidstat.model;

import java.time.Instant;
import java.util.List;

public record StatisticRequest(
    List<String> countries,
    Instant from,
    Instant to
) {

}
