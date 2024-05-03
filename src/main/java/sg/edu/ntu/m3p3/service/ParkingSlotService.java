package sg.edu.ntu.m3p3.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import sg.edu.ntu.m3p3.entity.ParkingSlot;
import sg.edu.ntu.m3p3.repository.ParkingSlotRepository;

@Service
public class ParkingSlotService {

    @Autowired
    private ParkingSlotRepository parkingSlotRepository;

    public ParkingSlot createParkingSlot(ParkingSlot parkingSlot) {
        // Check if slot number is valid
        if (parkingSlot.getSlotNumber() == null || parkingSlot.getSlotNumber() <= 0) {
            throw new IllegalArgumentException("Slot number must be provided and greater than 0.");
        }

        // Save parking slot to the database
        return parkingSlotRepository.save(parkingSlot);
    }

    public List<ParkingSlot> getAllSlots() {
        return parkingSlotRepository.findAll();
    }

    public void deleteSlots(UUID id) {

        ParkingSlot parkingSlot = parkingSlotRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User not found"));

        parkingSlotRepository.deleteById(id);
    }
}
