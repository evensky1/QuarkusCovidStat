package com.innowise.covidstat.model;

import java.time.Instant;

public record CountryStatistic(
    String country,
    int maxNewCases, Instant maxNewCasesDate,
    int minNewCases, Instant minNewCasesDate
) {

}
