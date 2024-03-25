package sg.edu.ntu.m3p3.entity.Feature;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Index;

import lombok.Data;
import java.util.*;

@Entity
@Data
@Table(name = "features", indexes = { @Index(name = "idx_feature_name", columnList = "featureName"),
        @Index(name = "idx_element", columnList = "element"),
        @Index(name = "idx_action", columnList = "action") })
public class Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID featureId;

    private FeatureName featureName;
    private Element element;
    private Action action;
    private boolean isDeleted;

}