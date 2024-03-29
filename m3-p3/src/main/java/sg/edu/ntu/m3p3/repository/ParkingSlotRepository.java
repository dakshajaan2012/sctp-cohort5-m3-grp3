package sg.edu.ntu.m3p3.repository;

// DO NOT REMOVE ANYTHING OR COMMENT
//import java.util.List;
//import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.ntu.m3p3.entity.ParkingSlot;

public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, UUID> {

    // Method user for testing
    // Optional<Booking> findByBookingId(Long BookingId);
    // List<ParkingSlot> findByBookingId(UUID userid); was working
    // List<ParkingSlot> findByBookingId(UUID userid);

}
