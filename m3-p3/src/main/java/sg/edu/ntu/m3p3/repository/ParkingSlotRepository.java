package sg.edu.ntu.m3p3.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.ntu.m3p3.entity.ParkingSlot;

public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long> {
}
