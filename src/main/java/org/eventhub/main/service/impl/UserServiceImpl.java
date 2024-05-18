package org.eventhub.main.service.impl;

import groovy.util.logging.Slf4j;
import jakarta.persistence.EntityNotFoundException;
import org.eventhub.main.config.AuthenticationService;
import org.eventhub.main.dto.*;
import org.eventhub.main.exception.NullDtoReferenceException;
import org.eventhub.main.exception.PasswordException;
import org.eventhub.main.exception.ResponseStatusException;
import org.eventhub.main.mapper.UserMapper;
import org.eventhub.main.model.Photo;
import org.eventhub.main.model.User;
import org.eventhub.main.repository.UserRepository;
import org.eventhub.main.service.PhotoService;
import org.eventhub.main.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

//@RequiredArgsConstructor
@Service
@EnableScheduling
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userDtoMapper;

    private final PhotoService photoService;
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Lazy
    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userDtoMapper, PhotoService photoService) {
        this.userRepository = userRepository;
        this.userDtoMapper = userDtoMapper;
        this.photoService = photoService;
    }

    @Override
    public UserResponse create(UserRequestCreate userRequest) {
        if (userRequest != null) {
            User user = userDtoMapper.createRequestToEntity(userRequest, new User());
            UserResponse response = userDtoMapper.entityToResponse(userRepository.save(user));
            logger.info("User entity saved");
            if (userRequest.getPhotoUrl() != null) {
                photoService.addUserPhotoByUrl(response.getId(), userRequest.getPhotoUrl());

                logger.info("Photo added: " + userRequest.getPhotoUrl());
            }

            return response;
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

    @Override
    public UserResponseBriefInfo readByIdBriefInfo(UUID id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User with " + id + " not found"));
        return userDtoMapper.entityToBriefResponse(user);
    }

    @Override
    public UserResponse readByUsername(String username){
        User user = userRepository.findByUsername(username);
        if(user == null)
            throw new EntityNotFoundException("User with username " + username + " not found");
        return userDtoMapper.entityToResponse(user);
    }

    @Override
    public String getUsername(UUID id){
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User with " + id + " not found"));
        return user.getNickname();
    }

    @Transactional
    @Override
    public UserResponse update(UUID id, UserRequestUpdate userRequest) {
        if (userRequest != null) {
            User user = userDtoMapper.updateRequestToEntity(userRequest, this.readByIdEntity(id));
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
        User user = userRepository.findByEmail(email);
        if(user == null)
            throw new EntityNotFoundException("User with email " + email + " not found");
        return user;
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

    @Override
    public UserResponse changePassword(UUID id, PasswordRequest passwordRequest){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String newPassword = encoder.encode(passwordRequest.getNewPassword());
        User user = this.readByIdEntity(id);

        if(!encoder.matches(passwordRequest.getOldPassword(), user.getPassword())){
            throw new PasswordException("The old password is incorrect!");
        }
        if(encoder.matches(passwordRequest.getOldPassword(), newPassword)){
            throw new PasswordException("the new password cannot be the same as the old one!");
        }

        user.setPassword(newPassword);
        return userDtoMapper.entityToResponse(userRepository.save(user));
    }

    @Override
    public UserResponse confirmUser(UUID id){
        User user = readByIdEntity(id);
        if(user.isVerified()){
            throw new ResponseStatusException("User is already verified!");
        }
        user.setVerified(true);
        return userDtoMapper.entityToResponse(this.userRepository.save(user));
    }

    @Override
    public List<User> findApprovedUsersByEventId(UUID eventId){
       return this.userRepository.findApprovedUsersByEventId(eventId);
    }

//    public User readByEmail(String email) {
//        return userRepository.findByEmail(email);
//    }
}

