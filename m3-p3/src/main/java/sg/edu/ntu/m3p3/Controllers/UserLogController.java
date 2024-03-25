package sg.edu.ntu.m3p3.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletRequest;
import sg.edu.ntu.m3p3.entity.UserLog;
import sg.edu.ntu.m3p3.service.UserLogService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user-logs")
public class UserLogController {
    private static final Logger logger = LoggerFactory.getLogger(UserLogController.class);

    @Autowired
    private UserLogService userLogService;

    @Autowired
    private RestTemplate restTemplate;

    public UserLogController(UserLogService userLogService) {
        this.userLogService = userLogService;
    }

    @PostMapping("")
    public ResponseEntity<UserLog> createUserLog(@RequestBody UserLog userLog, HttpServletRequest request) {
        logger.info("Create a new userLog");
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        String countryOrigin = getCountryOrigin(ipAddress);

        // Current ipAddress = 127.0.0.1
        userLog.setIpAddress("222.164.130.246");
        userLog.setOrigin(countryOrigin);    
        UserLog createdUserLog = userLogService.createUserLog(userLog);
        return new ResponseEntity<>(createdUserLog, HttpStatus.CREATED);
    }

    // Helper method
    private String getCountryOrigin(String ipAddress) {
        String apiUrl = "https://ipapi.co/" + "222.164.130.246" + "/city/";
        String countryOrigin = restTemplate.getForObject(apiUrl, String.class);
        // System.out.println(countryOrigin);
        // System.out.flush();
        return countryOrigin;
    }

    @GetMapping("")
    public ResponseEntity<List<UserLog>> getAllUserLogs() {
        List<UserLog> userLogs = userLogService.getAllUserLogs();
        if (userLogs.isEmpty()) {
            logger.warn("No userLogs found");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        logger.info("Returning {} userLogs", userLogs.size());
        return new ResponseEntity<>(userLogs, HttpStatus.OK);
    }

    @DeleteMapping("/{userLogId}")
    public ResponseEntity<Void> deleteUserLog(@PathVariable UUID userLogId) {
        userLogService.deleteUserLogsById(userLogId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}