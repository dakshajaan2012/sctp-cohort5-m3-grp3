package sg.edu.ntu.m3p3.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;



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

    @GetMapping
    public 


        List<Booking> booking = bookingService.getAllBooking();if(booking.isEmpty())
    {
        logger.error("GET /booking - Not found");
        return ResponseEntity.notFound().build();
    }logger.info("GET /booking - Found {} users",booking.size());

    return ResponseEntity.ok(booking);
    }

    // http://localhost:8080/bookings?userId=1&slotId=2
    @PostMapping("/bookings")
    public String bookSlot(@RequestParam Long userId, @RequestParam Long slotId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IlllArgumentException("User not found with ID: " + userId));

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
