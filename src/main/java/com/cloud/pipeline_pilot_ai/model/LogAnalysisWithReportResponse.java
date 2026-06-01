package com.cloud.pipeline_pilot_ai.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogAnalysisWithReportResponse {

    private LogAnalysisResult analysis;

    private IncidentReport incidentReport;
}