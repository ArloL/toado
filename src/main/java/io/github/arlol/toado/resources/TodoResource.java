package io.github.arlol.toado.resources;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import com.codahale.metrics.annotation.Timed;

import io.github.arlol.toado.api.Todo;

@Path("/todos")
@Produces(MediaType.APPLICATION_JSON)
public class TodoResource {

    private Map<Long, Todo> todos = Collections.synchronizedMap(new HashMap<>());
    private AtomicLong idGenerator = new AtomicLong(1);

    @GET
    @Timed
    public Collection<Todo> findAll() {
        return todos.values();
    }

    @GET
    @Path("/{id}")
    @Timed
    public Todo findById(@PathParam("id") long id) {
        ensureExists(id);
        return todos.get(id);
    }

    @POST
    @Timed
    public Todo create(@NotNull Todo todo) {
        if (todo.getId() != null) {
            throw new WebApplicationException("id should not be set", Status.BAD_REQUEST);
        }
        todo.setId(idGenerator.getAndIncrement());
        todos.put(todo.getId(), todo);
        return todo;
    }

    @PUT
    @Path("/{id}")
    @Timed
    public Todo update(@PathParam("id") long id, @NotNull Todo todo) {
        if (todo.getId() != id) {
            final String msg = String.format("Todo ID %s does not match path ID", todo.getId(), id);
            throw new WebApplicationException(msg, Status.BAD_REQUEST);
        }
        ensureExists(id);
        return todos.put(todo.getId(), todo);
    }

    @DELETE
    @Path("/{id}")
    @Timed
    public Todo delete(@PathParam("id") long id) {
        ensureExists(id);
        return todos.remove(id);
    }

    private void ensureExists(long id) {
        if (!todos.containsKey(id)) {
            final String msg = String.format("Todo %s does not exist", id);
            throw new WebApplicationException(msg, Status.NOT_FOUND);
        }
    }

}
