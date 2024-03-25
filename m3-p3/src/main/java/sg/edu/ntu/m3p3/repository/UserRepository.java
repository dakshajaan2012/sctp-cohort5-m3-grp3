
package sg.edu.ntu.m3p3.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.edu.ntu.m3p3.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // User findByUsername(String username);

    // Optional<User> findById(Long id);
    Optional<User> findById(User userId);

}
