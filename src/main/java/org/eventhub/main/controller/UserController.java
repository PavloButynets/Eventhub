package org.eventhub.main.controller;

import lombok.extern.slf4j.Slf4j;
import org.eventhub.main.dto.EventSearchResponse;
import org.eventhub.main.dto.OperationResponse;
import org.eventhub.main.dto.UserRequest;
import org.eventhub.main.dto.UserResponse;
import org.eventhub.main.exception.ResponseStatusException;
import org.eventhub.main.mapper.EventMapper;
import org.eventhub.main.model.Event;
import org.eventhub.main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final EventMapper eventMapper;

    @Autowired
    public UserController(UserService userService, EventMapper eventMapper) {
        this.userService = userService;
        this.eventMapper = eventMapper;
    }

    @GetMapping
    List<UserResponse> getAll() {
        return userService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserResponse> create(@Validated @RequestBody UserRequest userRequest, BindingResult result) {
        if (result.hasErrors()) {
            throw new ResponseStatusException("Invalid Input");
        }

        UserResponse userResponse = userService.create(userRequest);
        log.info("**/created user(id) = " + userResponse.getId());

        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{user_id}")
    public ResponseEntity<UserResponse> update(@PathVariable("user_id") UUID userId,
                                               @Validated @RequestBody UserRequest userRequest, BindingResult result) {
        if (result.hasErrors()) {
            throw new ResponseStatusException("Invalid Input");
        }

        UserResponse userResponse = userService.update(userRequest);
        log.info("**/updated user(id) = " + userId);

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity<OperationResponse> delete(@PathVariable("user_id") UUID userId) {
        String name = userService.readById(userId).getFirstName();
        log.info("**/deleted user(id) = " + userId);
        userService.delete(userId);
        return new ResponseEntity<>(new OperationResponse("User " + name + " deleted successfully"), HttpStatus.OK);
    }

    @GetMapping("/{user_id}/events")
    public ResponseEntity<List<EventSearchResponse>> getUserEvents(@PathVariable("user_id") UUID userId) {
        List<EventSearchResponse> userEvents = userService.getUserEvents(userId)
                .stream()
                .map(eventMapper::entityToSearchResponse)
                .toList();
        return new ResponseEntity<>(userEvents, HttpStatus.OK);
    }
}

