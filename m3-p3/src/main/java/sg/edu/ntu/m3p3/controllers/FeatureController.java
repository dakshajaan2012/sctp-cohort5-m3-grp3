package sg.edu.ntu.m3p3.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import sg.edu.ntu.m3p3.entity.Feature.Feature;
import sg.edu.ntu.m3p3.service.FeatureService;
import sg.edu.ntu.m3p3.utils.ResponseUtil;
import sg.edu.ntu.m3p3.utils.ResponseWrapper;

@Tag(name = "7. Feature", description = "Feature APIs")
@RestController
@RequestMapping("/feature")
public class FeatureController {
    private final FeatureService featureService;
    private final ResponseUtil responseUtil;

    public FeatureController(FeatureService featureService, ResponseUtil responseUtil) {
        this.featureService = featureService;
        this.responseUtil = responseUtil;
    }

    @Operation(summary = "Get all features", description = "Get all features", tags = {})
    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Feature>>> getAllFeatures() {
        try {
            List<Feature> features = featureService.getAllFeatures();
            if (features.isEmpty()) {
                return responseUtil.createSuccessResponse(Collections.emptyList());
            }
            return responseUtil.createSuccessResponse(features);
        } catch (Exception e) {
            return responseUtil.createErrorResponse(e.getMessage());
        }
    }

    @Operation(summary = "Get one feature by featureId", description = "Get one feature by featureId", tags = {})
    @GetMapping("/{featureId}")
    public ResponseEntity<ResponseWrapper<Optional<Feature>>> getFeatureById(@PathVariable UUID featureId) {
        try {
            Optional<Feature> feature = featureService.getFeatureById(featureId);
            return responseUtil.createSuccessResponse(feature);
        } catch (Exception e) {
            return responseUtil.createErrorResponse(e.getMessage());
        }
    }

    @Operation(summary = "Delete one featureId", description = "Delete one featureId", tags = {})
    @DeleteMapping("/{featureId}")
    public ResponseEntity<ResponseWrapper<String>> deleteFeature(@PathVariable UUID featureId) {
        try {
            featureService.deleteFeature(featureId);
            return responseUtil
                    .createSuccessResponse("Feature with ID " + featureId + " has been deleted successfully");
        } catch (Exception e) {
            return responseUtil.createErrorResponse(e.getMessage());
        }
    }
}
