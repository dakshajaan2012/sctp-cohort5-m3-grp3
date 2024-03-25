package sg.edu.ntu.m3p3.serviceImpl;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
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
    public FeatureUsage createFeatureUsage(FeatureUsage featureUsage) {
        featureUsage.setCreatedAt(LocalDateTime.now());
        featureUsage.setUpdatedAt(LocalDateTime.now());
        return featureUsageRepository.save(featureUsage);
    }

}
