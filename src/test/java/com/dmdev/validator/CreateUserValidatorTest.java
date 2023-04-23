package com.dmdev.validator;

import com.dmdev.dto.CreateUserDto;
import com.dmdev.entity.Gender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CreateUserValidatorTest {

    @Test
    @DisplayName("the method should add one error if the birthdate is not valid")
    void birthdayIsInvalid(){

        //Given
        CreateUserDto createUserDto = CreateUserDto.builder()
                .name("Yury")
                .gender("MALE")
                .password("123")
                .email("mail@mail.ru")
                .birthday("32-03-2023")
                .role("ADMIN")
                .build();

        //When
        ValidationResult validationResult = CreateUserValidator.getInstance().validate(createUserDto);

        //Then
        assertThat(validationResult.getErrors().size()).isEqualTo(1);
        assertThat(validationResult.getErrors().get(0).getCode()).isEqualTo("invalid.birthday");
        assertThat(validationResult.getErrors().get(0).getMessage()).isEqualTo("Birthday is invalid");
        assertThat(validationResult.isValid()).isFalse();

    }

    @Test
    @DisplayName("the method should add three errors if the birthdate, gender, role are not valid")
    void birthdayAndMaleAndEmailIsInvalid(){

        //Given
        CreateUserDto createUserDto = CreateUserDto.builder()
                .name("Yury")
                .gender("MAL")
                .password("123")
                .email("mail@mail.ru")
                .birthday("32-03-2023")
                .role("ADMI")
                .build();

        //When
        ValidationResult validationResult = CreateUserValidator.getInstance().validate(createUserDto);

        //Then
        assertThat(validationResult.getErrors().size()).isEqualTo(3);
        assertThat(validationResult.getErrors().get(0).getCode()).isEqualTo("invalid.birthday");
        assertThat(validationResult.getErrors().get(1).getCode()).isEqualTo("invalid.gender");
        assertThat(validationResult.getErrors().get(2).getCode()).isEqualTo("invalid.role");
        assertThat(validationResult.isValid()).isFalse();
    }

    @Test
    @DisplayName("the method should not add errors if all is OK")
    void allIsValid(){

        //Given
        CreateUserDto createUserDto = CreateUserDto.builder()
                .name("Yury")
                .gender("MALE")
                .password("123")
                .email("mail@mail.ru")
                .birthday("1987-05-26")
                .role("ADMIN")
                .build();

        //When
        ValidationResult validationResult = CreateUserValidator.getInstance().validate(createUserDto);

        //Then
        assertThat(validationResult.getErrors().size()).isEqualTo(0);
        assertThat(validationResult.isValid()).isTrue();
    }

}