package io.github.arlol.toado.resources;

import java.util.Collection;
import java.util.Collections;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;

import io.github.arlol.toado.api.Todo;

@Path("/todos")
@Produces(MediaType.APPLICATION_JSON)
public class TodoResource {

    @GET
    @Timed
    public Collection<Todo> get() {
        return Collections.emptyList();
    }

}
