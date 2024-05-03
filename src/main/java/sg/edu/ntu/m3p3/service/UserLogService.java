package sg.edu.ntu.m3p3.service;

import java.util.List;
import java.util.UUID;

import sg.edu.ntu.m3p3.entity.UserLog;

public interface UserLogService {

    UserLog createUserLog(UserLog userLog);

    List<UserLog> getAllUserLogs();

    void deleteUserLogsById(UUID userLogId);

}
