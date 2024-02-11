package org.eventhub.main.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.eventhub.main.dto.UserDto;
import org.eventhub.main.dto.UserRequest;
import org.eventhub.main.exception.NullEntityReferenceException;
import org.eventhub.main.mapper.UserDtoMapper;
import org.eventhub.main.mapper.UserRequestMapper;
import org.eventhub.main.model.User;
import org.eventhub.main.repository.UserRepository;
import org.eventhub.main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

//@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;
    private final UserRequestMapper userRequestMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserDtoMapper userDtoMapper, UserRequestMapper userRequestMapper) {
        this.userRepository = userRepository;
        this.userDtoMapper = userDtoMapper;
        this.userRequestMapper = userRequestMapper;
    }

    @Override
    public UserDto create(UserRequest userRequest) {
        if (userRequest != null) {
            User user = userRequestMapper.apply(userRequest);
            userRepository.save(user);
        }
        throw new NullEntityReferenceException("User cannot be 'null'");
    }

    @Override
    public UserDto readByDtoId(long id) {
        return userRepository.findById(id).map(userDtoMapper).orElseThrow(
                () -> new EntityNotFoundException("User with" + id + "not found"));
    }

    @Override
    public User readById(long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User with" + id + "not found"));
    }

    @Transactional
    @Override
    public UserDto update(UserRequest userRequest) {
        if (userRequest != null) {
            User user = userRequestMapper.apply(userRequest);
            readById(user.getId());
            userRepository.save(user);
        }
        throw new NullEntityReferenceException("User cannot be 'null'");
    }

    @Override
    public void delete(long id) {
        userRepository.delete(readById(id));
    }

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userDtoMapper)
                .collect(Collectors.toList());
    }
}

