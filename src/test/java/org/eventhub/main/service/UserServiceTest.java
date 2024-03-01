package org.eventhub.main.service;

import jakarta.persistence.EntityNotFoundException;
import org.eventhub.main.dto.UserRequest;
import org.eventhub.main.dto.UserResponse;
import org.eventhub.main.exception.NullDtoReferenceException;
import org.eventhub.main.mapper.UserMapper;
import org.eventhub.main.model.Gender;
import org.eventhub.main.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
public class UserServiceTest {

    public final UserService userService;
    public final UserMapper userMapper;
    @Autowired
    public UserServiceTest(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Test
    public void createValidUser(){
        UserResponse expected = new UserResponse(
                3L,
                "John",
                "Doe",
                "john.doe123",
                "john.doe@example.com",
                "profile-image.jpg",
                "A description about John",
                LocalDateTime.now(),
                "New York",
                LocalDate.of(1990, 5, 15),
                Gender.MALE
        );
        UserRequest request = new UserRequest(
                "John",
                "Doe",
                "john.doe123",
                "john.doe@example.com",
                "Password123",
                "profile-image.jpg",
                "A description about John",
                "New York",
                LocalDate.of(1990, 5, 15),
                Gender.MALE
        );

        UserResponse actual = userService.create(request);
        actual.setCreatedAt(expected.getCreatedAt());

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(5, userService.getAll().size());
        Assertions.assertEquals(expected,actual);
        Assertions.assertEquals(3L, expected.getId());

        userService.delete(actual.getId());
    }

    @Test
    public void createInvalidUser(){
        Assertions.assertThrows(NullDtoReferenceException.class, () -> userService.create(null));
    }

    @Test
    public void readValidUserById() {
        User user = userService.readByIdEntity(10L);
        Assertions.assertEquals("nickGreen", user.getUsername());
    }

    @Test
    public void readInvalidUserById() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.readById(100));
    }

    @Test
    public void readValidUserResponseById() {
        UserResponse response = userService.readById(10L);
        Assertions.assertEquals("nickGreen", response.getUsername());
    }

    @Test
    public void readInvalidUserResponseById() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.readById(100));
    }

    @Test
    public void updateValidUser(){
        UserResponse expected = new UserResponse(
                2L,
                "John",
                "Doe",
                "john.doe123",
                "john.doe@example.com",
                "profile-image.jpg",
                "A description about John",
                LocalDateTime.now(),
                "New York",
                LocalDate.of(1990, 5, 15),
                Gender.MALE
        );
        UserRequest request = new UserRequest(
                "John",
                "Doe",
                "john.doe123",
                "john.doe@example.com",
                "Password123",
                "profile-image.jpg",
                "A description about John",
                "New York",
                LocalDate.of(1990, 5, 15),
                Gender.MALE
        );

        UserResponse userAdd = userService.create(request);
        UserResponse updated = userService.update(request);
        updated.setCreatedAt(expected.getCreatedAt());

        Assertions.assertNotNull(updated);
        Assertions.assertEquals(5, userService.getAll().size());
        Assertions.assertEquals(expected, updated);
        Assertions.assertEquals(2L, expected.getId());

        userService.delete(expected.getId());
    }

    @Test
    public void updateInvalidUser(){
        Assertions.assertThrows(NullDtoReferenceException.class, () -> userService.update(null));
    }

    @Test
    public void deleteValidUserTest() {
        UserRequest request = new UserRequest(
                "Johny",
                "Doer",
                "johny.doe123",
                "john.dot@example.com",
                "Password123566",
                "profile-image.jpg",
                "A description about John",
                "New York",
                LocalDate.of(1990, 5, 15),
                Gender.MALE
        );

        UserResponse response = userService.create(request);

        Assertions.assertEquals(5, userService.getAll().size());

        userService.delete(response.getId());

        Assertions.assertEquals(4, userService.getAll().size());
    }

    @Test
    public void deleteInvalidUserTest() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.delete(100));
    }

    @Test
    public void getAllValidUsersTest() {
        Assertions.assertEquals(4, userService.getAll().size());
    }

    @Test
    public void findUserByValidEmail() {
        User user = userService.findByEmail("nick@mail.com");
        Assertions.assertEquals("nick@mail.com", user.getEmail());
    }

//    @Test
//    public void findUserByInvalidEmail() {
//        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.findByEmail("batman04@mail.com"));
//    }
}
