package com.cloud.pipeline_pilot_ai.service;

import com.cloud.pipeline_pilot_ai.model.LogAnalysisResponse;
import org.springframework.stereotype.Component;

@Component
public class LogSummaryBuilder {

    public String buildSummary(
            LogAnalysisResponse response) {

        return """
                Categories:
                %s

                Errors:
                %s
                """
                .formatted(
                        response.getCategories(),
                        String.join(
                                "\n",
                                response.getDetectedErrors()
                        )
                );
    }
}