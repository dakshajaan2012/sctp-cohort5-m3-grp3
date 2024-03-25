package sg.edu.ntu.m3p3.entity.User;

import java.util.*;

import org.springframework.stereotype.Component;

import sg.edu.ntu.m3p3.service.UserService;

@Component
public class UserUtils {

    private final UserService userService;

    public UserUtils(UserService userService) {
        this.userService = userService;
    }

    public Object getUserById(UUID userId) {
        User existingUser = userService.getUserById(userId);
        if (existingUser == null) {
            throw new IllegalArgumentException("User with ID '" + userId + "' not found");
        }
        return existingUser;
    }

    public void checkUsernameExists(String username) {
        Optional<User> existingUser = userService.findByUserName(username);
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Username '" + username + "' already exists");
        }
    }
}
