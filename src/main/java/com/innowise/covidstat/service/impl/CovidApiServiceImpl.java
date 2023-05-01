package com.innowise.covidstat.service.impl;

import com.innowise.covidstat.client.CovidApiClient;
import com.innowise.covidstat.client.model.DayInfo;
import com.innowise.covidstat.service.CovidApiService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class CovidApiServiceImpl implements CovidApiService {

    @RestClient
    private CovidApiClient covidApiClient;

    @Override
    public Uni<List<DayInfo>> getDayInfosByCountryAndRange(
        String country, String from, String to) {

        return covidApiClient.getByCountyNameAndDateRange(country, from, to);
    }
}
