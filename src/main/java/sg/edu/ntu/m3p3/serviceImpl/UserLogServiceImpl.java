package sg.edu.ntu.m3p3.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.ntu.m3p3.entity.UserLog;
import sg.edu.ntu.m3p3.repository.UserLogRepository;
import sg.edu.ntu.m3p3.service.UserLogService;

import java.util.List;
import java.util.UUID;

@Service
public class UserLogServiceImpl implements UserLogService {

    @Autowired
    private UserLogRepository userLogRepository;

    @Override
    public UserLog createUserLog(UserLog userLog) {
        return userLogRepository.save(userLog);
    }

    @Override
    public List<UserLog> getAllUserLogs() {
        return userLogRepository.findAll();
    }

    @Override
    public void deleteUserLogsById(UUID userLogId) {
        userLogRepository.deleteById(userLogId);
    }

}
