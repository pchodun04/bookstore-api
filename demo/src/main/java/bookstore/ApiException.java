package bookstore;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ApiException extends ResponseStatusException {

    public ApiException(HttpStatus status, String message) {
        super(status, message);
    }

    public static ApiException notFound(String message) {
        return new ApiException(HttpStatus.NOT_FOUND, message);
    }

    public static ApiException invalidData(String message) {
        return new ApiException(HttpStatus.BAD_REQUEST, message);
    }
}

