package com.capstone.licencelifecyclemanagement.servicetests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.capstone.licencelifecyclemanagement.entitys.User;
import com.capstone.licencelifecyclemanagement.repository.UserRepository;
import com.capstone.licencelifecyclemanagement.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void testFindByName() {
        String username = "admin";
        User user = new User();
        user.setName(username);

        when(userRepository.findByName(username)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByName(username);

        assertTrue(result.isPresent());
        assertEquals(username, result.get().getName());
    }

}
