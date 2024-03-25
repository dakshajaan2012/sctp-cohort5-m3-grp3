package sg.edu.ntu.m3p3.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.*;
import sg.edu.ntu.m3p3.ErrorResponse;
import sg.edu.ntu.m3p3.entity.UserLog;
import sg.edu.ntu.m3p3.entity.User.CreateUserRequest;
import sg.edu.ntu.m3p3.entity.User.UpdateUserRequest;
import sg.edu.ntu.m3p3.entity.User.User;
import sg.edu.ntu.m3p3.entity.User.UserBulkUpdateRequest;
import sg.edu.ntu.m3p3.entity.User.UserUtils;
import sg.edu.ntu.m3p3.exceptions.UserNotFoundException;
import sg.edu.ntu.m3p3.service.UserService;
import sg.edu.ntu.m3p3.utils.SearchCriteria;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Tag(name = "User", description = "User APIs")
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final UserUtils userUtils;

    public UserController(UserService userService, UserUtils userUtils) {
        this.userService = userService;
        this.userUtils = userUtils;
    }

    @Operation(summary = "Retrieve all users", description = "Get a list of all users.", tags = {})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "404", description = "Users not found", content = {
                    @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json") })
    })
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            logger.warn("No users found");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        logger.info("Returning {} users", users.size());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Operation(summary = "Retrieve one user", description = "Get one user based on userId", tags = {})
    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUserById(@PathVariable UUID userId) {
        try {
            User user = userService.getUserById(userId);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            throw e; // Let the global exception handler handle this
        } catch (Exception e) {
            throw new UserNotFoundException("Error retrieving user with ID: " + userId);
        }
    }

    @Operation(summary = "Create a user", description = "Create a user", tags = {})
    @PostMapping("/create")
    public ResponseEntity<Object> createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        logger.info("Received request to create a new user");

        User user = new User();
        user.setUserName(createUserRequest.getUserName());
        user.setPassword(createUserRequest.getPassword());
        user.setFirstName(createUserRequest.getFirstName());
        user.setLastName(createUserRequest.getLastName());
        user.setEmail(createUserRequest.getEmail());
        user.setIsAdmin(createUserRequest.isAdmin());

        User createdUser = userService.createUser(user);
        if (createdUser == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username or email already exists");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @Operation(summary = "Bulk update user details", description = "Bulk update user details", tags = {})
    @PatchMapping("/bulk-update")
    public ResponseEntity<Object> bulkUpdateUsers(@RequestBody UserBulkUpdateRequest bulkUpdateRequest) {
        List<UUID> userIdList = bulkUpdateRequest.getUserIdList();
        if (userIdList == null || userIdList.isEmpty()) {
            return ResponseEntity.badRequest().body("userIdList cannot be null or empty");
        }

        try {
            List<User> updatedUsers = userService.bulkUpdateUsers(bulkUpdateRequest);
            return ResponseEntity.ok(updatedUsers);
        } catch (Exception e) {
            // Handle any exceptions and return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating users: " + e.getMessage());
        }
    }

    @Operation(summary = "Update user details", description = "Update user details", tags = {})
    @PatchMapping("/{userId}/update")
    public ResponseEntity<Object> updateUser(@PathVariable UUID userId,
            @Valid @RequestBody UpdateUserRequest updateUserRequest) {
        logger.info("Received request to update user with ID: {}", userId);

        try {
            User existingUser = userService.getUserById(userId);
            updateUserFields(existingUser, updateUserRequest);
            User updatedUser = userService.updateUser(userId, existingUser);

            logger.info("User updated successfully with ID: {}", userId);
            return ResponseEntity.ok(updatedUser);
        } catch (UserNotFoundException e) {
            logger.error("User not found with ID: {}", userId, e);
            throw e; // Let the global exception handler handle this
        } catch (Exception e) {
            logger.error("Error updating user with ID: {}", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error updating user with ID: " + userId, LocalDateTime.now()));
        }
    }

    private void updateUserFields(User existingUser, UpdateUserRequest updateUserRequest)
            throws IllegalAccessException {
        for (Field field : updateUserRequest.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value = field.get(updateUserRequest);
            if (value != null) {
                String fieldName = field.getName();
                switch (fieldName) {
                    case "userName", "firstName", "lastName", "password", "email" -> {
                        String fieldValue = (String) value;
                        // Perform validation and set value
                        switch (fieldName) {
                            case "userName" -> {
                                // Check if the username already exists
                                userUtils.checkUsernameExists(fieldValue);
                                existingUser.setUserName(fieldValue);
                            }
                            case "password" -> existingUser.setPassword(fieldValue);
                            case "firstName" -> existingUser.setFirstName(fieldValue);
                            case "lastName" -> existingUser.setLastName(fieldValue);
                            case "email" -> existingUser.setEmail(fieldValue);
                        }
                    }
                    case "loginAttemptCounter" -> existingUser.setLoginAttemptCounter((Integer) value);
                    case "isAdmin" -> existingUser.setIsAdmin((Boolean) value);
                    case "isActive" -> existingUser.setIsActive((Boolean) value);
                    case "isDeleted" -> existingUser.setIsDeleted((Boolean) value);
                }
            }
        }
    }

    @Operation(summary = "Multifiltering search for users", description = "Multifiltering search for users", tags = {})
    @PostMapping("/search")
    /*
     * {
     * "filterCriteriaList": [
     * {
     * "fieldNames": ["userName"],
     * "comparisonOperator": "CONTAINS",
     * "value": "john"
     * },
     * {
     * "fieldNames": ["isAdmin"],
     * "comparisonOperator": "EQUALS",
     * "value": true
     * },
     * {
     * "fieldNames": ["loginAttemptCounter"],
     * "comparisonOperator": "GREATER_THAN",
     * "value": 5
     * }
     * ],
     * "logicalOperator": "OR"
     * }
     * 
     * 
     */
    public ResponseEntity<List<User>> searchUsers(@RequestBody SearchCriteria searchCriteria) {
        List<User> users = userService.findBySearchCriteria(searchCriteria);
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(users);
    }

    @PostMapping("/{userId}/user-logs")
    public ResponseEntity<UserLog> addUserLogToUser(@PathVariable UUID userId,
            @RequestBody UserLog userLog) {
        UserLog newUserLog = userService.addUserLogToUser(userId, userLog);
        return ResponseEntity.ok(newUserLog);
    }

}
