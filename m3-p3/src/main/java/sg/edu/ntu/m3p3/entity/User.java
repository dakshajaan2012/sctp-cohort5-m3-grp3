package sg.edu.ntu.m3p3.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // @Column(name = "id")
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Session> sessions;
    // private UUID userId;
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // private Long id;

    // @NotEmpty
    @Column(name = "user_name")
    @Pattern(regexp = "^[a-zA-Z0-9_-]{5,20}$", message = "Username must be 5-20 characters long and contain only alphanumeric characters, underscore, or hyphen")
    private String userName;

    // @NotEmpty
    @Column(name = "password")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$", message = "Password must contain at least one lowercase letter, one uppercase letter, one digit, and be at least 8 characters long")
    private String password;

    // @NotEmpty
    @Column(name = "first_name")
    private String firstName;

    // @NotEmpty
    @Column(name = "last_name")
    private String lastName;

    // @NotEmpty
    @Column(name = "email")
    @Email(message = "Email should be valid")
    private String email;

    @Column(name = "is_admin")
    private boolean isAdmin;

    @Column(name = "login_attempt_counter")
    private int loginAttemptCounter;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    /*
     * public User() {
     * }
     */

    // private String timeStart;
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    // private String timeStop;
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

    private LocalDateTime bookingTime;

  

    // private static final Logger logger = Logger.getLogger(User.class.getName());
    @PrePersist
    public void prePersist() {
        // Auto-populate the timestamp before persisting
        LocalDateTime currentTime = LocalDateTime.now();
        // logger.info("Current time: " + currentTime);

        if (createdAt == null) {
            createdAt = currentTime.minusMinutes(30);
            // logger.info("Setting createdAt to: " + createdAt);
        }
        if (updatedAt == null) {
            updatedAt = currentTime;
            // logger.info("Setting updatedAt to: " + updatedAt);
        }
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
     
    }

}
