package sg.edu.ntu.m3p3.repository;

import java.util.List;
//import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sg.edu.ntu.m3p3.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, UUID> {
    // Used methods for testing
    // Optional<Booking> findByUserId(UUID userId);
    // List<Booking> findByUserId(UUID userId);

    // Custom method
    @Query("SELECT b FROM Booking b WHERE b.user.userId = :userId")
    List<Booking> findByUserId(@Param("userId") UUID userId);

    List<Booking> findByUser_UserId(UUID userId);

}
