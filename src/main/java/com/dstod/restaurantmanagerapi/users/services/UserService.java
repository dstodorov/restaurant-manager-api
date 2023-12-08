package com.dstod.restaurantmanagerapi.users.services;

import com.dstod.restaurantmanagerapi.common.exceptions.users.UserDetailsDuplicationException;
import com.dstod.restaurantmanagerapi.common.exceptions.users.UserNotFoundException;
import com.dstod.restaurantmanagerapi.common.models.SuccessResponse;
import com.dstod.restaurantmanagerapi.users.models.dtos.CreateUserRequest;
import com.dstod.restaurantmanagerapi.users.models.dtos.UpdateUserRequest;
import com.dstod.restaurantmanagerapi.users.models.dtos.UserDetailsResponse;
import com.dstod.restaurantmanagerapi.users.models.entities.Role;
import com.dstod.restaurantmanagerapi.users.models.entities.User;
import com.dstod.restaurantmanagerapi.users.models.enums.RoleType;
import com.dstod.restaurantmanagerapi.users.repositories.RoleRepository;
import com.dstod.restaurantmanagerapi.users.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.dstod.restaurantmanagerapi.common.messages.ApplicationMessages.*;

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

    public SuccessResponse createUser(CreateUserRequest createUserRequest) {
        ensureUserDetailsDoNotExist(
                0,
                createUserRequest.username(),
                createUserRequest.email(),
                createUserRequest.phoneNumber()
        );

        User user = mapUserRequestToUserEntity(createUserRequest);
        User savedUser = this.userRepository.save(user);
        UserDetailsResponse savedUserDetailsResponse = mapUserEntityToUserInfoResponse(savedUser);

        return new SuccessResponse(USER_CREATED, new Date(), savedUserDetailsResponse);
    }


    public UserDetailsResponse getUserInfo(long userId) {
        User user = this.userRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_ID_NOT_EXISTS, userId)));

        return mapUserEntityToUserInfoResponse(user);
    }

    public SuccessResponse updateUser(long userId, UpdateUserRequest userDetailsRequest) {
        Optional<User> userById = this.userRepository.findById(userId);

        if (userById.isEmpty()) {
            throw new UserNotFoundException(String.format(USER_ID_NOT_EXISTS, userId));
        }

        ensureUserDetailsDoNotExist(
                userId,
                userDetailsRequest.username(),
                userDetailsRequest.email(),
                userDetailsRequest.phoneNumber()
        );

        User user = userById.get();
        updateUserEntity(user, userDetailsRequest);
        UserDetailsResponse savedUserDetailsResponse = mapUserEntityToUserInfoResponse(user);

        return new SuccessResponse(String.format(USER_SUCCESSFULLY_UPDATED,
                user.getUsername()),
                new Date(),
                savedUserDetailsResponse
        );
    }

    private void ensureUserDetailsDoNotExist(long userId, String username, String email, String phoneNumber) {
        checkUserDetailDuplication(
                userId,
                username,
                userRepository::findByUsername,
                userRepository::findByUsernameExcludingUserId,
                USERNAME_EXISTS
        );

        checkUserDetailDuplication(
                userId,
                email,
                userRepository::findByEmail,
                userRepository::findByEmailExcludingUserId,
                EMAIL_EXISTS
        );

        checkUserDetailDuplication(
                userId,
                phoneNumber,
                userRepository::findByPhoneNumber,
                userRepository::findByPhoneNumberExcludingUserId,
                PHONE_NUMBER_EXISTS
        );
    }

    private void checkUserDetailDuplication(long userId,
                                            String detail,
                                            Function<String, Optional<User>> finder,
                                            BiFunction<String, Long, Optional<User>> finderExcludingId,
                                            String errorMessage
    ) {
        Optional<User> userByDetail = (userId > 0) ? finderExcludingId.apply(detail, userId) : finder.apply(detail);
        if (userByDetail.isPresent()) {
            throw new UserDetailsDuplicationException(String.format(errorMessage, detail));
        }
    }

    private void updateUserEntity(User user, UpdateUserRequest userDetailsRequest) {

        user.getRoles().clear();
        this.userRepository.save(user);

        Set<Role> roles = getRoleEntities(userDetailsRequest.roles());

        user.setFirstName(userDetailsRequest.firstName());
        user.setMiddleName(userDetailsRequest.middleName() != null ? userDetailsRequest.middleName() : "N/A");
        user.setLastName(userDetailsRequest.lastName());
        user.setUsername(userDetailsRequest.username());
        user.setPassword(userDetailsRequest.password());
        user.setRoles(roles);
        user.setEmail(userDetailsRequest.email() != null ? userDetailsRequest.email() : "N/A");
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
                createUserRequest.middleName() != null ? createUserRequest.middleName() : "N/A",
                createUserRequest.lastName(),
                createUserRequest.username(),
                passwordEncoder.encode(createUserRequest.password()),
                roles,
                createUserRequest.email() != null ? createUserRequest.email() : "N/A",
                createUserRequest.phoneNumber()
        );
    }

    private UserDetailsResponse mapUserEntityToUserInfoResponse(User user) {
        List<String> roles = user.getRoles().stream().map(role -> role.getRole().name()).toList();

        return new UserDetailsResponse(
                user.getId(),
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
