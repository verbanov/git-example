package com.dev.service;

import com.dev.dao.User;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

public class UserService {
    private final List<User> users;

    public UserService() {
        users = new ArrayList<>();
    }

    public List<User> getAll() {
        return users;
    }

    public void add(User... users) {
        this.users.addAll(Arrays.asList(users));
    }

    public Optional<User> login(String name, String password) {
        if (name == null || password == null) {
            throw new IllegalArgumentException("name or password is null");
        }
        return users.stream()
                .filter(user -> user.getName().equals(name))
                .filter(user -> user.getPassword().equals(password))
                .findFirst();
    }

    public Map<Long, User> getAllConvertedById() {
        return users.stream()
                .collect(toMap(User::getId, Function.identity()));
    }
}
