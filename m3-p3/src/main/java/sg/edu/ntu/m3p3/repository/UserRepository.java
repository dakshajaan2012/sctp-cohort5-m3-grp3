package sg.edu.ntu.m3p3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.ntu.m3p3.entity.User.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    // Save or update a user
    User save(User user);
    
    // Retrieve a user by ID
    Optional<User> findById(UUID userId);
    
    // Retrieve all users
    ArrayList<User> findAll();

    // Retrieve all users using native SQL query
    @Query(value = "SELECT * FROM \"user\"", nativeQuery = true)
    List<User> findAllUsersNativeQuery();

    
    // Delete a user by ID
    void deleteById(UUID userId);
    
    // Additional methods
    // Find users by username
    Optional<User> findByUserName(String userName);
    
    // Find users by email
    Optional<User> findByEmail(String email);
    
    // Find users by first name and last name
    List<User> findByFirstNameAndLastName(String firstName, String lastName);
    
    // Custom query example using @Query annotation
    // Find users by username containing a certain substring
    @Query("SELECT u FROM User u WHERE u.userName LIKE %:keyword%")
    List<User> findByUserNameContaining(@Param("keyword") String keyword);

}
