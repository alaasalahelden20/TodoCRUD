package com.example.TodoApp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Operation implements GrantedAuthority {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    private String name;



    @Override
    public String getAuthority() {
        return name;
    }


}
