package io.github.arlol.toado.api;

import java.util.ArrayList;
import java.util.Collection;

public class Todo {

    private long id;
    private String name;
    private String description;
    private Collection<Todo> subtasks = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<Todo> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(Collection<Todo> subtasks) {
        this.subtasks = subtasks;
    }

}
