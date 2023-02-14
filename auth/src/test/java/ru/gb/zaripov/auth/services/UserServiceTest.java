package ru.gb.zaripov.auth.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.gb.zaripov.auth.entities.Role;
import ru.gb.zaripov.auth.entities.User;
import ru.gb.zaripov.auth.repositories.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.gb.zaripov.auth.AdditionalMethods.initUserRepository;

@SpringBootTest(classes = UserService.class)
class UserServiceTest {

    @MockBean
    private UserRepository userRepository;
    private User user;
    private UserService userService;

    @BeforeEach
    void setUp() {
        String USERNAME = "bob";
        String CORRECT_PASSWORD = "correct password";
        user = initUserRepository(userRepository, USERNAME, CORRECT_PASSWORD, "USER");
        userService = new UserService(userRepository);
    }

    @Test
    void getDetailsByName() {
        UserDetails actual = userService.getDetailsByName(user.getUsername());
        assertEquals(user.getUsername(), actual.getUsername());
        assertEquals(user.getPassword(), actual.getPassword());
        assertCollectionEquals(
                user.getRoles().stream().map(Role::getName).toList(),
                actual.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
    }

    @Test
    void getPasswordByName() {
        UserDetails actual = userService.getDetailsByName(user.getUsername());
        assertEquals(user.getPassword(), actual.getPassword());
    }

    @Test
    void getUserNotFoundException1() {
        assertThrows(UsernameNotFoundException.class, () -> userService.getDetailsByName("wrong name"));
    }

    @Test
    void getUserNotFoundException2() {
        assertThrows(UsernameNotFoundException.class, () -> userService.getPasswordByName("wrong name"));
    }


    private void assertCollectionEquals(List<String> expected, List<String> actual) {
        if (expected == null ||
                actual == null ||
                !expected.containsAll(actual) ||
                !actual.containsAll(expected)) {
            throw new AssertionFailedError("Lists are not equal!");
        }
    }
}