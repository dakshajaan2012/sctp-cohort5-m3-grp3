package sg.edu.ntu.m3p3.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ParkingSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer slotNumber;
    @Column(nullable = false, columnDefinition = "boolean default false")
    @Builder.Default
    private Boolean isAvailable = true;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(Integer slotNumber) {
        this.slotNumber = slotNumber;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    /*
     * public Long getId() {
     * return id;
     * 
     * 
     * public void setId(Long id) {
     * this.id = id;
     * }
     * 
     * public int getSlotNumber() {
     * return slotNumber;
     * }
     * 
     * public void setSlotNumber(int slotNumber) {
     * this.slotNumber = slotNumber;
     * }
     * 
     * public boolean isAvailable() {
     * return isAvailable;
     * }
     * 
     * public void setAvailable(boolean available) {
     * isAvailable = available;
     * }
     */

    /*
     * // constructors
     * public ParkingSlot(Long id, Integer slotNumber, Boolean isAvailable) {
     * this.id = id;
     * this.slotNumber = slotNumber;
     * this.isAvailable = isAvailable;
     * }
     */

}
