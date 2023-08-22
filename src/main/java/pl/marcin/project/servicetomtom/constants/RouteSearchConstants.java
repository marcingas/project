package pl.marcin.project.servicetomtom.constants;

public class RouteSearchConstants {
    public static final String VERSION_NUMBER = "2";
    public static final String EXT = "json";
    public static final String KEY = "vctUnSj6acA5V6nLMaZMmSqXpoyPA5xq";

    public static final String GET_LAT_LONG = "/search/{versionNumber}/geocode/{query}.{ext}";
    public static final String FIND_ROUTE = "" +
            "/routing/1/calculateRoute/{routePlanningLocations}" +
            "/json?maxAlternatives={alternativeRoutes}&routeType={routeType}" +
            "&traffic={traffic}&travelMode={travelMode}";
}
