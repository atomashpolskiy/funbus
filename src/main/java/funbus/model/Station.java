package funbus.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Station {

    public static Builder id(int stationId) {
        return new Builder(stationId);
    }

    private int stationId;
    private List<Integer> routes;

    private Station(int stationId, List<Integer> routes) {
        this.stationId = stationId;
        this.routes = Collections.unmodifiableList(routes);
    }

    public int getStationId() {
        return stationId;
    }

    public List<Integer> getRoutes() {
        return routes;
    }

    public boolean hasCommonRoutes(Station station) {
        return getLeastCommonRoute(station.getRoutes()) >= 0;
    }

    private int getLeastCommonRoute(List<Integer> sortedRoutes) {

        int i = 0, j = 0, m = routes.size(), n = sortedRoutes.size();
        Integer r1, r2;
        while (i < m && j < n) {
            r1 = routes.get(i);
            r2 = sortedRoutes.get(j);

            if (r1.intValue() == r2.intValue()) {
                return r1;
            }
            else if (r1 > r2) {
                if (j < n - 1) {
                    do {
                        r2 = sortedRoutes.get(++j);
                    } while (r2 < r1 && j < n - 1);
                }
            }
            else if (r1 < r2) {
                if (i < m - 1) {
                    do {
                        r1 = routes.get(++i);
                    } while (r1 < r2 && i < m - 1);
                }
            }
            i++;
            j++;
        }
        return -1;
    }

    public static class Builder {

        private int stationId;
        private Set<Integer> routes;

        public Builder(int stationId) {
            if (stationId < 0) {
                throw new IllegalArgumentException("Illegal id: " + stationId);
            }
            this.stationId = stationId;
        }

        public Builder route(int routeId) {
            if (routeId < 0) {
                throw new IllegalArgumentException("Illegal id: " + routeId);
            }
            if (routes == null) {
                routes = new HashSet<>();
            }
            routes.add(routeId);
            return this;
        }

        public Station build() {
            Objects.requireNonNull(routes, "Can't build station -- no routes");
            List<Integer> routeList = new ArrayList<>(routes);
            Collections.sort(routeList);
            return new Station(stationId, routeList);
        }
    }
}
