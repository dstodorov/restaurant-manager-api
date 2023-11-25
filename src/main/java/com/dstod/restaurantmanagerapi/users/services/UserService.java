package com.dstod.restaurantmanagerapi.users.services;

import com.dstod.restaurantmanagerapi.users.models.entities.Token;
import com.dstod.restaurantmanagerapi.users.models.enums.TokenType;
import com.dstod.restaurantmanagerapi.users.repositories.TokenRepository;
import com.dstod.restaurantmanagerapi.users.models.dtos.AuthenticationResponse;
import com.dstod.restaurantmanagerapi.users.exceptions.UserDetailsDuplicationException;
import com.dstod.restaurantmanagerapi.users.exceptions.UserNotFoundException;
import com.dstod.restaurantmanagerapi.users.models.dtos.CreateUserRequest;
import com.dstod.restaurantmanagerapi.users.models.dtos.LoginRequest;
import com.dstod.restaurantmanagerapi.users.models.dtos.UpdateUserDetailsRequest;
import com.dstod.restaurantmanagerapi.users.models.dtos.UserDetailsResponse;
import com.dstod.restaurantmanagerapi.users.models.entities.Role;
import com.dstod.restaurantmanagerapi.users.models.entities.User;
import com.dstod.restaurantmanagerapi.users.models.enums.RoleType;
import com.dstod.restaurantmanagerapi.users.repositories.RoleRepository;
import com.dstod.restaurantmanagerapi.users.repositories.UserRepository;
import com.dstod.restaurantmanagerapi.users.security.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, TokenRepository tokenRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse createUser(CreateUserRequest createUserRequest) {

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

        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);

        return new AuthenticationResponse(jwtToken, refreshToken);
    }

    public AuthenticationResponse authenticate(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()
                )
        );

        User user = userRepository.findByUsername(loginRequest.username()).orElseThrow();

        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return new AuthenticationResponse(jwtToken, refreshToken);
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

    private void saveUserToken(User user, String jwtToken) {
        Token token = new Token(0, jwtToken, TokenType.BEARER, false, false, user);
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty()) {
            return;
        }

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);
        if (username != null) {

            User user = this.userRepository.findByUsername(username)
                    .orElseThrow();

            if (jwtService.isTokenValid(refreshToken, user)) {
                String accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                AuthenticationResponse authenticationResponse = new AuthenticationResponse(accessToken, refreshToken);

                new ObjectMapper().writeValue(response.getOutputStream(), authenticationResponse);
            }
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
