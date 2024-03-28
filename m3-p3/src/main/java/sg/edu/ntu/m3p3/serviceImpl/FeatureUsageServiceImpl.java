package sg.edu.ntu.m3p3.serviceImpl;

import java.time.LocalDateTime;
import java.util.*;

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

        for (FeatureUsage featureUsage : featureUsages) {
            String featureName = featureUsage.getFeature().getFeatureName().name(); // Get enum name as string
            aggregatedData.put(featureName, aggregatedData.getOrDefault(featureName, 0) + 1);
        }

        return aggregatedData;
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

        // Calculate median overall features per session
        List<Integer> counts = new ArrayList<>(featureIdsPerSession.values());
        counts.sort(Integer::compareTo);
        double median;
        if (counts.size() % 2 == 0) {
            int mid = counts.size() / 2;
            median = (counts.get(mid - 1) + counts.get(mid)) / 2.0;
        } else {
            median = counts.get(counts.size() / 2);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("featureIdsPerSession", featureIdsPerSession);
        result.put("medianOverallFeaturesPerSession", median);
        return result;
    }

}
