package com.example.TodoApp.service;

import com.example.TodoApp.dto.TodoDto;
import com.example.TodoApp.dto.UserDto;
import com.example.TodoApp.entity.Todo;
import com.example.TodoApp.entity.User;
import com.example.TodoApp.mapper.TodoMapper;
import com.example.TodoApp.mapper.UserMapper;
import com.example.TodoApp.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

@Autowired
private UserRepository userRepository;
@Autowired
private UserMapper userMapper;
@Autowired
private TodoMapper todoMapper;
@Autowired
private BCryptPasswordEncoder passwordEncoder;




    @Override
    public UserDto register(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user=userMapper.toUserEntity(userDto);

        User savedUser= userRepository.save(user);
        return userMapper.toUserDto(savedUser);


    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users=userRepository.findAll() ;

        return userMapper.toUserDtoList(users);
    }

    @Override
    public boolean existByUserNameOrEmail(String Username,String Email) {
        return userRepository.existsByUsername(Username)|| userRepository.existsByEmail(Email);

    }

    @Override
    public List<TodoDto> getUserTodos(String username) {
        User user=userRepository.findByUsername(username).orElseThrow(RuntimeException::new);
        List<Todo> userTodos=user.getTodos();
        return todoMapper.toDtoList(userTodos);

    }


}
