package com.cloud.pipeline_pilot_ai.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LogAnalysisResult {

    private String fileName;

    private int totalLines;

    private int errorCount;

    private List<String> detectedErrors;

    private List<String> categories;

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