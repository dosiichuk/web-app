package com.crud.tasks.controller;
import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService dbService;
    @MockBean
    private TaskMapper taskMapper;


    @Test
    void getTasks() throws Exception {
        Task task1 = new Task(1L, "Test task", "Test content");
        Task task2 = new Task(2L, "Another task", "Another content");
        List<Task> tasks = Arrays.asList(task1, task2);

        when(dbService.getAllTasks()).thenReturn(tasks);
        when(taskMapper.mapToTaskDtoList(tasks)).thenReturn(
                Arrays.asList(
                        new TaskDto(1L, "Test task", "Test content"),
                        new TaskDto(2L, "Another task", "Another content")
                )
        );
        //When and then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Test task"))
                .andExpect(jsonPath("$[0].content").value("Test content"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].title").value("Another task"))
                .andExpect(jsonPath("$[1].content").value("Another content"));
    }

    @Test
    void getTask() throws Exception {
        Task task = new Task(1L, "Test task", "Test content");
        when(dbService.getTaskById(anyLong())).thenReturn(task);
        when(taskMapper.mapToTaskDto(any(Task.class))).thenReturn(new TaskDto(1L, "Test task", "Test content"));

        //When and then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test task"))
                .andExpect(jsonPath("$.content").value("Test content"));
    }

    @Test
    void deleteTask() throws Exception {
        // Given
        long taskId = 1L;

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/tasks/" + taskId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        
            
    }

    @Test
    void updateTask() throws Exception {
        // Given
        TaskDto taskDto = new TaskDto(1L, "Updated task", "Updated content");
        Task task = new Task(1L, "Updated task", "Updated content");

        when(taskMapper.mapToTask(any(TaskDto.class))).thenReturn(task);
        when(dbService.saveTask(any(Task.class))).thenReturn(task);
        when(taskMapper.mapToTaskDto(any(Task.class))).thenReturn(taskDto);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/tasks")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"id\":1,\"title\":\"Updated task\",\"content\":\"Updated content\"}"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1L))
                    .andExpect(jsonPath("$.title").value("Updated task"))
                    .andExpect(jsonPath("$.content").value("Updated content"));
    }

    @Test
    void createTask() throws Exception {
        // Given
        TaskDto taskDto = new TaskDto(1L, "New task", "New content");
        Task task = new Task(1L, "New task", "New content");

        when(taskMapper.mapToTask(any(TaskDto.class))).thenReturn(task);
        when(dbService.saveTask(any(Task.class))).thenReturn(task);
        when(taskMapper.mapToTaskDto(any(Task.class))).thenReturn(taskDto);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/tasks")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"title\":\"New task\",\"content\":\"New content\"}"))
                    .andExpect(status().isOk());
    }
}