package com.cloud.pipeline_pilot_ai.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class IncidentReport {

    private String incidentId;

    private LocalDateTime timestamp;

    private String fileName;

    private List<String> categories;

    private List<String> detectedErrors;

    private String failureCategory;

    private String rootCause;

    private String severity;

    private String impactAssessment;

    private String suggestedFix;

    private String preventionStrategy;

    private Integer confidence;

    private String impactedComponent;

    private List<String> likelyTechnologies;

    private List<String> failureChain;
}
