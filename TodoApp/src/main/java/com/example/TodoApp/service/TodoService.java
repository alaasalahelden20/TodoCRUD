package com.example.TodoApp.service;

import com.example.TodoApp.dto.TodoDto;
import com.example.TodoApp.mapper.TodoMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface TodoService {
//add_todo
    TodoDto createDto (TodoDto todoDto,String username);
// get-todo_by-id
    TodoDto getTodoById(long Id,String username);

    List<TodoDto> getTodos();



    TodoDto updateDto(Long Id,TodoDto todoDto);

    void delTodoById(Long Id);



}
