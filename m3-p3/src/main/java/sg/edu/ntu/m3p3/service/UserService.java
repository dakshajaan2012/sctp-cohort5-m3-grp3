package sg.edu.ntu.m3p3.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import sg.edu.ntu.m3p3.entity.UserLog;
import sg.edu.ntu.m3p3.entity.User.User;

public interface UserService {

    User createUser(User user);

    User getUserById(UUID userId);

    ArrayList<User> getAllUsers();

    List<User> getAllUsersNativeQuery();

    User updateUser(UUID userId, User updatedUser);

    void deleteUser(UUID userId);

    UserLog addUserLogToUser(UUID userId, UserLog userLog);

}