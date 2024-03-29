package sg.edu.ntu.m3p3.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
//import jakarta.validation.constraints.NotEmpty;
//import jakarta.validation.constraints.Size;
import lombok.*;
import sg.edu.ntu.m3p3.entity.User.User;
//import sg.edu.ntu.m3p3.entity.Booking;
//import sg.edu.ntu.m3p3.entity.ParkingSlot;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "session_table")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id") // Seems ok
    private UUID id;

    @ManyToOne
    // @JoinColumn(name = "user_id", referencedColumnName = "id") not applicable
    @JsonIgnoreProperties("sessions")
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
    }

}