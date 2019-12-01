package io.github.arlol.toado;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.github.arlol.toado.health.TodoHealthCheck;
import io.github.arlol.toado.resources.TodoResource;

public class ToadoApplication extends Application<ToadoConfiguration> {

    public static void main(final String[] args) throws Exception {
        new ToadoApplication().run(args);
    }

    @Override
    public String getName() {
        return "Toado";
    }

    @Override
    public void initialize(final Bootstrap<ToadoConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final ToadoConfiguration configuration, final Environment environment) {
        final TodoResource resource = new TodoResource();
        final TodoHealthCheck healthCheck = new TodoHealthCheck(resource);
        environment.healthChecks().register("todo", healthCheck);
        environment.jersey().register(resource);
    }

}
