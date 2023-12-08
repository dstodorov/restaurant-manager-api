package com.dstod.restaurantmanagerapi.users.controllers;

import com.dstod.restaurantmanagerapi.common.models.SuccessResponse;
import com.dstod.restaurantmanagerapi.users.models.dtos.*;
import com.dstod.restaurantmanagerapi.users.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User management APIs")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Create new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "Not authenticated"),
            @ApiResponse(responseCode = "200", description = "User successfully created")
    })
    @PostMapping
    public ResponseEntity<SuccessResponse> createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        return ResponseEntity.ok(this.userService.createUser(createUserRequest));
    }

    @Operation(summary = "Get user details by giver ID")
    @GetMapping("/{userId}")
    public ResponseEntity<UserDetailsResponse> getUserDetails(@PathVariable long userId) {
        UserDetailsResponse userInfo = this.userService.getUserInfo(userId);

        return ResponseEntity.ok(userInfo);
    }

    @Operation(summary = "Update user details by given ID")
    @PutMapping("/{userId}")
    public ResponseEntity<SuccessResponse> updateUserDetails(@PathVariable long userId,
                                                             @RequestBody @Valid UpdateUserRequest updateUserRequest) {
        return ResponseEntity.ok(this.userService.updateUser(userId, updateUserRequest));
    }
}
