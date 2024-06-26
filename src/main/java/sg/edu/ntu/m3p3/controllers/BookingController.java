package sg.edu.ntu.m3p3.controllers;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import sg.edu.ntu.m3p3.entity.Booking;
import sg.edu.ntu.m3p3.entity.ParkingSlot;
import sg.edu.ntu.m3p3.repository.BookingRepository;
import sg.edu.ntu.m3p3.repository.ParkingSlotRepository;
import sg.edu.ntu.m3p3.repository.UserRepository;
import sg.edu.ntu.m3p3.service.BookingService;

@RestController
@Tag(name = "6. Booking", description = "Booking APIs")
public class BookingController {
    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParkingSlotRepository parkingSlotRepository;

    @Autowired
    private BookingRepository bookingRepository;

    // get all

    @Autowired
    private BookingService bookingService;

    // Get all bookings-ok
    @GetMapping("/bookings")
    public ResponseEntity<List<Booking>> getAllBooking() {

        List<Booking> booking = bookingService.getAllBookings();
        if (booking.isEmpty()) {
            logger.error("GET /booking - Not found");
            return ResponseEntity.notFound().build();
        }
        logger.info("GET /booking - Found {} users", booking.size());

        return ResponseEntity.ok(booking);
    }

    // Create booking-ok
    // End point
    // http://localhost:8080/bookings?userId=1&slotId=2
    @Transactional
    @PostMapping("/bookings")
    public String bookSlot(@RequestParam UUID userId, @RequestParam UUID slotId) {
        sg.edu.ntu.m3p3.entity.User.User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        ParkingSlot parkingSlot = parkingSlotRepository.findById(slotId)
                .orElseThrow(() -> new IllegalArgumentException("Parking slot not found with ID: " + slotId));

        if (!parkingSlot.getIsAvailable()) {
            return "Parking slot is not available.";
        }

        // Update slot availability
        parkingSlot.setIsAvailable(false);
        parkingSlotRepository.save(parkingSlot);

        // Create booking record
        Booking booking = new Booking(slotId, user, parkingSlot);
        booking.setUser(user);
        booking.setParkingSlot(parkingSlot);
        bookingRepository.save(booking);
        return "Slot number " + slotId + " booked successfully.";
    }

    // Get booking by ID - ok
    @GetMapping("/bookings/{bookingId}")
    public ResponseEntity<Booking> getBookingById(@PathVariable UUID bookingId) {
        try {
            Booking booking = bookingService.getBookingById(bookingId);
            return ResponseEntity.ok().body(booking);
        } catch (EntityNotFoundException ex) {
            logger.error("Error occurred while fetching booking with ID: {}", bookingId, ex);
            return ResponseEntity.notFound().build();
        }
    }

    // Not userd due to nothing to update
    @PutMapping("/bookings/{bookingId}")
    public ResponseEntity<Booking> updateBooking(@PathVariable UUID bookingId, @RequestBody Booking updatedBooking) {
        try {
            Booking booking = bookingService.updateBooking(bookingId, updatedBooking);
            return ResponseEntity.ok().body(booking);
        } catch (EntityNotFoundException ex) {
            logger.error("Error occurred while updating booking with ID: {}", bookingId, ex);
            return ResponseEntity.notFound().build();
        }
    }

}
