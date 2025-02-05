package com.craftorganizer.userservice.controller;

import com.craftorganizer.userservice.service.dto.UserDto;
import com.craftorganizer.userservice.service.UserService;
import com.craftorganizer.userservice.swagger.DescriptionVariables;
import com.craftorganizer.userservice.swagger.HTMLResponseMessages;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@Tag(name = "User Controller", description = DescriptionVariables.USER)
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Create a new user", description = "Creates a new user")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = HTMLResponseMessages.HTTP_201),
            @ApiResponse(responseCode = "400", description = HTMLResponseMessages.HTTP_400),
            @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)
    })
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto createdUser = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Fetches a user by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = HTMLResponseMessages.HTTP_200),
            @ApiResponse(responseCode = "404", description = HTMLResponseMessages.HTTP_404),
            @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)
    })
    public ResponseEntity<UserDto> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Fetches all users")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = HTMLResponseMessages.HTTP_200),
            @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)
    })
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a user", description = "Updates user details")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = HTMLResponseMessages.HTTP_200),
            @ApiResponse(responseCode = "400", description = HTMLResponseMessages.HTTP_400),
            @ApiResponse(responseCode = "404", description = HTMLResponseMessages.HTTP_404),
            @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)
    })
    public ResponseEntity<UserDto> updateUser(@PathVariable String id, @Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }

    @PostMapping("/{userId}/projects/{projectId}")
    @Operation(summary = "Add a project to a user", description = "Associates a project with a user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = HTMLResponseMessages.HTTP_200),
            @ApiResponse(responseCode = "400", description = HTMLResponseMessages.HTTP_400),
            @ApiResponse(responseCode = "404", description = HTMLResponseMessages.HTTP_404),
            @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)
    })
    public ResponseEntity<UserDto> addProjectToUser(@PathVariable String userId, @PathVariable String projectId) {
        return ResponseEntity.ok(userService.addProjectToUser(userId, projectId));
    }
}
