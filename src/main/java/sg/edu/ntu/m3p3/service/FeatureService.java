package sg.edu.ntu.m3p3.service;

import java.util.*;

import sg.edu.ntu.m3p3.entity.Feature.Feature;

public interface FeatureService {

    List<Feature> insertSampleFeatures();

    List<Feature> getAllFeatures();

    Optional<Feature> getFeatureById(UUID featureId);

    void deleteFeature(UUID featureId);
}
