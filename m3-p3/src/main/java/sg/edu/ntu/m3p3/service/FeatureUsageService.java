package sg.edu.ntu.m3p3.service;

import java.util.List;
import java.util.Map;

import sg.edu.ntu.m3p3.entity.FeatureUsage.FeatureUsage;

public interface FeatureUsageService {

    FeatureUsage createFeatureUsage(FeatureUsage featureUsage);

    List<FeatureUsage> getAllFeatureUsages();

    Map<String, Integer> aggregateFeatureUsageData();

    Map<String, Object> calculateFeatureIdsPerSession();

}
