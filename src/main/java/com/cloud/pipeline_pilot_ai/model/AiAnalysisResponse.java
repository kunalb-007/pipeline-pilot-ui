package com.cloud.pipeline_pilot_ai.model;

import lombok.Data;

import java.util.List;

@Data
public class AiAnalysisResponse {

    private String failureCategory;

    private String rootCause;

    private String severity;

    private String suggestedFix;

    private String preventionStrategy;

    private Integer confidence;

    private String impactedComponent;

    private List<String> likelyTechnologies;

    private List<String> failureChain;
}