package sg.edu.ntu.m3p3.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "session_table")
@EqualsAndHashCode(callSuper = false)

public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @NotEmpty(message = "Name can not be blank")
    @Size(min = 3, message = "Name must be at least 3 characters long")
    // @Column(unique = true)

    @Column(name = "session_name")
    private String sessionName;
    @Column(name = "user_name")
    private String userName;

    // private String timeStart;
    @Column(name = "time_start", columnDefinition = "TIMESTAMP")
    private LocalDateTime timeStart;

    // private String timeStop;
    @Column(name = "time_stop", columnDefinition = "TIMESTAMP")
    private LocalDateTime timeStop;

    // private boolean isDeleted;
    // @Column(name = "Deleted")

    @PrePersist
    public void prePersist() {
        // Auto-populate the timestamp before persisting
        if (timeStart == null) {
            timeStart = LocalDateTime.now();
        }
        if (timeStop == null) {
            timeStop = LocalDateTime.now();
        }
    }

    // Getters and setters

    /*
     * public void setDeleted(boolean deleted) {
     * this.isDeleted = deleted;
     * }
     */
    /*
     * public boolean isDeleted() {
     * return isDeleted;
     * }
     */

    public void setName(String sessionName) {
        this.sessionName = sessionName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /*
     * public String getName() {
     * return name;
     * }
     */

    /*
     * public LocalDateTime getTimeStart() {
     * return timeStart;
     * }
     */

    public LocalDateTime getTimeStart() {
        return timeStart;
    }

    /*
     * public void setTimeStart(LocalDateTime timeStart) {
     * this.timeStart = timeStart;
     * }
     */

    public void setTimeStart(LocalDateTime timeStart) {
        this.timeStart = timeStart;
    }

    public LocalDateTime getTimeStop() {
        return timeStop;
    }

    public void setTimeStop(LocalDateTime timeStop) {
        this.timeStop = timeStop;
    }

}
