package pl.marcin.project.exceptions;

public class GeocodingTooManyRequestsException extends RuntimeException {
    public GeocodingTooManyRequestsException(String message) {
        super(message);
    }

    public GeocodingTooManyRequestsException(String message, Throwable cause) {
        super(message, cause);
    }
}
