package com.innowise.covidstat.service;

import com.innowise.covidstat.model.CountryDayStatistic;
import io.smallrye.mutiny.Uni;
import java.time.Instant;
import java.util.List;

public interface DayStatisticService {

    Uni<List<CountryDayStatistic>> getDayStatList(String country, Instant from, Instant to);
}
