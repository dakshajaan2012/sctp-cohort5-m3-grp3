
package sg.edu.ntu.m3p3.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

//import sg.edu.ntu.m3p3.entity.Session;
import sg.edu.ntu.m3p3.entity.User;
import sg.edu.ntu.m3p3.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Get all users

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Create user
    public User createUser(User user) {
        // session.setDeleted(false);
        return userRepository.save(user);
    }

    // Get user bu ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    // Update user
    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // session.setUserName(sessionDetails.getUserId());
        user.setUserName(userDetails.getUserName());
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmail(userDetails.getEmail());
        // session.setTimeStart(sessionDetails.getTimeStart());
        // session.setTimeStop(sessionDetails.getTimeStop());
        // session.setDeleted(sessionDetails.isDeleted());

        return userRepository.save(user);
    }

    // Delete user
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User not found"));
        userRepository.deleteById(id);

    }
}