package sg.edu.ntu.m3p3.controllers;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import sg.edu.ntu.m3p3.entity.Booking;
import sg.edu.ntu.m3p3.entity.ParkingSlot;
import sg.edu.ntu.m3p3.entity.User.User;
import sg.edu.ntu.m3p3.repository.BookingRepository;
import sg.edu.ntu.m3p3.repository.ParkingSlotRepository;
import sg.edu.ntu.m3p3.repository.UserRepository;
import sg.edu.ntu.m3p3.service.BookingService;

@RestController
@Tag(name = "Booking", description = "Booking APIs")
public class BookingController {
    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);
    private UserRepository userRepository;
    private ParkingSlotRepository parkingSlotRepository;
    private BookingRepository bookingRepository;
    private final BookingService bookingService;

    // get all

    public BookingController(UserRepository userRepository, ParkingSlotRepository parkingSlotRepository,
            BookingService bookingService,
            BookingRepository bookingRepository) {
        this.userRepository = userRepository;
        this.parkingSlotRepository = parkingSlotRepository;
        this.bookingRepository = bookingRepository;
        this.bookingService = bookingService;
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBooking();

        if (bookings.isEmpty()) {
            logger.error("GET /bookings - Not found");
            return ResponseEntity.notFound().build();
        }

        logger.info("GET /bookings - Found {} bookings", bookings.size());
        return ResponseEntity.ok(bookings);
    }

    // http://localhost:8080/bookings?userId=1&slotId=2
    @PostMapping("/bookings")
    public String bookSlot(@RequestParam UUID userId, @RequestParam Long slotId) {
        User user = userRepository.findById(userId)
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

    // original booking
    /*
     * @PostMapping("/bookings")
     * public String bookSlot(@RequestParam Long userId, @RequestParam Long slotId)
     * {
     * User user = userRepository.findById(userId)
     * .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " +
     * userId));
     * 
     * ParkingSlot parkingSlot = parkingSlotRepository.findById(slotId)
     * .orElseThrow(() -> new
     * IllegalArgumentException("Parking slot not found with ID: " + slotId));
     * 
     * if (!parkingSlot.getIsAvailable()) {
     * return "Parking slot is not available.";
     * }
     * 
     * // Update slot availability
     * parkingSlot.setIsAvailable(false);
     * parkingSlotRepository.save(parkingSlot);
     * 
     * // Create booking record
     * Booking booking = new Booking(slotId, user, parkingSlot);
     * booking.setUser(user);
     * 
     * }
     */

}
