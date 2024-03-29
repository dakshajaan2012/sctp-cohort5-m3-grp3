package sg.edu.ntu.m3p3.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//import sg.edu.ntu.m3p3.entity.User.User;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ParkingSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Parking_id")
    private UUID id;

    @Column(name = "slot_number")
    private Integer slotNumber;

    @Column(nullable = false, columnDefinition = "boolean default false")
    @Builder.Default
    private Boolean isAvailable = true;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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

}
