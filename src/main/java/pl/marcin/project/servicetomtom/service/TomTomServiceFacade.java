package pl.marcin.project.servicetomtom.service;

import pl.marcin.project.servicetomtom.geocodingmodel.AddressData;
import pl.marcin.project.servicetomtom.geocodingmodel.GeocodingAnswer;
import pl.marcin.project.servicetomtom.geocodingmodel.Position;
import pl.marcin.project.servicetomtom.routingmodel.RouteAnswer;
import pl.marcin.project.servicetomtom.routingmodel.RouteData;

public interface TomTomServiceFacade {

    Position getLocationsCoordinates(AddressData addressData);

    Integer getRouteBetweenAddresses(RouteData routeData);
}
