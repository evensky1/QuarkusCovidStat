package com.innowise.covidstat.model;

import java.time.Instant;

public record CountryDayStatistic(
    String country,
    int newCases,
    Instant date
) {

}
