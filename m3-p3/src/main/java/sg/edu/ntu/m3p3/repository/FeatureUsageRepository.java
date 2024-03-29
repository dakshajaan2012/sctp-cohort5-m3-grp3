package sg.edu.ntu.m3p3.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.ntu.m3p3.entity.FeatureUsage.FeatureUsage;

import java.util.*;

public interface FeatureUsageRepository extends JpaRepository<FeatureUsage, UUID> {

    // void deleteBySession_SessionId(UUID sessionId);
    void deleteBySession_Id(UUID sessionId);
}
