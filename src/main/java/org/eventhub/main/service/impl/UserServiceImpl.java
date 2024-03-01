package org.eventhub.main.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.eventhub.main.dto.PhotoResponse;
import org.eventhub.main.dto.UserResponse;
import org.eventhub.main.dto.UserRequest;
import org.eventhub.main.exception.NullDtoReferenceException;
import org.eventhub.main.mapper.PhotoMapper;
import org.eventhub.main.mapper.UserMapper;
import org.eventhub.main.model.Photo;
import org.eventhub.main.model.User;
import org.eventhub.main.repository.UserRepository;
import org.eventhub.main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

//@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userDtoMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userDtoMapper) {
        this.userRepository = userRepository;
        this.userDtoMapper = userDtoMapper;
    }

    @Override
    public UserResponse create(UserRequest userRequest) {
        if (userRequest != null) {
            User user = userDtoMapper.requestToEntity(userRequest, new User());
            return userDtoMapper.entityToResponse(userRepository.save(user));
        }
        throw new NullDtoReferenceException("User cannot be 'null'");
    }

    @Override
    public UserResponse readById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User with " + id + " not found"));
        return userDtoMapper.entityToResponse(user);
    }

    @Override
    public User readByIdEntity(UUID id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User with " + id + " not found"));
    }

    @Transactional
    @Override
    public UserResponse update(UserRequest userRequest) {
        if (userRequest != null) {
            User user = userDtoMapper.requestToEntity(userRequest, userRepository.findByEmail(userRequest.getEmail()));
            readByIdEntity(user.getId());
            return userDtoMapper.entityToResponse(userRepository.save(user));
        }
        throw new NullDtoReferenceException("User cannot be 'null'");
    }

    @Override
    public void delete(UUID id) {
        userRepository.delete(readByIdEntity(id));
    }

    @Override
    public List<UserResponse> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userDtoMapper::entityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not Found!");
        }
        return user;
    }

//    public boolean isCurrentUser(Long userId) {
//        User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        return Objects.equals(readByEmail(userDetails.getUsername()).getId(), userId);
//    }

    @Override
    public void addImage(UUID id, Photo image){
        User user = this.readByIdEntity(id);
        user.getProfileImages().add(image);
    }

    @Override
    public void deleteImage(UUID userId, Photo image) {
        this.readByIdEntity(userId).getProfileImages().remove(image);
    }

//    public User readByEmail(String email) {
//        return userRepository.findByEmail(email);
//    }
}

