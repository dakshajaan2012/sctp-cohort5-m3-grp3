package sg.edu.ntu.m3p3.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sg.edu.ntu.m3p3.entity.User.User;

import java.util.*;

public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

    // Save or update a user
    User save(Optional<User> user);

    // Retrieve a user by ID
    Optional<User> findById(UUID userId);

    // Retrieve all users
    ArrayList<User> findAll();

    // Delete a user by ID
    void deleteById(UUID userId);

    // Find users by username
    Optional<User> findByUserName(String userName);

    // Find users by email
    Optional<User> findByEmail(String email);

    // Custom query example using @Query annotation
    // Find users by username containing a certain substring
    @Query("SELECT u FROM User u WHERE u.userName LIKE %:keyword%")
    List<User> findByUserNameContaining(@Param("keyword") String keyword);

    List<User> findAll(Specification<User> spec);

}
