package io.github.arlol.toado.api;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.validation.Validator;

import org.junit.Test;

import io.dropwizard.jersey.validation.Validators;

public class TodoValidationTest {

    private final Validator validator = Validators.newValidator();

    @Test
    public void validTodo() {
        Todo todo = newValidTodo();
        assertTrue(validator.validate(todo).isEmpty());
    }

    @Test
    public void invalidTodoWithEmptyName() {
        Todo todo = newValidTodo();
        todo.setName("");
        assertFalse(validator.validate(todo).isEmpty());
    }

    @Test
    public void invalidTodoWithNullName() {
        Todo todo = newValidTodo();
        todo.setName(null);
        assertFalse(validator.validate(todo).isEmpty());
    }

    @Test
    public void invalidTodoWithInvalidSubTask() {
        Todo todo = newValidTodo();
        Todo subtask = newValidTodo();
        subtask.setName("");
        todo.getSubtasks().add(subtask);
        assertFalse(validator.validate(todo).isEmpty());
    }

    private Todo newValidTodo() {
        Todo subtask = new Todo();
        subtask.setName("name");

        Todo todo = new Todo();
        todo.setName("name");
        todo.getSubtasks().add(subtask);
        return todo;
    }

}
