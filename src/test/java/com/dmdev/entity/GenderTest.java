package com.dmdev.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GenderTest {

    @Test
    void checkFindGender() {

        //Given
        String findGender = "MALE";

        //When
        Gender gender = Gender.find(findGender).orElse(null);

        //Then
        assertThat(gender).isEqualTo(Gender.MALE);
    }

}