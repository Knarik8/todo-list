package com.epam.todo.todolist.service;

import com.epam.todo.todolist.entity.Todo;
import com.epam.todo.todolist.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TodoServiceTest {

    @InjectMocks
    private TodoService todoService;

    @Mock
    private TodoRepository todoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTodo() {
        Todo todo = new Todo();
        todo.setTitle("Test Title");
        todo.setDescription("Test Description");

        when(todoRepository.save(any(Todo.class))).thenReturn(todo);

        Todo createdTodo = todoService.createTodo(todo);

        assertEquals(todo.getTitle(), createdTodo.getTitle());
        assertEquals(todo.getDescription(), createdTodo.getDescription());
        verify(todoRepository, times(1)).save(todo);
    }

    @Test
    void getTodoById() {
        Todo todo = new Todo();
        todo.setId(1L);
        todo.setTitle("Test Title");
        todo.setDescription("Test Description");

        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));

        Optional<Todo> foundTodo = todoService.getTodoById(1L);

        assertEquals(todo.getTitle(), foundTodo.get().getTitle());
        assertEquals(todo.getDescription(), foundTodo.get().getDescription());
    }

    @Test
    void updateTodo() {
        Todo todo = new Todo();
        todo.setId(1L);
        todo.setTitle("Test Title");
        todo.setDescription("Test Description");

        Todo updatedTodoDetails = new Todo();
        updatedTodoDetails.setTitle("Updated Title");
        updatedTodoDetails.setDescription("Updated Description");

        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));
        when(todoRepository.save(any(Todo.class))).thenReturn(updatedTodoDetails);

        Todo updatedTodo = todoService.updateTodo(1L, updatedTodoDetails);

        assertEquals(updatedTodoDetails.getTitle(), updatedTodo.getTitle());
        assertEquals(updatedTodoDetails.getDescription(), updatedTodo.getDescription());
    }

    @Test
    void deleteTodo() {
        Todo todo = new Todo();
        todo.setId(1L);
        todo.setTitle("Test Title");
        todo.setDescription("Test Description");

        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));

        todoService.deleteTodo(1L);

        verify(todoRepository, times(1)).delete(todo);
    }
}
