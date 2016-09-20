package funbus.rest;

import com.google.inject.Inject;
import funbus.model.DirectRouteResponse;
import funbus.service.IRouteService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response.Status;

@Path("direct")
public class RouteResource {

    @Inject
    private IRouteService routeService;

    @GET
    public DirectRouteResponse getRoute(@QueryParam("dep_sid") int departureStation,
                                        @QueryParam("arr_sid") int arrivalStation) {

        if (departureStation == arrivalStation) {
            throw new RestException(Status.BAD_REQUEST, "Departure and arrival stations must be different");
        }

        return routeService.existsDirectRoute(departureStation, arrivalStation) ?
                DirectRouteResponse.routeExists(departureStation, arrivalStation)
                    : DirectRouteResponse.noRoute(departureStation, arrivalStation);
    }
}
