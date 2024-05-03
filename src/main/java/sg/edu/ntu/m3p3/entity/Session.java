package sg.edu.ntu.m3p3.entity;

import java.time.LocalDateTime;
import java.util.UUID;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
//import jakarta.validation.constraints.NotEmpty;
//import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sg.edu.ntu.m3p3.entity.User.User;
//import sg.edu.ntu.m3p3.entity.Booking;
//import sg.edu.ntu.m3p3.entity.ParkingSlot;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sessions")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "session_id") // Seems ok
    private UUID sessionId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    // @JsonIgnoreProperties("sessions") // just added for testing
    private User user;

    @ManyToOne
    private Booking booking;

    /*
     * @Column(name = "first_name")
     * private String firstName;
     * 
     * @Column(name = "last_name")
     * private String lastName;
     */

    // @Column(name = "user_name")
    // private String userName;

    @Column(name = "activity", nullable = false)
    private String activity = "Slot Booked"; // static

    @Column(name = "time_start", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "time_stop", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

    @Column(name = "booking_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime bookingTime;

    @PrePersist
    public void prePersist() {
        if (activity == null) {
            activity = "Slot Booked";
        }

        // Generate session ID if not already set
        if (sessionId == null) {
            sessionId = UUID.randomUUID();
        }
    }

    // Constructor with necessary fields
    public Session(UUID sessionId, User user, Booking booking, LocalDateTime createdAt, LocalDateTime updatedAt,
            LocalDateTime bookingTime) {
        this.sessionId = sessionId;
        this.user = user;
        this.booking = booking;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.bookingTime = bookingTime;
    }

}