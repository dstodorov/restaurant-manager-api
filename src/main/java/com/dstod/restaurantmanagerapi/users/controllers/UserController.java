package com.dstod.restaurantmanagerapi.users.controllers;

import com.dstod.restaurantmanagerapi.users.models.dtos.*;
import com.dstod.restaurantmanagerapi.users.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<StatusResponse> createUser(@Valid @RequestBody CreateUserRequest createUserRequest, UriComponentsBuilder uriComponentsBuilder) {
        return ResponseEntity.ok(this.userService.createUser(createUserRequest));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDetailsResponse> getUserDetails(@PathVariable long userId) {
        UserDetailsResponse userInfo = this.userService.getUserInfo(userId);

        return ResponseEntity.ok(userInfo);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDetailsResponse> updateUserDetails(@PathVariable long userId,
                                                                 @RequestBody @Valid UpdateUserDetailsRequest updateUserDetailsRequest) {
        UserDetailsResponse updatedUserDetails = this.userService.updateUserDetails(userId, updateUserDetailsRequest);

        return  ResponseEntity.ok(updatedUserDetails);
    }
}
