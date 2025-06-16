package com.example.TodoApp.controller;


import com.example.TodoApp.dto.TodoDto;
import com.example.TodoApp.dto.UserDto;
import com.example.TodoApp.security.jwt.JwtUtil;
import com.example.TodoApp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    UserService userService;

    @GetMapping()
    public ResponseEntity<List<TodoDto>> getUserTodos(@RequestHeader("Authorization") String token){
        String username = jwtUtil.getUsernameFromToken(token.substring(7));
        List<TodoDto> userTodos=userService.getUserTodos(username);
        return new ResponseEntity<>(userTodos, HttpStatus.ACCEPTED);
    }

    @GetMapping("/admin")
    public ResponseEntity<List<UserDto>>getAllUsers(){
        List<UserDto> users=userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.ACCEPTED);


    }
}
