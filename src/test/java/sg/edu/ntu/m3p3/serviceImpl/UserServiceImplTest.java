package sg.edu.ntu.m3p3.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import sg.edu.ntu.m3p3.entity.User.User;
import sg.edu.ntu.m3p3.repository.SessionRepository;
import sg.edu.ntu.m3p3.repository.UserLogRepository;
import sg.edu.ntu.m3p3.repository.UserRepository;

@SpringBootTest
public class UserServiceImplTest {

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserLogRepository userLogRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testGetUserById_Positive() {
        // Mock data
        UUID userId = UUID.randomUUID();
        User mockUser = new User();
        mockUser.setUserId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        // Test
        User result = userService.getUserById(userId);

        // Verify
        assertNotNull(result);
        assertEquals(userId, result.getUserId());
    }

    @Test
    public void testGetUserById_Negative_UserNotFound() {
        // Mock data
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Test & Verify
        assertThrows(IllegalArgumentException.class, () -> userService.getUserById(userId));
    }

    @Test
    void testFindByUserName_UserExists() {
        String userName = "existingUser";
        User user = new User();
        user.setUserName(userName);
        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByUserName(userName);

        assertTrue(result.isPresent());
        assertEquals(userName, result.get().getUserName());
    }

    @Test
    void testFindByUserName_UserDoesNotExist() {
        String userName = "nonExistingUser";
        when(userRepository.findByUserName(userName)).thenReturn(Optional.empty());

        Optional<User> result = userService.findByUserName(userName);

        assertFalse(result.isPresent());
    }

    @Test
    void testCheckUsernameExists_UsernameExists() {
        String userName = "existingUser";
        User user = new User();
        user.setUserName(userName);
        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(user));

        assertThrows(IllegalArgumentException.class, () -> userService.checkUsernameExists(userName));
    }

    @Test
    void testCheckUsernameExists_UsernameDoesNotExist() {
        String userName = "nonExistingUser";
        when(userRepository.findByUserName(userName)).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> userService.checkUsernameExists(userName));
    }

    private User createUser() {
        User user = new User();
        user.setUserName("testUser");
        user.setPassword("password");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john@example.com");
        return user;
    }

    /*
     * @Test
     * void testCreateUser_UserNotExists_Success() {
     * User newUser = createUser(); // create a new user
     * when(userRepository.findByUserName(newUser.getUserName())).thenReturn(
     * Optional.empty());
     * when(userRepository.findByEmail(newUser.getEmail())).thenReturn(Optional.
     * empty());
     * when(userRepository.save(newUser)).thenReturn(newUser);
     * 
     * User createdUser = userService.createUser(newUser);
     * 
     * assertNotNull(createdUser);
     * assertEquals(newUser.getUserName(), createdUser.getUserName());
     * assertTrue(createdUser.getCreatedAt().isBefore(LocalDateTime.now()));
     * assertTrue(createdUser.getUpdatedAt().isBefore(LocalDateTime.now()));
     * assertEquals(0, createdUser.getLoginAttemptCounter());
     * assertTrue(createdUser.getIsActive());
     * assertFalse(createdUser.getIsDeleted());
     * }
     */

    @Test
    void testCreateUser_UsernameExists_Failure() {
        User existingUser = createUser(); // create an existing user
        when(userRepository.findByUserName(existingUser.getUserName())).thenReturn(Optional.of(existingUser));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.createUser(existingUser));
        assertEquals("Username '" + existingUser.getUserName() + "' already exists. ", exception.getMessage());
    }

    @Test
    void testCreateUser_EmailExists_Failure() {
        User existingUser = createUser(); // create an existing user
        when(userRepository.findByUserName(existingUser.getUserName())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(existingUser.getEmail())).thenReturn(Optional.of(existingUser));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.createUser(existingUser));
        assertEquals("Email '" + existingUser.getEmail() + "' already exists.", exception.getMessage());
    }

    /*
     * @Test
     * void testDeleteUser() {
     * // Mock data
     * UUID userId = UUID.randomUUID();
     * 
     * // Call the deleteUser method
     * userService.deleteUser(userId);
     * 
     * // Verify that userRepository.deleteById() is called with the correct user ID
     * verify(userRepository, times(1)).deleteById(userId);
     * }
     */

}
