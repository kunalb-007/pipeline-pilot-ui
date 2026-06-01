package com.cloud.pipeline_pilot_ai.controller;

import com.cloud.pipeline_pilot_ai.model.IncidentReport;
import com.cloud.pipeline_pilot_ai.model.LogAnalysisWithReportResponse;
import com.cloud.pipeline_pilot_ai.service.IncidentReportService;
import com.cloud.pipeline_pilot_ai.service.LogAnalysisOrchestratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
@CrossOrigin("*")
public class LogAnalysisController {

    private final LogAnalysisOrchestratorService service;
    private final IncidentReportService incidentReportService;

    @PostMapping(
            value = "/analyze",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public LogAnalysisWithReportResponse analyzeLog(
            @RequestParam("file")
            MultipartFile file) {

        return service.analyzeLog(file);
    }

    @GetMapping("/reports/{incidentId}")
    public IncidentReport getReport(
            @PathVariable String incidentId) {

        return incidentReportService.getReport(
                incidentId);
    }
}