package sg.edu.ntu.m3p3.entity.FeatureUsage;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.*;

import lombok.Data;
import sg.edu.ntu.m3p3.entity.Address;
import sg.edu.ntu.m3p3.entity.Session;
import sg.edu.ntu.m3p3.entity.Feature.Feature;

@Entity
@Data
@Table(name = "feature_usages")
public class FeatureUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID featureUsageId;

    @ManyToOne
    @JoinColumn(name = "session_id", referencedColumnName = "session_id") // TODO: Change to sessionId
    private Session session;

    @ManyToOne
    @JoinColumn(name = "feature_id", referencedColumnName = "feature_id")
    private Feature feature;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isDeleted;
}
