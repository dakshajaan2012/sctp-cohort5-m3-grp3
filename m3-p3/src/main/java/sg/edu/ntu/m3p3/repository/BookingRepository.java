package sg.edu.ntu.m3p3.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.ntu.m3p3.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByUserId(Long userId);
}
