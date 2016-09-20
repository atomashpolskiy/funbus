package funbus.rest;

import javax.ws.rs.core.Response.Status;
import java.util.Objects;

public class RestException extends RuntimeException {

    private Status status;

    public RestException(Status status, String message) {
        this(status, message, null);
    }

    public RestException(Status status, String message, Throwable cause) {
        super(message, cause);
        this.status = Objects.requireNonNull(status);
    }

    public Status getStatus() {
        return status;
    }
}
