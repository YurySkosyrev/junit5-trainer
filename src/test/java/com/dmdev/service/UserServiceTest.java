package com.dmdev.service;

import com.dmdev.dto.CreateUserDto;
import com.dmdev.dto.UserDto;
import com.dmdev.entity.Gender;
import com.dmdev.entity.Role;
import com.dmdev.entity.User;
import com.dmdev.exception.ValidationException;
import com.dmdev.integration.IntegrationTestBase;
import com.dmdev.mapper.UserMapper;
import com.dmdev.util.LocalDateFormatter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith({
        MockitoExtension.class
})
class UserServiceTest extends IntegrationTestBase {

    @Mock
    private UserService mockUserService;

    private UserService userService = UserService.getInstance();;

    @Test
    @DisplayName("mock login test")
    void mockLoginTest() {
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
        Mockito.doReturn(Optional.of(userWeAreLookingForDto)).when(mockUserService).login(email, password);
        UserDto userDto = mockUserService.login(email, password).orElse(null);

        //Then

        assertThat(userDto.getName()).isEqualTo("Vlad");
    }

    @Nested
    @DisplayName("login tests")
    class LoginTests {

        @Test
        @DisplayName("correct login should return userDto")
        void correctLoginShouldReturnUserDto() {

            //Given
            String email = "vlad@gmail.com";
            String password = "456";

            //When
            UserDto userDto = userService.login(email, password).orElse(null);

            //Then
            assertThat(userDto.getName()).isEqualTo("Vlad");
        }

        @Test
        @DisplayName("incorrect login or password should return null")
        void loginOrPasswordNotMatchingShouldReturnEmpty() {

            //Given
            String email = "vlad@gmail.com";
            String password = "456";

            String invalidEmail = "vla@gmail.com";
            String invalidPassword = "45";


            //When
            UserDto userDtoInvalidPassword =
                    userService.login(email, invalidPassword).orElse(null);
            UserDto userDtoInvalidEmail =
                    userService.login(invalidEmail, password).orElse(null);
            UserDto userDtoInvalidAll =
                    userService.login(invalidEmail, invalidEmail).orElse(null);

            //Then
            assertThat(userDtoInvalidPassword).isNull();
            assertThat(userDtoInvalidEmail).isNull();
            assertThat(userDtoInvalidAll).isNull();
        }
    }

    @Nested
    @DisplayName("Create tests")
    class CreateTests {

        @Test
        @DisplayName("create method should return userDto when userDto is valid")
        void createValidUserDto() {

            //Given
            CreateUserDto validCreateUserDto = CreateUserDto.builder()
                    .name("Yury")
                    .birthday("1988-03-14")
                    .email("yury@gmail.com")
                    .password("1234")
                    .role("USER")
                    .gender("MALE")
                    .build();

            User correctUser = User.builder()
                    .id(6)
                    .name("Yury")
                    .birthday(LocalDateFormatter.format("1988-03-14"))
                    .gender(Gender.MALE)
                    .password("1234")
                    .role(Role.USER)
                    .email("yury@gmail.com")
                    .build();

            UserDto correctUserDto = UserMapper.getInstance().map(correctUser);

            //When
            UserDto returnUserDto = userService.create(validCreateUserDto);

            //Then
            assertThat(returnUserDto.getName()).isEqualTo("Yury");
            assertThat(returnUserDto).isEqualTo(correctUserDto);

        }

        @Test
        @DisplayName("create method should return userDto when userDto is valid")
        void throwsExceptionIfUserDtoIsInvalid() {

            //Given
            CreateUserDto inValidBirthday = CreateUserDto.builder()
                    .name("Yury")
                    .birthday("1988-22-14")
                    .email("yury@gmail.com")
                    .password("1234")
                    .role("USER")
                    .gender("MALE")
                    .build();

            CreateUserDto inValidRole = CreateUserDto.builder()
                    .name("Yury")
                    .birthday("1988-02-14")
                    .email("yury@gmail.com")
                    .password("1234")
                    .role("USE")
                    .gender("MALE")
                    .build();

            assertAll(
                    () -> {
                        ValidationException exc = assertThrows(ValidationException.class,
                                () -> userService.create(inValidBirthday));
                        assertThat(exc.getErrors().get(0).getMessage()).isEqualTo("Birthday is invalid");
                    },
                    () -> assertThrows(ValidationException.class, () -> userService.create(inValidRole))
            );
        }
    }
}