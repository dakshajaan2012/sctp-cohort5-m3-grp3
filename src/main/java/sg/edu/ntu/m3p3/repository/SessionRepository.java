package sg.edu.ntu.m3p3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sg.edu.ntu.m3p3.entity.Booking;
import sg.edu.ntu.m3p3.entity.Session;
//import sg.edu.ntu.m3p3.entity.User;
//import sg.edu.ntu.m3p3.entity.User.User;

import java.util.List;
//import java.util.Optional;
//import java.util.Optional;
import java.util.UUID;

public interface SessionRepository extends JpaRepository<Session, UUID> {

    // Tested methods
    // Optional<Session> findById(UUID id);
    // Optional<Session> findById(Long userId);
    // List<Session> findByUserId(UUID userId);// was run
    // Optional<Session> findById(Long userId);

    // Used custom method

    @Query("SELECT b FROM Booking b WHERE b.user.userId = :userId")
    List<Booking> findByUserId(@Param("userId") UUID userId);

    List<Session> findByUser_UserId(UUID userId);

    void deleteByUser_UserId(UUID userId);

    List<Session> findBySessionId(UUID sessionId);

}
