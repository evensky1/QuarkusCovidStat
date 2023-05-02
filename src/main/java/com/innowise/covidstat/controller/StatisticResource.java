package com.innowise.covidstat.controller;

import com.innowise.covidstat.model.CountryStatistic;
import com.innowise.covidstat.model.StatisticRequest;
import com.innowise.covidstat.service.CountryStatisticService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import org.jboss.resteasy.reactive.ResponseStatus;

@Path("/api/stat")
public class StatisticResource {

    @Inject
    CountryStatisticService countryStatisticService;

    @POST
    @ResponseStatus(201)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<List<CountryStatistic>> requestCountryStatistic(StatisticRequest statisticRequest) {

        return countryStatisticService.getCountryStatisticList(
            statisticRequest.countries(), statisticRequest.from(), statisticRequest.to());
    }
}
