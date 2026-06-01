package com.cloud.pipeline_pilot_ai.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LogAnalysisResponse {

    private String fileName;

    private int totalLines;

    private int errorCount;

    private List<String> detectedErrors;

    private List<String> categories;
}