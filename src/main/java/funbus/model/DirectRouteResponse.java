package funbus.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;

public class DirectRouteResponse {

    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper(new JsonFactory());
    }

    private final int departureStation;
    private final int arrivalStation;
    private final boolean routeExists;

    private DirectRouteResponse(int departureStation, int arrivalStation, boolean routeExists) {
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.routeExists = routeExists;
    }

    public static DirectRouteResponse routeExists(int departureStation, int arrivalStation) {
        return new DirectRouteResponse(departureStation, arrivalStation, true);
    }

    public static DirectRouteResponse noRoute(int departureStation, int arrivalStation) {
        return new DirectRouteResponse(departureStation, arrivalStation, false);
    }

    @JsonProperty("dep_sid")
    public int getDepartureStation() {
        return departureStation;
    }

    @JsonProperty("arr_sid")
    public int getArrivalStation() {
        return arrivalStation;
    }

    @JsonProperty("direct_bus_route")
    public boolean isRouteExists() {
        return routeExists;
    }

    public void writeTo(OutputStream out) {
        try {
            mapper.writeValue(out, this);
        } catch (IOException e) {
            throw new RuntimeException("Failed to serialize object", e);
        }
    }
}
