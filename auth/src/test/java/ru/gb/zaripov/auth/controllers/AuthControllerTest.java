package ru.gb.zaripov.auth.controllers;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.gb.zaripov.auth.AdditionalMethods;
import ru.gb.zaripov.auth.repositories.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {
    private final String USERNAME = "bob";
    private final String CORRECT_PASSWORD = "correct password";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        AdditionalMethods.initUserRepository(userRepository, USERNAME, CORRECT_PASSWORD, "USER");
    }


    @Test
    void getTokenOnGoodRequest() throws Exception {
        check(USERNAME, CORRECT_PASSWORD, status().isOk());
    }

    @Test
    void getUnauthorizedOnWrongPassword() throws Exception {
        check(USERNAME, "WRONG_PASSWORD", status().isUnauthorized());
    }

    @Test
    void getUnauthorizedOnWrongUserName() throws Exception {
        check("Wrong user name", CORRECT_PASSWORD, status().isUnauthorized());
    }

    private void check(String username, String password, ResultMatcher resultMatcher) throws Exception {
        mockMvc
                .perform(
                        post("/api/v1/auth")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(String.format(
                                        "{\"username\" : \"%s\",\"password\" :\"%s\"}", username, password)
                                )
                )
                .andDo(print())
                .andExpect(resultMatcher);
    }
}