package sg.edu.ntu.m3p3.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "booking")

public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private ParkingSlot parkingSlot;
    @Column(name = "booking_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime bookingTime;

    /*
     * public Booking(User user, ParkingSlot parkingSlot, ParkingSlot bookingTime) {
     * 
     * this.user = user;
     * this.parkingSlot = parkingSlot;
     * this.bookingTime = bookingTime;
     * 
     * }
     */

    // Constructor accepting id, user, and parkingSlot parameters
    public Booking(Long id, User user, ParkingSlot parkingSlot) {
        this.id = id;
        this.user = user;
        this.parkingSlot = parkingSlot;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public Booking(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    public void setId(Long id) {
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
        // Auto-populate the timestamp before persisting
        LocalDateTime currentTime = LocalDateTime.now();
        // logger.info("Current time: " + currentTime);

        if (bookingTime == null) {
            bookingTime = currentTime;
            // logger.info("Setting createdAt to: " + createdAt);
        }

    }

}
