package pl.marcin.project.exceptions;

public class GeocodingBadRequestException extends RuntimeException {
    public GeocodingBadRequestException(String message) {
        super(message);
    }

    public GeocodingBadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
