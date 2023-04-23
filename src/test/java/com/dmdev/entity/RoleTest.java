package com.dmdev.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    @Test
    void checkFindRole() {

        //Given
        String findRole = "ADMIN";

        //When
        Role role = Role.find(findRole).orElse(null);

        //Then
        assertThat(role).isEqualTo(Role.ADMIN);
    }

}