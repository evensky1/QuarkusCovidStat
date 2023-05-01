package com.innowise.covidstat.client;

import com.innowise.covidstat.client.model.DayInfo;
import io.smallrye.common.annotation.NonBlocking;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/total")
@RegisterRestClient(configKey = "covid-client")
public interface CovidApiClient {

    @GET
    @NonBlocking
    @Path("/country/{country}/status/confirmed")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Uni<List<DayInfo>> getByCountyNameAndDateRange(
        @PathParam("country") String countryName,
        @QueryParam("from") String from,
        @QueryParam("to") String to);

}
