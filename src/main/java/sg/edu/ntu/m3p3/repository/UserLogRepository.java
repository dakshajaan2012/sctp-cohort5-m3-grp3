package sg.edu.ntu.m3p3.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.edu.ntu.m3p3.entity.UserLog;

@Repository
public interface UserLogRepository extends JpaRepository<UserLog, UUID> {

    void deleteByUser_UserId(UUID userId);
}
