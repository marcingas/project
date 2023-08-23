package pl.marcin.project.exceptions;

public class GeocodingNotFoundException extends RuntimeException {
    public GeocodingNotFoundException(String message) {
        super(message);
    }

    public GeocodingNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
