package com.cloud.pipeline_pilot_ai.service;

import com.cloud.pipeline_pilot_ai.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class LogAnalysisOrchestratorService {

    private final LogPreprocessingService preprocessingService;
    private final GithubAiAnalysisService aiAnalysisService;
    private final LogSummaryBuilder logSummaryBuilder;
    private final IncidentReportService incidentReportService;

    public LogAnalysisWithReportResponse  analyzeLog(MultipartFile file) {

        LogAnalysisResponse preprocessingResult =
                preprocessingService.processLog(file);

        String summary =
                logSummaryBuilder.buildSummary(preprocessingResult);

        AiAnalysisResponse aiResponse =
                aiAnalysisService.analyzeLog(summary);

        LogAnalysisResult analysis =
                LogAnalysisResult.builder()
                        .fileName(
                                preprocessingResult.getFileName())
                        .totalLines(
                                preprocessingResult.getTotalLines())
                        .errorCount(
                                preprocessingResult.getErrorCount())
                        .detectedErrors(
                                preprocessingResult.getDetectedErrors())
                        .categories(
                                preprocessingResult.getCategories())
                        .failureCategory(
                                aiResponse.getFailureCategory())
                        .rootCause(
                                aiResponse.getRootCause())
                        .severity(
                                aiResponse.getSeverity())
                        .suggestedFix(
                                aiResponse.getSuggestedFix())
                        .preventionStrategy(
                                aiResponse.getPreventionStrategy())
                        .confidence(
                                aiResponse.getConfidence())
                        .impactedComponent(
                                aiResponse.getImpactedComponent())
                        .likelyTechnologies(
                                aiResponse.getLikelyTechnologies())
                        .failureChain(
                                aiResponse.getFailureChain())
                        .build();

        IncidentReport report =
                incidentReportService.generateReport(
                        analysis);

        return LogAnalysisWithReportResponse
                .builder()
                .analysis(analysis)
                .incidentReport(report)
                .build();
    }
}