package com.innowise.covidstat.service.impl;

import com.innowise.covidstat.model.CountryDayStatistic;
import com.innowise.covidstat.model.CountryStatistic;
import com.innowise.covidstat.service.CountryStatisticService;
import com.innowise.covidstat.service.DayStatisticService;
import io.quarkus.cache.CacheResult;
import io.quarkus.logging.Log;
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

        var statStream = countryNames.stream()
            .map(countryName -> getCountryStatistic(countryName, from, to));

        return Uni.join().all(statStream.toList()).andFailFast()
            .onItem().invoke(statList -> statList.remove(null));
    }


    @CacheResult(cacheName = "country-stat-cache")
    Uni<CountryStatistic> getCountryStatistic(
        String countryName, Instant from, Instant to) {

        return dayStatisticService.getDayStatList(countryName, from, to)
            .onItem().transform(this::countCountryStat)
            .onFailure().invoke(e -> Log.errorf("\"%s\" was thrown on [%s]", e.getMessage(), countryName))
            .onFailure().recoverWithNull();
    }

    private CountryStatistic countCountryStat(List<CountryDayStatistic> stats) {

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
    }
}
