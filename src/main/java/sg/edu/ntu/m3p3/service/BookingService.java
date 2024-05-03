package sg.edu.ntu.m3p3.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import sg.edu.ntu.m3p3.entity.Booking;
import sg.edu.ntu.m3p3.entity.ParkingSlot;
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

    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    // Make a booking process
    public Booking bookSlot(UUID userId, UUID slotId) {
        // Retrieve user and parking slot from the database
        sg.edu.ntu.m3p3.entity.User.User user = userRepository.findById(userId)
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
    @Transactional(readOnly = true)
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // Get by id
    public Booking getBookingById(UUID bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with ID: " + bookingId));
    }

    // Define a method to update a booking
    @Transactional
    public Booking updateBooking(UUID bookingId, Booking updatedBooking) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with ID: " + bookingId));

        // Update booking details with the provided values
        booking.setUser(updatedBooking.getUser());
        booking.setParkingSlot(updatedBooking.getParkingSlot());
        booking.setBookingTime(updatedBooking.getBookingTime());

        // Save the updated booking to the database
        return bookingRepository.save(booking);
    }

}
