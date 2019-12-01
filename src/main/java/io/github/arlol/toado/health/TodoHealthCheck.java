package io.github.arlol.toado.health;

import java.util.Collection;

import com.codahale.metrics.health.HealthCheck;

import io.github.arlol.toado.api.Todo;
import io.github.arlol.toado.resources.TodoResource;

public class TodoHealthCheck extends HealthCheck {

    private TodoResource todoResource;

    public TodoHealthCheck(TodoResource todoResource) {
        this.todoResource = todoResource;
    }

    @Override
    protected Result check() throws Exception {
        Collection<Todo> todos = todoResource.getAllTodos();
        if (todos == null) {
            return Result.unhealthy("todos are null");
        }
        return Result.healthy();
    }

}
