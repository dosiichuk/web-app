package com.crud.tasks.mapper;
import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class TaskMapperTestSuite {

    @Autowired
    private TaskMapper taskMapper;

    @BeforeEach

    @Test
    void testMapToTask() {
        TaskDto taskDto = new TaskDto(1L, "Test Task", "This is a test task");
        Task task = taskMapper.mapToTask(taskDto);
        assert task.getId().equals(taskDto.getId());
        assert task.getTitle().equals(taskDto.getTitle());
        assert task.getContent().equals(taskDto.getContent());
    }

    @Test
    void testMapToTaskDto() {
        Task task = new Task(1L, "Test Task", "This is a test task");
        TaskDto taskDto = taskMapper.mapToTaskDto(task);
        assert taskDto.getId().equals(task.getId());
        assert taskDto.getTitle().equals(task.getTitle());
    }

    @Test
    void testMapToTaskDtoList() {
        List<Task> tasks = Arrays.asList(
            new Task(1L, "Task 1", "Content 1"),
            new Task(2L, "Task 2", "Content 2")
        );
        List<TaskDto> taskDtos = taskMapper.mapToTaskDtoList(tasks);
        assert taskDtos.size() == tasks.size();
        for (int i = 0; i < tasks.size(); i++) {
            assert taskDtos.get(i).getId().equals(tasks.get(i).getId());
            assert taskDtos.get(i).getTitle().equals(tasks.get(i).getTitle());
        }
    }
}
