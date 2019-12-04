package io.github.arlol.toado.resources;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import javax.ws.rs.WebApplicationException;

import org.junit.Before;
import org.junit.Test;

import io.github.arlol.toado.api.Todo;

public class TodoResourceTest {

    TodoResource resource;

    @Before
    public void setup() {
        resource = new TodoResource();
    }

    @Test
    public void findAll() {
        Collection<Todo> todos = resource.findAll();
        assertNotNull(todos);
        assertTrue(todos.isEmpty());
    }

    @Test
    public void createAndFindAll() {
        resource.create(newTodo());
        Collection<Todo> todos = resource.findAll();
        assertNotNull(todos);
        assertFalse(todos.isEmpty());
    }

    @Test
    public void findById() {
        assertThatThrownBy(() -> resource.findById(100L)).isInstanceOf(WebApplicationException.class)
                .hasFieldOrPropertyWithValue("response.status", 404);
    }

    @Test
    public void createAndFindById() {
        Todo todo = newTodo();
        todo = resource.create(todo);
        assertNotNull(todo);
        assertNotNull(todo.getId());
        todo = resource.findById(todo.getId());
        assertNotNull(todo);
    }

    @Test
    public void createWithIdFails() {
        Todo todo = newTodo();
        todo.setId(4L);
        assertThatThrownBy(() -> resource.create(todo)).isInstanceOf(WebApplicationException.class)
                .hasFieldOrPropertyWithValue("response.status", 400);
    }

    @Test
    public void delete() {
        Todo todo = new Todo();
        todo = resource.create(todo);
        assertNotNull(resource.delete(todo.getId()));
    }

    @Test
    public void deleteNonExisting() {
        assertThatThrownBy(() -> resource.delete(100L)).isInstanceOf(WebApplicationException.class)
                .hasFieldOrPropertyWithValue("response.status", 404);
    }

    @Test
    public void update() {
        Todo todo = newTodo();
        todo = resource.create(todo);
        todo.setName("updated name");
        Todo updated = resource.update(todo.getId(), todo);
        assertEquals(todo.getName(), updated.getName());
        assertEquals(todo.getName(), resource.findById(todo.getId()).getName());
    }

    @Test
    public void updateNonExisting() {
        Todo todo = newTodo();
        todo.setId(1L);
        assertThatThrownBy(() -> resource.update(todo.getId(), todo)).isInstanceOf(WebApplicationException.class)
                .hasFieldOrPropertyWithValue("response.status", 404);
    }

    @Test
    public void updateWrongId() {
        Todo todo = resource.create(newTodo());
        todo.setName("updated name");
        assertThatThrownBy(() -> resource.update(todo.getId() + 10, todo)).isInstanceOf(WebApplicationException.class)
                .hasFieldOrPropertyWithValue("response.status", 400);
    }

    private Todo newTodo() {
        Todo todo = new Todo();
        todo.setName("name");
        todo.setDescription("description");
        return todo;
    }

}
