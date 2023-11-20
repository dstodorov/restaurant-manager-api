package com.dstod.restaurantmanagerapi.users.services;

import com.dstod.restaurantmanagerapi.users.exceptions.UserDetailsDuplicationException;
import com.dstod.restaurantmanagerapi.users.exceptions.UserNotFoundException;
import com.dstod.restaurantmanagerapi.users.models.dtos.CreateUserRequest;
import com.dstod.restaurantmanagerapi.users.models.dtos.UpdateUserDetailsRequest;
import com.dstod.restaurantmanagerapi.users.models.dtos.UserDetailsResponse;
import com.dstod.restaurantmanagerapi.users.models.entities.Role;
import com.dstod.restaurantmanagerapi.users.models.entities.User;
import com.dstod.restaurantmanagerapi.users.models.enums.RoleType;
import com.dstod.restaurantmanagerapi.users.repositories.RoleRepository;
import com.dstod.restaurantmanagerapi.users.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public Long createUser(CreateUserRequest createUserRequest) {

        Optional<User> userByUsername = this.userRepository.findByUsername(createUserRequest.username());
        Optional<User> userByEmail = this.userRepository.findByEmail(createUserRequest.email());
        Optional<User> userByPhoneNumber = this.userRepository.findByPhoneNumber(createUserRequest.phoneNumber());

        if (userByUsername.isPresent()) {
            throw new UserDetailsDuplicationException("username exist");
        }

        if (userByEmail.isPresent()) {
            throw new UserDetailsDuplicationException("email exist");
        }

        if (userByPhoneNumber.isPresent()) {
            throw new UserDetailsDuplicationException("phone number exist");
        }

        User user = mapUserRequestToUserEntity(createUserRequest);
        User savedUser = this.userRepository.save(user);

        return savedUser.getId();
    }



    public UserDetailsResponse getUserInfo(long userId) {
        User user = this.userRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id %d does not exist!", userId)));


        return mapUserEntityToUserInfoResponse(user);
    }

    public UserDetailsResponse updateUserDetails(long userId, UpdateUserDetailsRequest userDetailsRequest) {

        Optional<User> userByUsername = this.userRepository.findByUsernameExcludingUserId(userDetailsRequest.username(), userId);
        Optional<User> userByEmail = this.userRepository.findByEmailExcludingUserId(userDetailsRequest.email(), userId);
        Optional<User> userByPhoneNumber = this.userRepository.findByPhoneNumberExcludingUserId(userDetailsRequest.phoneNumber(), userId);
        Optional<User> userById = this.userRepository.findById(userId);

        if (userById.isEmpty()) {
            throw new UserNotFoundException(String.format("User with id %d does not exist!", userId));
        }

        if (userByUsername.isPresent()) {
            throw new UserDetailsDuplicationException("username exist");
        }

        if (userByEmail.isPresent()) {
            throw new UserDetailsDuplicationException("email exist");
        }

        if (userByPhoneNumber.isPresent()) {
            throw new UserDetailsDuplicationException("phone number exist");
        }

        updateUserEntity(userById.get(), userDetailsRequest);

        return mapUserEntityToUserInfoResponse(userById.get());
    }

    private void updateUserEntity(User user, UpdateUserDetailsRequest userDetailsRequest) {

        Set<Role> roles = new HashSet<>();

        userDetailsRequest.roles().forEach(userRole -> {
            Role role = new Role(RoleType.valueOf(userRole));
            this.roleRepository.save(role);

            roles.add(role);
        });

        user.setFirstName(userDetailsRequest.firstName());
        user.setMiddleName(userDetailsRequest.middleName());
        user.setLastName(userDetailsRequest.lastName());
        user.setUsername(userDetailsRequest.username());
        user.setPassword(userDetailsRequest.password());
        user.setRoles(roles);
        user.setEmail(userDetailsRequest.email());
        user.setPhoneNumber(userDetailsRequest.phoneNumber());

        this.userRepository.save(user);
    }


    public void deleteUser() {

    }

    private User mapUserRequestToUserEntity(CreateUserRequest createUserRequest) {

        Set<Role> roles = new HashSet<>();

        createUserRequest.roles().forEach(userRole -> {
            Role role = new Role(RoleType.valueOf(userRole));
            this.roleRepository.save(role);

            roles.add(role);
        });
        return new User(
                createUserRequest.firstName(),
                createUserRequest.middleName(),
                createUserRequest.lastName(),
                createUserRequest.username(),
                createUserRequest.password(),
                roles,
                createUserRequest.email(),
                createUserRequest.phoneNumber()
        );
    }

    private UserDetailsResponse mapUserEntityToUserInfoResponse(User user) {
        List<String> roles = user.getRoles().stream().map(role -> role.getRole().name()).toList();

        return new UserDetailsResponse(
                user.getFirstName(),
                user.getMiddleName(),
                user.getLastName(),
                user.getUsername(),
                roles,
                user.getEmail(),
                user.getPhoneNumber()
        );
    }


}
