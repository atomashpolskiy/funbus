package funbus.service;

public interface IRouteService {

    /**
     * Determine if it's possible to travel between two stations,
     * possibly by taking several different routes.
     */
    boolean existsRoute(int station, int anotherStation);

    /**
     * Determine if it's possible to travel between two stations
     * by taking only one direct route.
     */
    boolean existsDirectRoute(int station, int anotherStation);
}
