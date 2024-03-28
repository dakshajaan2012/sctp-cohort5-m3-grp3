package sg.edu.ntu.m3p3.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.*;
import sg.edu.ntu.m3p3.entity.UserLog;
import sg.edu.ntu.m3p3.entity.User.CreateUserRequest;
import sg.edu.ntu.m3p3.entity.User.UpdateUserRequest;
import sg.edu.ntu.m3p3.entity.User.User;
import sg.edu.ntu.m3p3.entity.User.UserBulkUpdateRequest;
import sg.edu.ntu.m3p3.service.UserService;
import sg.edu.ntu.m3p3.utils.ResponseUtil;
import sg.edu.ntu.m3p3.utils.ResponseWrapper;
import sg.edu.ntu.m3p3.utils.SearchCriteria;

import java.lang.reflect.Field;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Tag(name = "User", description = "User APIs")
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final ResponseUtil responseUtil;

    public UserController(UserService userService, ResponseUtil responseUtil) {
        this.userService = userService;
        this.responseUtil = responseUtil;
    }

    @Operation(summary = "Retrieve all users", description = "Get a list of all users.", tags = {})
    // @ApiResponses({
    // @ApiResponse(responseCode = "200", description = "Successful operation",
    // content = {
    // @Content(mediaType = "application/json", schema = @Schema(implementation =
    // User.class)) }),
    // @ApiResponse(responseCode = "404", description = "Users not found", content =
    // {
    // @Content(mediaType = "application/json") }),
    // @ApiResponse(responseCode = "500", description = "Internal server error",
    // content = {
    // @Content(mediaType = "application/json") })
    // })
    @GetMapping
    public ResponseEntity<ResponseWrapper<List<User>>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return responseUtil.createSuccessResponse(Collections.emptyList());
        }
        return responseUtil.createSuccessResponse(users);
    }

    @Operation(summary = "Retrieve one user", description = "Get one user based on userId", tags = {})
    @GetMapping("/{userId}")
    public ResponseEntity<ResponseWrapper<User>> getUserById(@PathVariable UUID userId) {
        try {
            User user = userService.getUserById(userId);
            return responseUtil.createSuccessResponse(user);
        } catch (Exception e) {
            return responseUtil.createErrorResponse("Error retrieving user with ID: " + userId);
        }
    }

    @Operation(summary = "Create a user", description = "Create a user", tags = {})
    @PostMapping("/create")
    public ResponseEntity<ResponseWrapper<User>> createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        logger.info("Received request to create a new user");

        User user = new User();
        user.setUserName(createUserRequest.getUserName());
        user.setPassword(createUserRequest.getPassword());
        user.setFirstName(createUserRequest.getFirstName());
        user.setLastName(createUserRequest.getLastName());
        user.setEmail(createUserRequest.getEmail());
        user.setIsAdmin(createUserRequest.isAdmin());

        try {
            User createdUser = userService.createUser(user);
            return responseUtil.createSuccessResponse(createdUser);
        } catch (IllegalArgumentException e) {
            String errorMessage = e.getMessage() != null ? e.getMessage() : "Error occurred during user creation";
            return responseUtil.createErrorResponse(errorMessage);
        }
    }

    @Operation(summary = "Bulk update user details", description = "Bulk update user details", tags = {})
    @PatchMapping("/bulk-update")
    public ResponseEntity<ResponseWrapper<List<User>>> bulkUpdateUsers(
            @RequestBody UserBulkUpdateRequest bulkUpdateRequest) {
        List<UUID> userIdList = bulkUpdateRequest.getUserIdList();
        if (userIdList == null || userIdList.isEmpty()) {
            return responseUtil.createErrorResponse("userIdList cannot be null or empty");
        }

        try {
            List<User> updatedUsers = userService.bulkUpdateUsers(bulkUpdateRequest);
            return responseUtil.createSuccessResponse(updatedUsers);
        } catch (Exception e) {
            String errorMessage = e.getMessage() != null ? e.getMessage() : "An error occurred during bulk update";
            return responseUtil.createErrorResponse(errorMessage);
        }
    }

    @Operation(summary = "Update user details", description = "Update user details", tags = {})
    @PatchMapping("/{userId}/update")
    public ResponseEntity<ResponseWrapper<User>> updateUser(@PathVariable UUID userId,
            @Valid @RequestBody UpdateUserRequest updateUserRequest) {
        logger.info("Received request to update user with ID: {}", userId);

        try {
            User existingUser = userService.getUserById(userId);
            updateUserFields(existingUser, updateUserRequest);
            User updatedUser = userService.updateUser(userId, existingUser);

            logger.info("User updated successfully with ID: {}", userId);
            return responseUtil.createSuccessResponse(updatedUser);
        } catch (Exception e) {
            String errorMessage = "Error updating user with ID: " + userId + ". " + e.getMessage();
            logger.error(errorMessage, e);
            return responseUtil.createErrorResponse(errorMessage);
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
                                try {
                                    userService.checkUsernameExists(fieldValue);
                                    existingUser.setUserName(fieldValue);
                                } catch (IllegalArgumentException e) {
                                    logger.error("Error while setting username: {}", e.getMessage());
                                    throw e;
                                }
                            }
                            case "password" -> existingUser.setPassword(fieldValue);
                            case "firstName" -> existingUser.setFirstName(fieldValue);
                            case "lastName" -> existingUser.setLastName(fieldValue);
                            case "email" -> {
                                try {
                                    userService.checkEmailExists(fieldValue);
                                    existingUser.setEmail(fieldValue);
                                } catch (IllegalArgumentException e) {
                                    logger.error("Error while setting email: {}", e.getMessage());
                                    throw e;
                                }
                            }
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
     * Sample Request Body:
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
     */

    public ResponseEntity<ResponseWrapper<List<User>>> searchUsers(@RequestBody SearchCriteria searchCriteria) {
        List<User> users = userService.findBySearchCriteria(searchCriteria);

        if (users.isEmpty()) {
            // Return an empty success response if no users are found
            return responseUtil.createSuccessResponse(Collections.emptyList());
        }

        // Return a success response with the list of users
        return responseUtil.createSuccessResponse(users);
    }

    @Operation(summary = "Delete one user", description = "Delete one user", tags = {})
    @DeleteMapping("/{userId}")
    public ResponseEntity<ResponseWrapper<String>> deleteUser(@PathVariable UUID userId) {
        try {
            userService.deleteUser(userId);
            return responseUtil.createSuccessResponse("User with ID " + userId + " has been deleted successfully");
        } catch (Exception e) {
            return responseUtil.createErrorResponse("An error " + e.getMessage() +
                    "occurred while deleting the user" + userId);
        }
    }

    @Operation(summary = "Create user logs for user", description = "Create user logs for user", tags = {})
    @PostMapping("/{userId}/user-logs")
    public ResponseEntity<ResponseWrapper<UserLog>> addUserLogToUser(@PathVariable UUID userId,
            @RequestBody UserLog userLog) {
        UserLog newUserLog = userService.addUserLogToUser(userId, userLog);
        return responseUtil.createSuccessResponse(newUserLog);
    }

}