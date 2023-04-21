package com.dmdev.dao;

import com.dmdev.entity.Gender;
import com.dmdev.entity.Role;
import com.dmdev.entity.User;
import com.dmdev.integration.IntegrationTestBase;
import com.dmdev.util.LocalDateFormatter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserDaoTest extends IntegrationTestBase {

    UserDao userDao = UserDao.getInstance();

    @Test
    @DisplayName("findAll method should return 5 users")
    void initialUsersSize() {
        List<User> users = userDao.findAll();

        assertThat(users.size()).isEqualTo(5);
    }

    @Test
    @DisplayName("find user with name=Ivan and email=ivan@gmail.com by id=1")
    void findUserWithNameIvanByIdEqualsOne() {
        User user = userDao.findById(1).orElse(null);

        assertThat(user.getName()).isEqualTo("Ivan");
        assertThat(user.getEmail()).isEqualTo("ivan@gmail.com");
    }

    @Test
    void usersSizeIfUserAdded() {
        User user = User.builder()
                .name("Yury")
                .birthday(LocalDateFormatter.format("1987-05-26"))
                .gender(Gender.MALE)
                .password("12345")
                .role(Role.ADMIN)
                .email("zalupa@mail.ru")
                .build();
        userDao.save(user);

        List<User> users = userDao.findAll();

        assertThat(users.size()).isEqualTo(6);
    }

    @Test
    void findByEmailAndPassword() {
    }

    @Test
    void delete() {
    }

    @Test
    void update() {
    }
}