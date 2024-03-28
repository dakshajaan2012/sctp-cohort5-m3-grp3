package sg.edu.ntu.m3p3.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.ntu.m3p3.entity.Session;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SessionRepository extends JpaRepository<Session, UUID> {

    // @SuppressWarnings("null")
    // Optional<Session> findById(UUID id);

    Optional<Session> findById(UUID sessionId);

    void deleteByUser_UserId(UUID userId);

    List<Session> findByUser_UserId(UUID userId);

}
