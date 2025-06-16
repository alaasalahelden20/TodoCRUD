package com.example.TodoApp.controller;

import com.example.TodoApp.dto.LoginRequest;
import com.example.TodoApp.dto.TodoDto;
import com.example.TodoApp.dto.UserDto;
import com.example.TodoApp.security.jwt.JwtUtil;
import com.example.TodoApp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {


    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping()
    public ResponseEntity<?> Register(@RequestBody @Valid UserDto userDto){
        if (userService.existByUserNameOrEmail(userDto.getUsername(), userDto.getEmail())) {
            System.out.println(userService.existByUserNameOrEmail(userDto.getUsername(), userDto.getEmail()));
            return ResponseEntity.badRequest().body("Username or email already exists");
        }
        UserDto user=userService.register(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);


    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
            try {
                Authentication auth = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
                );
                UserDetails userDetails = (UserDetails) auth.getPrincipal();

                // If successful, you can generate JWT token here or return success
                return ResponseEntity.ok().body(jwtUtil.generateToken(userDetails.getUsername(),request.getId()));
            } catch (AuthenticationException ex) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        }
//        @GetMapping()
//    public ResponseEntity<List<UserDto>> getAllUsers(){
//       List<UserDto> users= userService.getAllUsers();
//       return new ResponseEntity<>(users,HttpStatus.ACCEPTED);
//        }










}
