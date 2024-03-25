package sg.edu.ntu.m3p3.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.ntu.m3p3.UserLogNotFoundException;
import sg.edu.ntu.m3p3.entity.UserLog;
import sg.edu.ntu.m3p3.entity.User.User;
import sg.edu.ntu.m3p3.repository.UserLogRepository;
import sg.edu.ntu.m3p3.repository.UserRepository;
import sg.edu.ntu.m3p3.service.UserService;

@Service
// @SuppressWarnings("null")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserLogRepository userLogRepository;

    public UserServiceImpl(UserRepository userRepository, UserLogRepository userLogRepository) {
        this.userRepository = userRepository;
        this.userLogRepository = userLogRepository;
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(UUID userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public ArrayList<User> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        return (ArrayList<User>) allUsers;
    }

    @Override
    public List<User> getAllUsersNativeQuery() {
        return userRepository.findAllUsersNativeQuery();
    }

    @Override
    public User updateUser(UUID userId, User updatedUser) {
        User existingUser = userRepository.findById(userId).orElse(null);
        if (existingUser != null) {
            updatedUser.setUserId(userId);
            return userRepository.save(updatedUser);
        }
        return null;
    }

    @Override
    public void deleteUser(UUID userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public UserLog addUserLogToUser(UUID userId, UserLog userLog) {
        User selectedUser = userRepository.findById(userId).orElseThrow(() -> new UserLogNotFoundException(userId));
        userLog.setUser(selectedUser);
        return userLogRepository.save(userLog);
    }

}