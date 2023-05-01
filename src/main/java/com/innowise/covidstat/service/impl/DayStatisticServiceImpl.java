package com.innowise.covidstat.service.impl;

import com.innowise.covidstat.client.model.DayInfo;
import com.innowise.covidstat.model.CountryDayStatistic;
import com.innowise.covidstat.service.CovidApiService;
import com.innowise.covidstat.service.DayStatisticService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class DayStatisticServiceImpl implements DayStatisticService {

    @Inject
    CovidApiService covidApiService;

    @Override
    public Uni<List<CountryDayStatistic>> getDayStatList(
        String country, Instant from, Instant to) {

        to = to.plus(2, ChronoUnit.DAYS); //to count last day stat

        return covidApiService
            .getDayInfosByCountryAndRange(country, from.toString(), to.toString())
            .onItem().transform(this::countDayStatistic);
    }

    private List<CountryDayStatistic> countDayStatistic(List<DayInfo> infos) {
        var dayStatisticList = new ArrayList<CountryDayStatistic>();

        for (int i = 0; i < infos.size() - 2; i++) {

            var current = infos.get(i);
            var next = infos.get(i + 1);

            dayStatisticList.add(
                new CountryDayStatistic(
                    current.country(), next.cases() - current.cases(), current.date())
            );
        }

        return dayStatisticList;
    }
}
