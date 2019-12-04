package io.github.arlol.toado.resources;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.Collections;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;

import org.junit.ClassRule;
import org.junit.Test;

import io.dropwizard.testing.junit.ResourceTestRule;
import io.github.arlol.toado.api.Todo;

public class TodoResourceIntegrationTest {

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder().addResource(new TodoResource()).build();

    @Test
    public void findAll() {
        GenericType<Collection<Todo>> type = new GenericType<>(Collection.class);
        assertThat(resources.target("/todos").request().get(type)).isEqualTo(Collections.emptyList());
    }

    @Test
    public void findByIdNotExisting() {
        assertThat(resources.target("/todos/1").request().get().getStatus()).isEqualTo(404);
    }

    @Test
    public void createWithIdSet() {
        Todo todo = new Todo();
        todo.setId(1L);
        assertThat(resources.target("/todos").request().post(Entity.json(todo)).getStatus()).isEqualTo(400);
    }

    @Test
    public void deleteByIdNotExisting() {
        assertThat(resources.target("/todos/1").request().delete().getStatus()).isEqualTo(404);
    }

    @Test
    public void updateByIdNotExisting() {
        Todo todo = new Todo();
        todo.setId(1L);
        assertThat(resources.target("/todos/1").request().put(Entity.json(todo)).getStatus()).isEqualTo(404);
    }

    @Test
    public void updateByIdWithIdMismatch() {
        Todo todo = new Todo();
        todo.setId(2L);
        assertThat(resources.target("/todos/1").request().put(Entity.json(todo)).getStatus()).isEqualTo(400);
    }

}
