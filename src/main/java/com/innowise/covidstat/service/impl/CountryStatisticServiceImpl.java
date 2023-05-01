package com.innowise.covidstat.service.impl;

import com.innowise.covidstat.model.CountryDayStatistic;
import com.innowise.covidstat.model.CountryStatistic;
import com.innowise.covidstat.service.CountryStatisticService;
import com.innowise.covidstat.service.DayStatisticService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;

@ApplicationScoped
public class CountryStatisticServiceImpl implements CountryStatisticService {

    @Inject
    DayStatisticService dayStatisticService;

    @Override
    public Uni<List<CountryStatistic>> getCountryStatisticList(
        List<String> countryNames, Instant from, Instant to) {

        var stream = countryNames.parallelStream()
            .map(country -> dayStatisticService.getDayStatList(country, from, to))
            .map(this::getCountryStatistic);

        return Uni.join().all(stream.toList()).andFailFast();
    }

    private Uni<CountryStatistic> getCountryStatistic(
        Uni<List<CountryDayStatistic>> dayStats) {

        return dayStats.onItem().transform(stats -> {

            var maxDay = stats.stream()
                .max(Comparator.comparing(CountryDayStatistic::newCases))
                .orElseThrow();

            var minDay = stats.stream()
                .min(Comparator.comparing(CountryDayStatistic::newCases))
                .orElseThrow();

            return new CountryStatistic(
                stats.get(0).country(),
                maxDay.newCases(), maxDay.date(),
                minDay.newCases(), minDay.date()
            );
        });
    }
}
