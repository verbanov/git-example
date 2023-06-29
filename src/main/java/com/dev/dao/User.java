package com.dev.dao;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor (staticName = "of")
public class User {
    private Long id;
    private String name;
    private String password;
}
