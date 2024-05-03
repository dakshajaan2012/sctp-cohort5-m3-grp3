package sg.edu.ntu.m3p3.entity.Feature;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Index;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Data
@Table(name = "features", indexes = { @Index(name = "idx_feature_name", columnList = "featureName"),
        @Index(name = "idx_element", columnList = "element"),
        @Index(name = "idx_action", columnList = "action") })
public class Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "feature_id")
    private UUID featureId;

    @Enumerated(EnumType.STRING)
    @Column(name = "feature_name")
    private FeatureName featureName;

    @Enumerated(EnumType.STRING)
    private Element element;

    @Enumerated(EnumType.STRING)
    private Action action;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}