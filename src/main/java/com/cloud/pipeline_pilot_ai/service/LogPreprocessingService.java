package com.cloud.pipeline_pilot_ai.service;


import com.cloud.pipeline_pilot_ai.model.LogAnalysisResponse;
import com.cloud.pipeline_pilot_ai.util.LogPatternUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

@Service
public class LogPreprocessingService {

    public LogAnalysisResponse processLog(MultipartFile file) {

        List<String> detectedErrors = new ArrayList<>();
        Set<String> categories = new HashSet<>();

        int totalLines = 0;

        try (BufferedReader reader =
                     new BufferedReader(
                             new InputStreamReader(file.getInputStream()))) {

            String line;

            while ((line = reader.readLine()) != null) {

                totalLines++;

                String upperLine = line.toUpperCase();

                String finalLine = line;
                LogPatternUtil.ERROR_PATTERNS
                        .forEach((pattern, category) -> {

                            if (upperLine.contains(pattern)) {

                                detectedErrors.add(finalLine);

                                categories.add(category);
                            }
                        });
            }

        } catch (Exception ex) {

            throw new RuntimeException(
                    "Failed to process log file",
                    ex);
        }

        return LogAnalysisResponse.builder()
                .fileName(file.getOriginalFilename())
                .totalLines(totalLines)
                .errorCount(detectedErrors.size())
                .detectedErrors(detectedErrors)
                .categories(new ArrayList<>(categories))
                .build();
    }
}