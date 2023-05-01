package com.innowise.covidstat.service;

import com.innowise.covidstat.model.CountryStatistic;
import io.smallrye.mutiny.Uni;
import java.time.Instant;
import java.util.List;

public interface CountryStatisticService {

    Uni<List<CountryStatistic>> getCountryStatisticList(
        List<String> countryNames, Instant from, Instant to);

}
