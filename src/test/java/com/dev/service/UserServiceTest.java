package com.dev.service;

import com.dev.dao.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCollection;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {
    private static final User IVAN = User.of(1L, "ivan", "123");
    private static final User PETR = User.of(2L, "petr", "456");

    private UserService userService;

    @BeforeAll
    static void init() {

    }

    @BeforeEach
    void prepare() {
        userService = new UserService();
    }

    @Test
    void shouldReturnEmptyList() {
        List<User> users = userService.getAll();
        assertTrue(users.isEmpty(), () -> "List of empty");
    }

    @Test
    void shouldReturnSizeIfUserAdded() {
        userService.add(IVAN);
        userService.add(PETR);

        List<User> users = userService.getAll();
        assertEquals(3, users.size());
    }

    @Test
    void shouldFailIfPasswordWrong() {
        userService.add(IVAN);

        var maybeUser = userService.login(IVAN.getName(), "345");

        assertThat(maybeUser).isNotPresent();
    }

    @Test
    void shouldTrueIfPasswordCorrect() {
        userService.add(IVAN);

        var maybeUser = userService.login(IVAN.getName(), IVAN.getPassword());

        assertThat(maybeUser).isPresent();
        maybeUser.ifPresent(user -> assertThat(IVAN).isEqualTo(user));
        assertThatCollection(userService.getAll()).hasSize(1);
    }

    @Test
    void usersConvertedToMapById() {
        userService.add(IVAN, PETR);
        Map<Long, User> users = userService.getAllConvertedById();

        assertThat(users.get(IVAN.getId())).isEqualTo(IVAN);
        assertThat(users.get(PETR.getId())).isEqualTo(PETR);

        assertAll(
                () -> assertThat(users).containsKeys(IVAN.getId(), PETR.getId()),
                () -> assertThat(users).containsValues(IVAN, PETR)
        );
    }

    @Test
    void shouldThrowIfNameOrPassIsNull() {
        assertAll(
                () ->
                {
                    var ex = assertThrows(IllegalArgumentException.class, () -> userService.login(null, IVAN.getPassword()));
                    assertThat(ex.getMessage()).isEqualTo("name or password is null");
                },
                () -> assertThrows(IllegalArgumentException.class, () -> userService.login(IVAN.getName(), null))
        );
    }

    @AfterEach
    void clean() {
        userService = null;
    }

    @AfterAll
    static void close() {

    }
}
