package sg.edu.ntu.m3p3.controllers;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import sg.edu.ntu.m3p3.entity.Feature.Feature;
import sg.edu.ntu.m3p3.entity.FeatureUsage.FeatureUsage;
import sg.edu.ntu.m3p3.entity.User.User;
import sg.edu.ntu.m3p3.service.FeatureUsageService;
import sg.edu.ntu.m3p3.utils.ResponseUtil;
import sg.edu.ntu.m3p3.utils.ResponseWrapper;

@Tag(name = "Feature Usage", description = "Feature Usage APIs")
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
        List<FeatureUsage> featureUsages = featureUsageService.getAllFeatureUsages();
        if (featureUsages.isEmpty()) {
            return responseUtil.createSuccessResponse(Collections.emptyList());
        }
        return responseUtil.createSuccessResponse(featureUsages);
    }

    @Operation(summary = "Create one feature usage", description = "Create one feature usage", tags = {})
    @PostMapping("/create")
    public ResponseEntity<Object> createFeatureUsage(@RequestBody FeatureUsage featureUsage) {
        FeatureUsage createdFeatureUsage = featureUsageService.createFeatureUsage(featureUsage);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFeatureUsage);
    }

    @Operation(summary = "Grouped by Feature Name", description = "Grouped by Feature Name", tags = {})
    @GetMapping("/aggregate")
    public ResponseEntity<ResponseWrapper<Map<String, Integer>>> aggregateFeatureUsageData() {
        Map<String, Integer> aggregatedData = featureUsageService.aggregateFeatureUsageData();
        return responseUtil.createSuccessResponse(aggregatedData);
    }

    @Operation(summary = "Features per Session and Overall Median", description = "Features per Session and Overall Mediane", tags = {})
    @GetMapping("/median")
    public ResponseEntity<ResponseWrapper<Map<String, Object>>> calculateFeatureIdsPerSession() {
        Map<String, Object> featureIdsPerSession = featureUsageService.calculateFeatureIdsPerSession();
        return responseUtil.createSuccessResponse(featureIdsPerSession);
    }

}
