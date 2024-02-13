package org.eventhub.main.service;

import org.eventhub.main.dto.UserRequest;
import org.eventhub.main.dto.UserResponse;
import org.eventhub.main.mapper.UserMapper;
import org.eventhub.main.model.Gender;
import org.eventhub.main.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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

//    @Test
//    public void createUserTest() {
//        UserRequest userRequest = new UserRequest();
//
//        userRequest.setFirstName("Bob");
//        userRequest.setLastName("Dylan");
//        userRequest.setUsername("bobino");
//        userRequest.setEmail("bob@mail.com");
//        userRequest.setPassword("Pass1234");
//        userRequest.setProfileImage("photo");
//        userRequest.setDescription("I'm sigma");
//        userRequest.setCity("Lviv");
//        userRequest.setPhoneNumber("0672604286");
//        userRequest.setBirthDate(LocalDate.of(1990, 5, 15));
//        userRequest.setGender(Gender.MALE);
//
//        UserResponse actual = userService.create(userRequest);
//
//        User user = userMapper.RequestToUser(userRequest, new User());
//        UserResponse expected = userMapper.UserToResponse(user);
//        expected.setCreatedAt(actual.getCreatedAt());
//
//        Assertions.assertEquals(expected, actual);
//    }

    @Test
    void createValidUser(){
        UserResponse expected = new UserResponse(
                1L,
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
                "1234567890",
                LocalDate.of(1990, 5, 15),
                Gender.MALE
        );

        UserResponse actual = userService.create(request);
        expected.setCreatedAt(actual.getCreatedAt());

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(5, userService.getAll().size());
        Assertions.assertEquals(expected,actual);
        Assertions.assertEquals(1L, expected.getId());

        userService.delete(actual.getId());
    }

//    @Test
//    public void updateUserTest() {
//        UserRequest userRequest = new UserRequest();
//
//        userRequest.setFirstName("Jack");
//        userRequest.setLastName("Rob");
//        userRequest.setUsername("Jacky");
//        userRequest.setEmail("jack@mail.com");
//        userRequest.setPassword("Pass12564");
//        userRequest.setProfileImage("photo");
//        userRequest.setDescription("I'm alfa");
//        userRequest.setCity("Lviv");
//        userRequest.setPhoneNumber("0672604256");
//        userRequest.setBirthDate(LocalDate.of(1990, 5, 15));
//        userRequest.setGender(Gender.MALE);
//
//        UserResponse actual = userService.create(userRequest);
//        UserResponse updated = userService.update(userRequest);
//        updated.setCreatedAt(actual.getCreatedAt());
//
//        Assertions.assertEquals(updated, actual);
//    }

    @Test
    public void deleteUserTest() {
        userService.delete(1);
        Assertions.assertEquals(5, userService.getAll().size());
    }

}
