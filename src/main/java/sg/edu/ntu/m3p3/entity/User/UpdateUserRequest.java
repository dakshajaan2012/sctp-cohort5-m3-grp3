package sg.edu.ntu.m3p3.entity.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserRequest {

    @Pattern(regexp = "^[a-zA-Z0-9_-]{5,20}$", message = "Username must be 5-20 characters long and contain only alphanumeric characters, underscore, or hyphen")
    private String userName;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$", message = "Password must contain at least one lowercase letter, one uppercase letter, one digit, and be at least 8 characters long")
    private String password;

    private String firstName;

    private String lastName;

    @Email(regexp = ".*@.*\\..*", message = "Email should be valid")
    private String email;

    private Boolean isAdmin;

    private Boolean isActive;

    private Boolean isDeleted;

    @Min(value = 0, message = "loginAttemptCounter must be greater than or equal to 0")
    @Max(value = 5, message = "loginAttemptCounter must be less than or equal to 5")
    private int loginAttemptCounter;

}
