package com.epam.todo.todolist.controller;

import com.epam.todo.todolist.entity.Todo;
import com.epam.todo.todolist.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.is;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TodoController.class)
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

//    TodoControllerTest(MockMvc mockMvc) {
//        this.mockMvc = mockMvc;
//    }

    @MockBean
    private TodoService todoService;

    private Todo todo;

    @BeforeEach
    public void setUp() {
        todo = new Todo();
        todo.setId(1L);
        todo.setTitle("Test Title");
        todo.setDescription("Test Description");
    }

    @Test
    public void testGetAllTodos() throws Exception {
        when(todoService.getAllTodos()).thenReturn(Arrays.asList(todo));

        mockMvc.perform(MockMvcRequestBuilders.get("/todos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", is(todo.getTitle())))
                .andExpect(jsonPath("$[0].description", is(todo.getDescription())));
    }

    @Test
    public void testGetTodoById() throws Exception {
        when(todoService.getTodoById(anyLong())).thenReturn(Optional.of(todo));

        mockMvc.perform(MockMvcRequestBuilders.get("/todos/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(todo.getTitle())))
                .andExpect(jsonPath("$.description", is(todo.getDescription())));
    }

    @Test
    public void testCreateTodo() throws Exception {
        when(todoService.createTodo(any(Todo.class))).thenReturn(todo);

        mockMvc.perform(MockMvcRequestBuilders.post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Test Title\",\"description\":\"Test Description\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is(todo.getTitle())))
                .andExpect(jsonPath("$.description", is(todo.getDescription())));
    }

    @Test
    public void testUpdateTodo() throws Exception {
        when(todoService.updateTodo(anyLong(), any(Todo.class))).thenReturn(todo);

        mockMvc.perform(MockMvcRequestBuilders.put("/todos/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Updated Title\",\"description\":\"Updated Description\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(todo.getTitle())))
                .andExpect(jsonPath("$.description", is(todo.getDescription())));
    }

    @Test
    public void testDeleteTodo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/todos/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}
