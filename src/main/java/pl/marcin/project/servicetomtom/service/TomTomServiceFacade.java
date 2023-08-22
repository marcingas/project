package pl.marcin.project.servicetomtom.service;

import pl.marcin.project.servicetomtom.geocodingmodel.AddressData;
import pl.marcin.project.servicetomtom.geocodingmodel.GeocodingAnswer;
import pl.marcin.project.servicetomtom.routingmodel.RouteAnswer;
import pl.marcin.project.servicetomtom.routingmodel.RouteData;

public interface TomTomServiceFacade {

    GeocodingAnswer getLocationsCoordinates(AddressData addressData);

    RouteAnswer getRouteBetweenAddresses(RouteData routeData);
}
