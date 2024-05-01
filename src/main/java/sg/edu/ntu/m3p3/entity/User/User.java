package sg.edu.ntu.m3p3.entity.User;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import sg.edu.ntu.m3p3.entity.Address;
import sg.edu.ntu.m3p3.entity.Booking;
import sg.edu.ntu.m3p3.entity.Session;
import sg.edu.ntu.m3p3.entity.UserLog;

@Data
@Entity
@NonNull
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private UUID userId;

    @NotEmpty
    @Column(name = "user_name")
    @Pattern(regexp = "^[a-zA-Z0-9_-]{5,20}$", message = "Username must be 5-20 characters long and contain only alphanumeric characters, underscore, or hyphen")
    private String userName;

    @NotEmpty
    @Column(name = "password")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$", message = "Password must contain at least one lowercase letter, one uppercase letter, one digit, and be at least 8 characters long")
    private String password;

    @NotEmpty
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty
    @Column(name = "last_name")
    private String lastName;

    @NotEmpty
    @Column(name = "email")
    @Email(regexp = ".*@.*\\..*", message = "Email should be valid")
    private String email;

    @Column(name = "is_admin")
    private boolean isAdmin;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Min(value = 0, message = "loginAttemptCounter must be greater than or equal to 0")
    @Max(value = 5, message = "loginAttemptCounter must be less than or equal to 5")
    @Column(name = "login_attempt_counter")
    private int loginAttemptCounter;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<Address> addresses = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<UserLog> userLogs = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<Session> sessions = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<Booking> bookings = new HashSet<>();

    public User(String userName, String password, String firstName, String lastName, String email, boolean isAdmin) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isAdmin = isAdmin;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public boolean getIsDeleted() {
        return isDeleted;
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

    public void setLoginAttemptCounter(int i) {
        // Limit loginAttemptCounter to a maximum value of 5
        if (i >= 0 && i <= 5) {
            this.loginAttemptCounter = i;
        } else {
            throw new IllegalArgumentException("loginAttemptCounter must be between 0 and 5 inclusive.");
        }
    }

    public User(String userId) {
        try {
            this.userId = UUID.fromString(userId);
        } catch (IllegalArgumentException e) {
            // Handle invalid UUID string
            // You may choose to log an error, throw an exception, or handle it differently
            // based on your application's requirements
            this.userId = null; // Or you can set a default value or handle it differently
        }
    }
}
