package sg.edu.ntu.m3p3.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.ntu.m3p3.entity.Booking;
import sg.edu.ntu.m3p3.entity.ParkingSlot;
import sg.edu.ntu.m3p3.entity.Session;
import sg.edu.ntu.m3p3.entity.User;
import sg.edu.ntu.m3p3.repository.BookingRepository;
import sg.edu.ntu.m3p3.repository.ParkingSlotRepository;
import sg.edu.ntu.m3p3.repository.UserRepository;

@Service
public class BookingService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ParkingSlotRepository parkingSlotRepository;

    // Make booking

    public Booking bookSlot(Long userId, Long slotId) {
        // Retrieve user and parking slot from the database
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        ParkingSlot parkingSlot = parkingSlotRepository.findById(slotId)
                .orElseThrow(() -> new IllegalArgumentException("Parking slot not found with ID: " + slotId));

        // Create booking
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setParkingSlot(parkingSlot);
        // Set booking time to current date and time
        booking.setBookingTime(LocalDateTime.now());

        // Save booking to the database
        return bookingRepository.save(booking);
    }

    // Get all booking
    public List<Booking> getAllBooking() {
        return bookingRepository.findAll();
    }

}
