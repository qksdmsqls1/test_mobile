package com.example.test_mobile.service;

import com.example.test_mobile.model.User;
import com.example.test_mobile.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testRegisterUser() {
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");

        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.registerUser(user);
        assertThat(createdUser.getName()).isEqualTo("Test User");
        assertThat(createdUser.getEmail()).isEqualTo("test@example.com");
    }
}
