package pl.marcin.project.exceptions;

public class GeocodingMethodNotAllowedException extends RuntimeException {
    public GeocodingMethodNotAllowedException(String message) {
        super(message);
    }

    public GeocodingMethodNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }
}
