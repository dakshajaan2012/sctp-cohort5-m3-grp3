package sg.edu.ntu.m3p3.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.ntu.m3p3.entity.Feature.Element;
import sg.edu.ntu.m3p3.entity.Feature.Feature;
import sg.edu.ntu.m3p3.entity.Feature.Action;
import sg.edu.ntu.m3p3.entity.Feature.FeatureName;

import java.util.*;

public interface FeatureRepository extends JpaRepository<Feature, UUID> {
    Optional<Feature> findByFeatureNameAndElementAndAction(FeatureName featureName, Element element, Action action);
}
