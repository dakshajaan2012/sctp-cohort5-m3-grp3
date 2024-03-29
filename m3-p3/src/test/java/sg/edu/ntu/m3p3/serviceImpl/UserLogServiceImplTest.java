package sg.edu.ntu.m3p3.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import sg.edu.ntu.m3p3.entity.UserLog;
import sg.edu.ntu.m3p3.repository.UserLogRepository;

@SpringBootTest
public class UserLogServiceImplTest {

    @Mock
    private UserLogRepository userLogRepository;

    @InjectMocks
    UserLogServiceImpl userLogService;

    // Test create userlogs case
    @Test
    public void createUserLogTest() {
        // Setup
        UserLog userLog = UserLog.builder().ipAddress("192.168.0.1").origin("singapore").build();

        // Mock the save method
        when(userLogRepository.save(userLog)).thenReturn(userLog);

        // Execute
        UserLog savedUserLog = userLogService.createUserLog(userLog);

        // Assert
        assertEquals(userLog, savedUserLog, "Saved userLog should be new userLog.");

        // Verify only one instance is saved
        verify(userLogRepository, times(1)).save(userLog);

    }

    // Test get userlogs case
    @Test
    public void getAllUserLogsTest() {
        // Setup
        List<UserLog> userLogs = new ArrayList<>();
        userLogs.add(UserLog.builder().ipAddress("192.168.0.1").origin("singapore").isLogin(true).build());
        userLogs.add(UserLog.builder().ipAddress("192.168.0.2").origin("thailand").isLogin(false).build());

        // Mock the behavior of findAll method
        when(userLogRepository.findAll()).thenReturn(userLogs);

        // Execute
        List<UserLog> retrievedUserLogs = userLogService.getAllUserLogs();

        // Assert
        assertEquals(userLogs.size(), retrievedUserLogs.size(), "Number of user logs should be equal.");

        // Verify that findAll method is called exactly once
        verify(userLogRepository, times(1)).findAll();
    }

    // Test delete userlogs case
    @Test
    public void deleteUserLogsByIdTest() {
        // Setup
        UUID userLogId = UUID.randomUUID();

        // Execute
        userLogService.deleteUserLogsById(userLogId);

        // Verify that deleteById method is called with any UUID argument
        verify(userLogRepository, times(1)).deleteById(any(UUID.class));
    }
}
