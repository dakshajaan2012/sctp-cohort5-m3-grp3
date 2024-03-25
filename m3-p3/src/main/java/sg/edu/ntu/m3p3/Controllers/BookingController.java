package sg.edu.ntu.m3p3.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import sg.edu.ntu.m3p3.entity.Session;
import sg.edu.ntu.m3p3.entity.User;
import sg.edu.ntu.m3p3.repository.SessionRepository;
import sg.edu.ntu.m3p3.repository.UserRepository;
import sg.edu.ntu.m3p3.service.SessionService;
import sg.edu.ntu.m3p3.service.UserService;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sg.edu.ntu.m3p3.entity.Booking;
import sg.edu.ntu.m3p3.entity.ParkingSlot;

import sg.edu.ntu.m3p3.repository.BookingRepository;
import sg.edu.ntu.m3p3.repository.ParkingSlotRepository;

import sg.edu.ntu.m3p3.service.BookingService;
import sg.edu.ntu.m3p3.service.ParkingSlotService;

@RestController
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

    @GetMapping
    public ResponseEntity<List<Booking>> getAllBooking() {

        List<Booking> booking = bookingService.getAllBooking();
        if (booking.isEmpty()) {
            logger.error("GET /booking - Not found");
            return ResponseEntity.notFound().build();
        }
        logger.info("GET /booking - Found {} users", booking.size());

        return ResponseEntity.ok(booking);
    }

    // http://localhost:8080/bookings?userId=1&slotId=2
    @PostMapping("/bookings")
    public String bookSlot(@RequestParam Long userId, @RequestParam Long slotId) {
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
     * booking.setParkingSlot(parkingSlot);
     * bookingRepository.save(booking);
     * 
     * return "Slot booked successfully.";
     * }
     */

}
