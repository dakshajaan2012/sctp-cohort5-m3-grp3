package sg.edu.ntu.m3p3.Controllers;

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
import sg.edu.ntu.m3p3.entity.UserLog;
import sg.edu.ntu.m3p3.entity.User.CreateUserRequest;
import sg.edu.ntu.m3p3.entity.User.User;
import sg.edu.ntu.m3p3.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Tag(name = "User", description = "User APIs")
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    // @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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
    public ResponseEntity<ArrayList<User>> getAllUsers() {
        ArrayList<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            logger.warn("No users found");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        logger.info("Returning {} users", users.size());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Operation(summary = "Create a user", description = "Create a user", tags = {})
    @PostMapping("/create")
    public ResponseEntity<User> createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        logger.info("Received request to create a new user");

        // Map CreateUserRequest to User entity
        User user = new User();
        user.setUserName(createUserRequest.getUserName());
        user.setPassword(createUserRequest.getPassword());
        user.setFirstName(createUserRequest.getFirstName());
        user.setLastName(createUserRequest.getLastName());
        user.setEmail(createUserRequest.getEmail());
        user.setIsAdmin(false);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setLoginAttemptCounter(0);
        user.setIsActive(true);
        user.setIsDeleted(false);

        // Create the user
        User createdUser = userService.createUser(user);

        if (createdUser != null) {
            logger.info("User created successfully with ID: {}", createdUser.getUserId());
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } else {
            logger.error("Failed to create user");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{userId}/user-logs")
    public ResponseEntity<UserLog> addUserLogToUser(@PathVariable UUID userId,
            @RequestBody UserLog userLog) {
        UserLog newUserLog = userService.addUserLogToUser(userId, userLog);
        return new ResponseEntity<>(newUserLog, HttpStatus.OK);
    }

}
