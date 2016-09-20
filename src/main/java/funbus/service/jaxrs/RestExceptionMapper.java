package funbus.service.jaxrs;

import funbus.rest.RestException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class RestExceptionMapper implements ExceptionMapper<RestException> {

    @Override
    public Response toResponse(RestException exception) {
        return Response.status(exception.getStatus())
                .entity(toResponseBody(exception))
                .build();
    }

    private static Object toResponseBody(RestException e) {

        StringBuilder buf = new StringBuilder();
        buf.append(e.getStatus().getStatusCode());
        buf.append(" ");
        buf.append(e.getStatus().getReasonPhrase());
        buf.append(" (");
        buf.append(e.getMessage());
        buf.append(")");

        Throwable t = e;
        do {
            buf.append("\nCause: ");
            buf.append(t.getMessage());
            for (StackTraceElement el : t.getStackTrace()) {
                buf.append("\n\t");
                buf.append(el);
            }

        } while (t.getCause() != t && (t = t.getCause()) != null);

        buf.append("\n\n");

        return buf.toString();
    }
}
