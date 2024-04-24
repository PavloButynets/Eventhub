package org.eventhub.main.controller;

import lombok.extern.slf4j.Slf4j;
import org.eventhub.main.config.JwtService;
import org.eventhub.main.dto.*;
import org.eventhub.main.exception.PasswordException;
import org.eventhub.main.exception.ResponseStatusException;
import org.eventhub.main.model.User;
import org.eventhub.main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    public UserController(UserService userService, JwtService jwtService) {

        this.userService = userService;
        this.jwtService = jwtService;
    }

    @GetMapping
    List<UserResponse> getAll() {
        return userService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserResponse> create(@Validated @RequestBody UserRequestCreate userRequest, BindingResult result) {
        if (result.hasErrors()) {
            throw new ResponseStatusException("Invalid Input");
        }

        UserResponse userResponse = userService.create(userRequest);
        log.info("**/created user(id) = " + userResponse.getId());

        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<UserResponse> getById(@PathVariable("user_id") UUID userId) {
        UserResponse response = userService.readById(userId);
        log.info("**/get by id user(id) = " + response.getId());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{username}/profile")
    public ResponseEntity<UserResponse> getByUsername(@PathVariable("username") String username){
        UserResponse response = userService.readByUsername(username);
        log.info("**/get by username user = " + response.getId());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/username")
    public ResponseEntity<String> getUsernameFromToken(@RequestHeader("Authorization") String token){
        String username = userService.getUsername(jwtService.getId(token));
        log.info("**/get username from token = " + username);
        return new ResponseEntity<>(username, HttpStatus.OK);
    }

    @GetMapping("/user-info")
    public ResponseEntity<UserResponse> getUserInfo(@RequestHeader("Authorization") String token){
        UserResponse response = userService.readById(jwtService.getId(token));
        log.info("**/get user info by id from token = " + response.getId());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping
    public ResponseEntity<UserResponse> update(@RequestHeader("Authorization") String token,
                                                @Validated @RequestBody UserRequestUpdate userRequest,
                                               BindingResult result) {
        if (result.hasErrors()) {
            throw new ResponseStatusException(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }

        UserResponse userResponse = userService.update(jwtService.getId(token), userRequest);
        log.info("**/updated user(id) = " + userResponse.getId());

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity<OperationResponse> delete(@PathVariable("user_id") UUID userId) {
        String name = userService.readById(userId).getFirstName();
        log.info("**/deleted user(id) = " + userId);
        userService.delete(userId);
        return new ResponseEntity<>(new OperationResponse("User " + name + " deleted successfully"), HttpStatus.OK);
    }
    @PutMapping("/change-password")
    public ResponseEntity<UserResponse> changePassword( @RequestHeader("Authorization") String token,
                                                        @Validated @RequestBody PasswordRequest passwordRequest,
                                                        BindingResult result){
        if (result.hasErrors()) {
            throw new PasswordException(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }

        UserResponse userResponse = userService.changePassword(jwtService.getId(token), passwordRequest);
        log.info("**/updated user(id) password = ");

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
}

