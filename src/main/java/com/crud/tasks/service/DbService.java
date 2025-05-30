package com.crud.tasks.service;

import com.crud.tasks.controller.exceptions.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DbService {
    @Autowired
    private TaskRepository repository;

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public Task getTaskById(Long id) throws TaskNotFoundException {
        return repository.findById(id).orElseThrow(TaskNotFoundException::new);
    }

    public Task saveTask(final Task task) {
        return repository.save(task);
    }

    public void deleteTaskById(Long id) throws TaskNotFoundException {
        if (!repository.existsById(id)) throw new TaskNotFoundException();
        repository.deleteById(id);
    }

}
