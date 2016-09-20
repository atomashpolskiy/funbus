package funbus;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provider;
import funbus.rest.RouteResource;
import funbus.service.FileRouteService;
import funbus.service.IRouteService;
import funbus.service.jaxrs.DirectRouteResponseWriter;
import funbus.service.jaxrs.RestExceptionMapper;
import io.bootique.Bootique;
import io.bootique.jersey.JerseyModule;

public class Application implements Module, Provider<IRouteService> {

    private static String routesFileName;

    public static void main(String[] args) {

        if (args.length == 0) {
            throw new IllegalStateException("Missing required parameter: routes file name");
        }
        routesFileName = args[0];

        Bootique.app("--config=classpath:run.yml", "--server").module(Application.class).autoLoadModules().run();
    }

    @Override
    public void configure(Binder binder) {

        binder.bind(IRouteService.class).toProvider(Application.class);

        JerseyModule.contributeResources(binder).addBinding().to(RouteResource.class);
        JerseyModule.contributeFeatures(binder).addBinding().toInstance(context -> {
            context.register(DirectRouteResponseWriter.class);
            context.register(RestExceptionMapper.class);
            return true;
        });
    }

    @Override
    public IRouteService get() {
        return new FileRouteService(routesFileName);
    }
}
