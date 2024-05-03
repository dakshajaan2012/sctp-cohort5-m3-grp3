package sg.edu.ntu.m3p3.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;

import lombok.*;
import sg.edu.ntu.m3p3.entity.User.User;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "booking_id")
    private UUID id;

    @ManyToOne
    private User user;

    @ManyToOne
    private ParkingSlot parkingSlot;

    @Column(name = "booking_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime bookingTime;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ParkingSlot getParkingSlot() {
        return parkingSlot;
    }

    public void setParkingSlot(ParkingSlot parkingSlot) {
        this.parkingSlot = parkingSlot;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    @PrePersist
    public void prePersist() {
        // Auto-populate the timestamp before processing
        if (bookingTime == null) {
            bookingTime = LocalDateTime.now();
        }
    }

    public Booking(UUID id, User user, ParkingSlot parkingSlot) {
        this.id = id;
        this.user = user;
        this.parkingSlot = parkingSlot;
        this.bookingTime = LocalDateTime.now(); // Set booking time to current time
    }

}
