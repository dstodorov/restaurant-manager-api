package com.dstod.restaurantmanagerapi.users.services;

import com.dstod.restaurantmanagerapi.core.messages.RmMessages;
import com.dstod.restaurantmanagerapi.users.models.dtos.*;
import com.dstod.restaurantmanagerapi.users.exceptions.UserDetailsDuplicationException;
import com.dstod.restaurantmanagerapi.users.exceptions.UserNotFoundException;
import com.dstod.restaurantmanagerapi.users.models.entities.Role;
import com.dstod.restaurantmanagerapi.users.models.entities.User;
import com.dstod.restaurantmanagerapi.users.models.enums.RoleType;
import com.dstod.restaurantmanagerapi.users.repositories.RoleRepository;
import com.dstod.restaurantmanagerapi.users.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public StatusResponse createUser(CreateUserRequest createUserRequest) {
        ensureUserDetailsDoNotExist(
                0,
                createUserRequest.username(),
                createUserRequest.email(),
                createUserRequest.phoneNumber()
        );

        User user = mapUserRequestToUserEntity(createUserRequest);
        this.userRepository.save(user);

        return new StatusResponse(HttpStatus.OK, RmMessages.USER_CREATED);
    }


    public UserDetailsResponse getUserInfo(long userId) {
        User user = this.userRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format(RmMessages.USER_ID_NOT_EXISTS, userId)));

        return mapUserEntityToUserInfoResponse(user);
    }

    public UserDetailsResponse updateUserDetails(long userId, UpdateUserDetailsRequest userDetailsRequest) {
        Optional<User> userById = this.userRepository.findById(userId);

        if (userById.isEmpty()) {
            throw new UserNotFoundException(String.format(RmMessages.USER_ID_NOT_EXISTS, userId));
        }

        ensureUserDetailsDoNotExist(
                userId,
                userDetailsRequest.username(),
                userDetailsRequest.email(),
                userDetailsRequest.phoneNumber()
        );

        updateUserEntity(userById.get(), userDetailsRequest);

        return mapUserEntityToUserInfoResponse(userById.get());
    }

    private void ensureUserDetailsDoNotExist(long userId, String username, String email, String phoneNumber) {
        Optional<User> userByUsername = userId > 0 ?
                this.userRepository.findByUsernameExcludingUserId(username, userId)
                : this.userRepository.findByUsername(username);
        Optional<User> userByEmail = userId > 0 ?
                this.userRepository.findByEmailExcludingUserId(email, userId)
                : this.userRepository.findByEmail(email);
        Optional<User> userByPhoneNumber = userId > 0 ?
                this.userRepository.findByPhoneNumberExcludingUserId(phoneNumber, userId)
                : this.userRepository.findByPhoneNumber(phoneNumber);

        if (userByUsername.isPresent()) {
            throw new UserDetailsDuplicationException(String.format(RmMessages.USERNAME_EXISTS, username));
        }

        if (userByEmail.isPresent()) {
            throw new UserDetailsDuplicationException(String.format(RmMessages.EMAIL_EXISTS, email));
        }

        if (userByPhoneNumber.isPresent()) {
            throw new UserDetailsDuplicationException(String.format(RmMessages.PHONE_NUMBER_EXISTS, phoneNumber));
        }
    }

    private void updateUserEntity(User user, UpdateUserDetailsRequest userDetailsRequest) {

        user.getRoles().clear();
        this.userRepository.save(user);

        Set<Role> roles = getRoleEntities(userDetailsRequest.roles());

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

    private Set<Role> getRoleEntities(List<String> roles) {
        Set<Role> roleEntities = new HashSet<>();

        roles.forEach(userRole -> {
            // Can throw UserRoleNotFoundException
            RoleType roleType = RoleType.fromString(userRole);

            Role role = this.roleRepository.findByRole(roleType);

            roleEntities.add(role);
        });

        return roleEntities;
    }

    private User mapUserRequestToUserEntity(CreateUserRequest createUserRequest) {

        Set<Role> roles = getRoleEntities(createUserRequest.roles());
        return new User(
                createUserRequest.firstName(),
                createUserRequest.middleName(),
                createUserRequest.lastName(),
                createUserRequest.username(),
                passwordEncoder.encode(createUserRequest.password()),
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
