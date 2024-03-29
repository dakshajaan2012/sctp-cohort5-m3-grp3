package sg.edu.ntu.m3p3.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import sg.edu.ntu.m3p3.entity.FeatureUsage.FeatureUsage;
import sg.edu.ntu.m3p3.service.FeatureUsageService;
import sg.edu.ntu.m3p3.utils.ResponseUtil;
import sg.edu.ntu.m3p3.utils.ResponseWrapper;

@Tag(name = "8. Feature Usage", description = "Feature Usage APIs")
@RestController
@RequestMapping("/feature-usage")
public class FeatureUsageController {
    private final FeatureUsageService featureUsageService;
    private final ResponseUtil responseUtil;

    public FeatureUsageController(FeatureUsageService featureUsageService, ResponseUtil responseUtil) {
        this.featureUsageService = featureUsageService;
        this.responseUtil = responseUtil;
    }

    @Operation(summary = "Get all feature usages", description = "Get all feature usages", tags = {})
    @GetMapping
    public ResponseEntity<ResponseWrapper<List<FeatureUsage>>> getAllFeatureUsages() {
        try {
            List<FeatureUsage> featureUsages = featureUsageService.getAllFeatureUsages();
            if (featureUsages.isEmpty()) {
                return responseUtil.createSuccessResponse(Collections.emptyList());
            }
            return responseUtil.createSuccessResponse(featureUsages);
        } catch (Exception e) {
            return responseUtil.createErrorResponse(e.getMessage());
        }
    }

    @Operation(summary = "Create one feature usage", description = "Create one feature usage", tags = {})
    @PostMapping("/create")
    public ResponseEntity<ResponseWrapper<FeatureUsage>> createFeatureUsage(
            @RequestBody FeatureUsage featureUsage) {
        try {
            FeatureUsage createdFeatureUsage = featureUsageService.createFeatureUsage(featureUsage);
            return responseUtil.createSuccessResponse(createdFeatureUsage);
        } catch (Exception e) {
            return responseUtil.createErrorResponse(e.getMessage());
        }
    }

    @Operation(summary = "Grouped by Feature Name", description = "Grouped by Feature Name", tags = {})
    @GetMapping("/aggregate")
    public ResponseEntity<ResponseWrapper<Map<String, Integer>>> aggregateFeatureUsageData() {
        try {
            Map<String, Integer> aggregatedData = featureUsageService.aggregateFeatureUsageData();
            return responseUtil.createSuccessResponse(aggregatedData);
        } catch (Exception e) {
            return responseUtil.createErrorResponse(e.getMessage());
        }
    }

    @Operation(summary = "Feature Usages per Session and Overall Feature Usage count", description = "Feature Usages per Session and  Overall Feature Usage count", tags = {})
    @GetMapping("/total")
    public ResponseEntity<ResponseWrapper<Map<String, Object>>> calculateFeatureIdsPerSession() {
        try {
            Map<String, Object> featureIdsPerSession = featureUsageService.calculateFeatureIdsPerSession();
            return responseUtil.createSuccessResponse(featureIdsPerSession);
        } catch (Exception e) {
            return responseUtil.createErrorResponse(e.getMessage());
        }
    }

}
