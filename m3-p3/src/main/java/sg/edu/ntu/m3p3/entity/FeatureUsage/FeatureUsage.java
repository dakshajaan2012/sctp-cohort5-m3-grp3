package sg.edu.ntu.m3p3.entity.FeatureUsage;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.*;

import lombok.Data;
import sg.edu.ntu.m3p3.entity.Session;
import sg.edu.ntu.m3p3.entity.Feature.Feature;

@Entity
@Data
public class FeatureUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID featureUsageId;

    @ManyToOne
    @JoinColumn(name = "session_id", referencedColumnName = "id") // TODO: Change to sessionId
    private Session session;

    @ManyToOne
    @JoinColumn(name = "feature_id", referencedColumnName = "featureId")
    private Feature feature;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isDeleted;
}
