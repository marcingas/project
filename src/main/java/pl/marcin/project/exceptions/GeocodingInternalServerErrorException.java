package pl.marcin.project.exceptions;

public class GeocodingInternalServerErrorException extends RuntimeException {
    public GeocodingInternalServerErrorException(String message) {
        super(message);
    }

    public GeocodingInternalServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
