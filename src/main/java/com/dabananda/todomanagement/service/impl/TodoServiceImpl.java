package com.dabananda.todomanagement.service.impl;

import com.dabananda.todomanagement.dto.TodoDto;
import com.dabananda.todomanagement.entity.Todo;
import com.dabananda.todomanagement.exception.ResourceNotFoundException;
import com.dabananda.todomanagement.repository.TodoRepository;
import com.dabananda.todomanagement.service.TodoService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {
    private TodoRepository todoRepository;
    private ModelMapper modelMapper;
    
    @Override
    public List<TodoDto> getAllTodos() {
        List<Todo> todos = todoRepository.findAll();
        return todos.stream().map(todo -> modelMapper.map(todo, TodoDto.class)).collect(Collectors.toList());
    }

    @Override
    public TodoDto getTodoById(long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo not found with id " + id));
        return modelMapper.map(todo, TodoDto.class);
    }

    @Override
    public TodoDto createTodo(TodoDto todoDto) {
        Todo todo = modelMapper.map(todoDto, Todo.class);
        todoRepository.save(todo);
        return modelMapper.map(todo, TodoDto.class);
    }

    @Override
    public TodoDto updateTodo(long id, TodoDto todoDto) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo not found with id " + id));
        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setCompleted(todoDto.getCompleted());
        Todo updatedTodo = todoRepository.save(todo);
        return modelMapper.map(updatedTodo, TodoDto.class);
    }

    @Override
    public void deleteTodo(long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo not found with id " + id));
        todoRepository.delete(todo);
    }

    @Override
    public TodoDto completeTodo(long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo not found with id " + id));
        todo.setCompleted(Boolean.TRUE);
        Todo updatedTodo = todoRepository.save(todo);
        return modelMapper.map(updatedTodo, TodoDto.class);
    }

    @Override
    public TodoDto inCompleteTodo(long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo not found with id " + id));
        todo.setCompleted(Boolean.FALSE);
        Todo updatedTodo = todoRepository.save(todo);
        return modelMapper.map(updatedTodo, TodoDto.class);
    }
}
