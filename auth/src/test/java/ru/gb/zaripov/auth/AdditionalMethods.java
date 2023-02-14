package ru.gb.zaripov.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.gb.zaripov.auth.entities.Role;
import ru.gb.zaripov.auth.entities.User;
import ru.gb.zaripov.auth.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

public class AdditionalMethods {

    public static User initUserRepository(UserRepository userRepository, String username, String password, String role) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        List<Role> roles = List.of(new Role(role));
        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        user.setRoles(roles);
        given(userRepository.findByUsername(user.getUsername())).willReturn(Optional.of(user));
        return user;
    }
}
