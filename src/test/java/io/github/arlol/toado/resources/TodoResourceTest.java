package io.github.arlol.toado.resources;

import static org.junit.Assert.assertNotNull;

import java.util.Collection;

import org.junit.Test;

import io.github.arlol.toado.api.Todo;

public class TodoResourceTest {

    @Test
    public void testAll() {
        Collection<Todo> todos = new TodoResource().getAllTodos();
        assertNotNull(todos);
    }

}
