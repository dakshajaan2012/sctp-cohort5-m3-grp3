package sg.edu.ntu.m3p3.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
//import jakarta.validation.constraints.NotEmpty;
//import jakarta.validation.constraints.Size;
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

    // @ManyToOne()
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = false)
    @JsonIgnoreProperties("sessions")
    private User user;

    @ManyToOne
    private Booking booking;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "user_name")
    private String userName;
    @NonNull
    @Column(name = "activity")
    private String activity;

    @Column(name = "time_start", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "time_stop", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

    @Column(name = "booking_time", columnDefinition = "TIMESTAMP")

    private LocalDateTime bookingTime;

    @PrePersist
      public void prePersist() {
      
   /*    if (bookingTime == null) {
      bookingTime = bookingTime;
      } */
      
      if (activity == null) {
     activity = "Slot Booked";
     }
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
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

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getActivity() {
        return activity;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Session(Long id, User user, String firstName, String lastName, String userName, String activity) {
        this.id = id;
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.activity = "Slot booked";

    }

}
