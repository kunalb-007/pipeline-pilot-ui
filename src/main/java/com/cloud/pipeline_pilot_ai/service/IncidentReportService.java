package com.cloud.pipeline_pilot_ai.service;

import com.cloud.pipeline_pilot_ai.model.IncidentReport;
import com.cloud.pipeline_pilot_ai.model.LogAnalysisResult;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class IncidentReportService {

    private final Map<String, IncidentReport> reports =
            new ConcurrentHashMap<>();

    public IncidentReport generateReport(
            LogAnalysisResult result) {

        IncidentReport report =
                IncidentReport.builder()
                        .incidentId(generateIncidentId())
                        .timestamp(LocalDateTime.now())
                        .fileName(result.getFileName())
                        .categories(result.getCategories())
                        .detectedErrors(result.getDetectedErrors())
                        .failureCategory(result.getFailureCategory())
                        .rootCause(result.getRootCause())
                        .severity(result.getSeverity())
                        .impactAssessment(
                                buildImpactAssessment(result))
                        .suggestedFix(result.getSuggestedFix())
                        .preventionStrategy(
                                result.getPreventionStrategy())
                        .confidence(result.getConfidence())
                        .impactedComponent(
                                result.getImpactedComponent())
                        .likelyTechnologies(
                                result.getLikelyTechnologies())
                        .failureChain(
                                result.getFailureChain())
                        .build();

        reports.put(
                report.getIncidentId(),
                report);

        return report;
    }

    public IncidentReport getReport(
            String incidentId) {

        IncidentReport report =
                reports.get(incidentId);

        if (report == null) {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Incident report not found");
        }

        return report;
    }

    private String generateIncidentId() {

        return "INC-" +
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase();
    }

    private String buildImpactAssessment(
            LogAnalysisResult result) {

        if ("HIGH".equalsIgnoreCase(
                result.getSeverity())) {

            return """
                   High business impact.
                   Deployment failure may prevent application availability.
                   """;
        }

        if ("MEDIUM".equalsIgnoreCase(
                result.getSeverity())) {

            return """
                   Moderate business impact.
                   Partial service degradation possible.
                   """;
        }

        return """
               Low business impact.
               Limited operational effect.
               """;
    }
}