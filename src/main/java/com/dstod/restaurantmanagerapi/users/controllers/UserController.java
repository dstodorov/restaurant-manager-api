package com.dstod.restaurantmanagerapi.users.controllers;

import com.dstod.restaurantmanagerapi.users.models.dtos.*;
import com.dstod.restaurantmanagerapi.users.services.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.annotations.OpenAPI31;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

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
    public ResponseEntity<StatusResponse> createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
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
    public ResponseEntity<UserDetailsResponse> updateUserDetails(@PathVariable long userId,
                                                                 @RequestBody @Valid UpdateUserDetailsRequest updateUserDetailsRequest) {
        UserDetailsResponse updatedUserDetails = this.userService.updateUserDetails(userId, updateUserDetailsRequest);

        return  ResponseEntity.ok(updatedUserDetails);
    }
}
