package pl.marcin.project.exceptions;

public class GeocodingForbiddenException extends RuntimeException {
    public GeocodingForbiddenException(String message) {
        super(message);
    }

    public GeocodingForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }
}
