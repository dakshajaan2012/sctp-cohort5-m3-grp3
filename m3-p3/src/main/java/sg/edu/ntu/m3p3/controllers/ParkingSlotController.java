package sg.edu.ntu.m3p3.controllers;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import sg.edu.ntu.m3p3.entity.ParkingSlot;
import sg.edu.ntu.m3p3.repository.ParkingSlotRepository;
import sg.edu.ntu.m3p3.service.ParkingSlotService;

@RestController
@Tag(name = "Parking Slot", description = "Parking Slot APIs")
@RequestMapping("/slots")
public class ParkingSlotController {
    private static final Logger logger = LoggerFactory.getLogger(ParkingSlotController.class);

    @Autowired
    private ParkingSlotRepository parkingSlotRepository;

    // Ok
    // Endpoint - /slots
    @PostMapping
    public ResponseEntity<String> createParkingSlot(@RequestBody ParkingSlot parkingSlot) {
        if (parkingSlot.getSlotNumber() == null || parkingSlot.getSlotNumber() <= 0) {
            return ResponseEntity.badRequest().body("Slot number must be provided and greater than 0.");
        }

        // Save parking slot to the database

        ParkingSlot savedParkingSlot = parkingSlotRepository.save(parkingSlot);

        String message = "Parking slot " + savedParkingSlot.getId() + " created successfully.";

        return ResponseEntity.ok(message);
    }

    @Autowired
    private ParkingSlotService parkingSlotService;

    // Ok
    // Endpoint /slots
    @GetMapping
    public ResponseEntity<List<ParkingSlot>> getAllSlots() {
        List<ParkingSlot> parkingSlots = parkingSlotService.getAllSlots();
        if (parkingSlots.isEmpty()) {
            // Log error when no parking slots are found
            logger.error("GET /parking-slots called - No parking slots found");
            return ResponseEntity.notFound().build();
        }
        // Log info when parking slots are found
        logger.info("GET /parking-slots called - Found {} parking slots", parkingSlots.size());

        return ResponseEntity.ok(parkingSlots);
    }

    // Ok
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSlots(@PathVariable UUID id) {
        parkingSlotService.deleteSlots(id);
        logger.info("DELETE /slots/{} deleted", id);
        // return ResponseEntity.noContent().build();
        return ResponseEntity.ok("Slots with Id " + id + " has been deleted.");
    }

}
