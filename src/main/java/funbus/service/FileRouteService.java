package funbus.service;

import funbus.model.Station;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FileRouteService implements IRouteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileRouteService.class);

    private final UnionFind<Integer> routes;
    private final Map<Integer, Station> stations;

    public FileRouteService(String fileName) {

        LOGGER.info("Reading routes from file: {}", fileName);

        try (InputStream in = new FileInputStream(Objects.requireNonNull(fileName, "Missing file name"))) {

            UnionFind<Integer> routes = new UnionFind<>();
            Map<Integer, Station> stations = new HashMap<>();
            initRoutes(in, routes, stations);
            this.routes = routes;
            this.stations = stations;

        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to read file: " + fileName, e);
        }
    }

    private static void initRoutes(InputStream in, UnionFind<Integer> routeSet, Map<Integer, Station> stations) {

        Scanner s = new Scanner(in);

        List<Integer> route;
        Map<Integer, Station.Builder> stationBuilders = new HashMap<>();
        int routeCount = Integer.valueOf(s.nextLine());
        int routeId, stationId, anotherStationId;
        for (; routeCount > 0; routeCount--) {
            route = Arrays.asList(s.nextLine().trim().split(" ")).stream()
                    .map(Integer::valueOf).collect(Collectors.toCollection(ArrayList::new));

            routeId = route.get(0);
            for (int i = 1; i < route.size(); i++) {
                stationId = route.get(i);

                for (int j = 2; j < route.size(); j++) {
                    if (i == j) {
                        continue;
                    }


                    anotherStationId = route.get(j);
                    if (!routeSet.contains(stationId)) {
                        routeSet.addElement(stationId);
                    }
                    if (!routeSet.contains(anotherStationId)) {
                        routeSet.addElement(anotherStationId);
                    }
                    routeSet.union(stationId, anotherStationId);
                }

                if (!stationBuilders.containsKey(stationId)) {
                    stationBuilders.put(stationId, Station.id(i));
                }
                stationBuilders.get(stationId).route(routeId);
            }
        }

        stationBuilders.entrySet().forEach(
                entry -> stations.put(entry.getKey(), entry.getValue().build()));
    }

    @Override
    public boolean existsRoute(int station, int anotherStation) {
        // comparison is intentionally done by identity, not equality
        return (routes.contains(station) && routes.contains(anotherStation))
                && (routes.find(station) == routes.find(anotherStation));
    }

    @Override
    public boolean existsDirectRoute(int stationId, int anotherStationId) {

        Station station = stations.get(stationId);
        Station anotherStation = stations.get(anotherStationId);

        return station != null && anotherStation != null && station.hasCommonRoutes(anotherStation);
    }
}
