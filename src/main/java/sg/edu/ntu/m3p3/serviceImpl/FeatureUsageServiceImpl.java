package sg.edu.ntu.m3p3.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import sg.edu.ntu.m3p3.entity.FeatureUsage.FeatureUsage;
import sg.edu.ntu.m3p3.repository.FeatureUsageRepository;
import sg.edu.ntu.m3p3.service.FeatureUsageService;

@Service
public class FeatureUsageServiceImpl implements FeatureUsageService {

    private final FeatureUsageRepository featureUsageRepository;

    public FeatureUsageServiceImpl(FeatureUsageRepository featureUsageRepository) {
        this.featureUsageRepository = featureUsageRepository;
    }

    @Override
    public List<FeatureUsage> getAllFeatureUsages() {
        List<FeatureUsage> allFeatureUsages = featureUsageRepository.findAll();
        return (List<FeatureUsage>) allFeatureUsages;
    }

    @Override
    public FeatureUsage createFeatureUsage(FeatureUsage featureUsage) {
        featureUsage.setCreatedAt(LocalDateTime.now());
        featureUsage.setUpdatedAt(LocalDateTime.now());
        return featureUsageRepository.save(featureUsage);
    }

    @Override
    public Map<String, Integer> aggregateFeatureUsageData() {
        List<FeatureUsage> featureUsages = featureUsageRepository.findAll();

        Map<String, Integer> aggregatedData = new HashMap<>();

        // Aggregate feature usage data
        for (FeatureUsage featureUsage : featureUsages) {
            String featureName = featureUsage.getFeature().getFeatureName().name();
            // Add value retrieved from aggregatedData corresponding featureName
            aggregatedData.put(featureName, aggregatedData.getOrDefault(featureName, 0) + 1);
        }

        // Convert the map to a list of entries
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(aggregatedData.entrySet());

        // Sort the list based on the values in descending order
        entryList.sort(Map.Entry.<String, Integer>comparingByValue().reversed());

        // Convert the sorted list back to a map
        Map<String, Integer> sortedAggregatedData = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : entryList) {
            sortedAggregatedData.put(entry.getKey(), entry.getValue());
        }

        return sortedAggregatedData;
    }

    @Override
    public Map<String, Object> calculateFeatureIdsPerSession() {
        List<FeatureUsage> featureUsages = featureUsageRepository.findAll();
        Map<String, Integer> featureIdsPerSession = new HashMap<>();

        // Count the number of feature IDs per session
        for (FeatureUsage featureUsage : featureUsages) {
            String sessionId = featureUsage.getSession().getSessionId().toString();
            featureIdsPerSession.put(sessionId, featureIdsPerSession.getOrDefault(sessionId, 0) + 1);
        }

        // Calculate total overall features IDs
        int totalCount = 0;
        for (int count : featureIdsPerSession.values()) {
            totalCount += count;
        }

        Map<String, Object> result = new HashMap<>();
        result.put("featureIdsPerSession", featureIdsPerSession);
        result.put("totalOverallFeatureIds", totalCount);
        return result;
    }

}
