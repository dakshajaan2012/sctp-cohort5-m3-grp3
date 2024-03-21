package sg.edu.ntu.m3p3.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.ntu.m3p3.entity.Session;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {

    // @SuppressWarnings("null")
    // Optional<Session> findById(UUID id);

    Optional<Session> findById(Long id);

}
