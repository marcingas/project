package pl.marcin.project.exceptions;

public class RoutingBadRequestException extends RuntimeException {
    public RoutingBadRequestException(String message) {
        super(message);
    }
}
