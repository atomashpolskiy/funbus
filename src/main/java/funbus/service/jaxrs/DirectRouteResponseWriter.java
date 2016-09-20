package funbus.service.jaxrs;

import funbus.model.DirectRouteResponse;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public class DirectRouteResponseWriter implements MessageBodyWriter<DirectRouteResponse> {

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return DirectRouteResponse.class.equals(type);
    }

    @Override
    public long getSize(DirectRouteResponse directRouteResponse, Class<?> type, Type genericType,
                        Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(DirectRouteResponse directRouteResponse, Class<?> type, Type genericType,
                        Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders,
                        OutputStream entityStream) throws IOException, WebApplicationException {

        directRouteResponse.writeTo(entityStream);
    }
}
