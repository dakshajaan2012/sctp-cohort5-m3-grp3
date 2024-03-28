package sg.edu.ntu.m3p3.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import sg.edu.ntu.m3p3.entity.FeatureUsage.FeatureUsage;
import sg.edu.ntu.m3p3.entity.User.User;

@Data
@Entity
// @Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sessions")
@EqualsAndHashCode(callSuper = false)

public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "session_id")
    private UUID sessionId;
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

    // Setter for user
    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<FeatureUsage> featureUsages = new HashSet<>();

}
