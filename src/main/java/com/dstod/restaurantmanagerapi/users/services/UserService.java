package com.dstod.restaurantmanagerapi.users.services;

import com.dstod.restaurantmanagerapi.users.exceptions.UserDetailsDuplicationException;
import com.dstod.restaurantmanagerapi.users.models.dtos.CreateUserRequest;
import com.dstod.restaurantmanagerapi.users.models.entities.Role;
import com.dstod.restaurantmanagerapi.users.models.entities.User;
import com.dstod.restaurantmanagerapi.users.models.enums.RoleType;
import com.dstod.restaurantmanagerapi.users.repositories.RoleRepository;
import com.dstod.restaurantmanagerapi.users.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
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

    public void deleteUser() {

    }

    public void updateUser() {

    }
}
