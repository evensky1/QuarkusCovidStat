package com.innowise.covidstat.service;

import com.innowise.covidstat.client.model.DayInfo;
import io.smallrye.mutiny.Uni;
import java.util.List;

public interface CovidApiService {

    Uni<List<DayInfo>> getDayInfosByCountryAndRange(String country, String from, String to);
}
