package sg.edu.ntu.m3p3.controllers;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import sg.edu.ntu.m3p3.entity.FeatureUsage.FeatureUsage;
import sg.edu.ntu.m3p3.service.FeatureUsageService;

@Tag(name = "Feature Usage", description = "Feature Usage APIs")
@RestController
@RequestMapping("/feature-usage")
public class FeatureUsageController {
    private final FeatureUsageService featureUsageService;

    public FeatureUsageController(FeatureUsageService featureUsageService) {
        this.featureUsageService = featureUsageService;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createFeatureUsage(@RequestBody FeatureUsage featureUsage) {
        FeatureUsage createdFeatureUsage = featureUsageService.createFeatureUsage(featureUsage);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFeatureUsage);
    }

}
