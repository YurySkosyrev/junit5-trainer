package com.dmdev.dao;

import com.dmdev.entity.Gender;
import com.dmdev.entity.Role;
import com.dmdev.entity.User;
import com.dmdev.integration.IntegrationTestBase;
import com.dmdev.util.LocalDateFormatter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.predicate;

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
    @DisplayName("findAll method should return 6 users after added new user")
    void usersSizeIfUserAdded() {

       //Given
        User user = User.builder()
                .name("Yury")
                .birthday(LocalDateFormatter.format("1987-05-26"))
                .gender(Gender.MALE)
                .password("12345")
                .role(Role.ADMIN)
                .email("zalupa@mail.ru")
                .build();
        userDao.save(user);

        //When
        List<User> users = userDao.findAll();

        //Then
        assertThat(users.size()).isEqualTo(6);
    }

    @Test
    @DisplayName("user with name=Vlad should be return")
    void findByEmailAndPassword() {

        //Given
        String email = "vlad@gmail.com";
        String password = "456";

        //When
        User findUser = userDao.findByEmailAndPassword(email, password).orElse(null);


        //Then
        assertThat(findUser).isNotEqualTo(null);
        assertThat(findUser.getName()).isEqualTo("Vlad");
    }

    @Test
    @DisplayName("after deleting a user users size should be 4 and findUserById(1) should return null")
    void delete() {
        //Given
        userDao.delete(1);

        //When
        List<User> users = userDao.findAll();

        //Then
        assertThat(users.size()).isEqualTo(4);
        assertThat(userDao.findById(1).orElse(null)).isEqualTo(null);

    }

    @Nested
    @DisplayName("update method tests")
    class UpdateTests {
        @Test
        @DisplayName("Update user with update() method")
        void updateUser() {
            //Given
            User user = User.builder()
                    .id(1)
                    .name("Yury")
                    .birthday(LocalDateFormatter.format("1987-05-26"))
                    .gender(Gender.MALE)
                    .password("12345")
                    .role(Role.ADMIN)
                    .email("zalupa@mail.ru")
                    .build();
            userDao.update(user);

            //When
            List<User> users = userDao.findAll();
            User findUser = userDao.findById(1).orElse(null);

            //Then
            assertThat(users.size()).isEqualTo(5);
            assertThat(findUser.getName()).isEqualTo("Yury");
        }

        @Test
        @DisplayName("updating a user with a non-existent id")
        void savingTheUserWhenUpdateCalling() {
            //Given
            User user = User.builder()
                    .id(6)
                    .name("Yury")
                    .birthday(LocalDateFormatter.format("1987-05-26"))
                    .gender(Gender.MALE)
                    .password("12345")
                    .role(Role.ADMIN)
                    .email("zalupa@mail.ru")
                    .build();
            userDao.update(user);

            //When
            List<User> users = userDao.findAll();
            User findUser = userDao.findById(6).orElse(null);

            //Then
            assertThat(findUser).isEqualTo(null);
        }
    }
}