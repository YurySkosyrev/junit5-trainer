package com.dmdev.service;

import com.dmdev.dao.UserDao;
import com.dmdev.dto.UserDto;
import com.dmdev.entity.Gender;
import com.dmdev.entity.Role;
import com.dmdev.entity.User;
import com.dmdev.util.LocalDateFormatter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({
        MockitoExtension.class
})
class UserServiceTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserService userService;

    @Test
    void loginTest() {
        //Given
        String email = "vlad@gmail.com";
        String password = "456";

        UserDto userWeAreLookingForDto = UserDto.builder()
                .id(4)
                .name("Vlad")
                .birthday(LocalDateFormatter.format("1984-03-14"))
                .email("vlad@gmail.com")
                .role(Role.USER)
                .gender(Gender.MALE)
                .build();

        //When
        Mockito.doReturn(Optional.of(userWeAreLookingForDto)).when(userDao).findByEmailAndPassword(email, password);
        UserDto userDto = userService.login(email, password).orElse(null);

        //Then

        assertThat(userDto.getName()).isEqualTo("Vlad");
    }

}