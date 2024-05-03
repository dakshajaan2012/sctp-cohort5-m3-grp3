package sg.edu.ntu.m3p3.serviceImpl;

import java.util.*;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import sg.edu.ntu.m3p3.entity.Feature.Action;
import sg.edu.ntu.m3p3.entity.Feature.Element;
import sg.edu.ntu.m3p3.entity.Feature.Feature;
import sg.edu.ntu.m3p3.entity.Feature.FeatureName;
import sg.edu.ntu.m3p3.repository.FeatureRepository;
import sg.edu.ntu.m3p3.service.FeatureService;

@Service
public class FeatureServiceImpl implements FeatureService {

    @PersistenceContext
    private EntityManager entityManager;

    private final FeatureRepository featureRepository;

    public FeatureServiceImpl(FeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
    }

    @Override
    @Transactional
    public List<Feature> insertSampleFeatures() {
        createFeatureTableIfNotExists(); // Ensure the table exists before insertion

        List<Feature> insertedFeatures = new ArrayList<>();
        try {
            for (FeatureName featureName : FeatureName.values()) {
                for (Element element : Element.values()) {
                    for (Action action : Action.values()) {
                        Feature feature = new Feature();
                        feature.setFeatureName(featureName);
                        feature.setElement(element);
                        feature.setAction(action);
                        feature.setDeleted(false);
                        insertedFeatures.add(featureRepository.save(feature));
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while inserting sample features");
        }
        return insertedFeatures;
    }

    private void createFeatureTableIfNotExists() {
        Table tableAnnotation = Feature.class.getAnnotation(Table.class);
        if (tableAnnotation != null) {
            String tableName = tableAnnotation.name();
            try {
                entityManager.createNativeQuery("SELECT 1 FROM " + tableName + " LIMIT 1").getSingleResult();
            } catch (Exception e) {
                // Table doesn't exist, so create it
                entityManager.createNativeQuery("CREATE TABLE IF NOT EXISTS " + tableName
                        + " (feature_id UUID PRIMARY KEY, feature_name VARCHAR(255), element VARCHAR(255), action VARCHAR(255), is_deleted BOOLEAN)")
                        .executeUpdate();
            }
        }
    }

    @Override
    public List<Feature> getAllFeatures() {
        return featureRepository.findAll();
    }

    @Override
    public Optional<Feature> getFeatureById(UUID featureId) {
        return featureRepository.findById(featureId);
    }

    @Override
    @Transactional
    public void deleteFeature(UUID featureId) {
        featureRepository.deleteById(featureId);
    }

}
