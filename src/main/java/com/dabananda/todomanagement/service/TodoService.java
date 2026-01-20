package com.dabananda.todomanagement.service;

import com.dabananda.todomanagement.dto.TodoDto;

import java.util.List;

public interface TodoService {
    List<TodoDto> getAllTodos();
    TodoDto getTodoById(long id);
    TodoDto createTodo(TodoDto todoDto);
    TodoDto updateTodo(long id, TodoDto todoDto);
    void deleteTodo(long id);
}
