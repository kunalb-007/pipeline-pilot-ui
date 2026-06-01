package com.cloud.pipeline_pilot_ai.service;

import com.cloud.pipeline_pilot_ai.model.AiAnalysisResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class GithubAiAnalysisService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${github.models.token}")
    private String githubToken;

    @Value("${ai.model}")
    private String model;

    public AiAnalysisResponse analyzeLog(String logContent) {

        String prompt = buildPrompt(logContent);

        String requestBody = """
                {
                  "messages": [
                    {
                      "role": "system",
                      "content": "You are a Senior DevOps Engineer."
                    },
                    {
                      "role": "user",
                      "content": %s
                    }
                  ],
                  "temperature": 0.2,
                  "max_tokens": 1000,
                  "model": "%s"
                }
                """.formatted(
                objectMapper.valueToTree(prompt).toString(),
                model
        );

        String response =
                WebClient.builder()
                        .baseUrl("https://models.inference.ai.azure.com")
                        .defaultHeader(
                                HttpHeaders.AUTHORIZATION,
                                "Bearer " + githubToken)
                        .defaultHeader(
                                HttpHeaders.CONTENT_TYPE,
                                MediaType.APPLICATION_JSON_VALUE)
                        .build()
                        .post()
                        .uri("/chat/completions")
                        .bodyValue(requestBody)
                        .retrieve()
                        .onStatus(
                                status -> status.isError(),
                                res -> res.bodyToMono(String.class)
                                        .map(body -> {
                                            System.out.println("GitHub Models Error:");
                                            System.out.println(body);
                                            return new RuntimeException(body);
                                        })
                        )
                        .bodyToMono(String.class)
                        .block();

        return parseResponse(response);
    }

    private String buildPrompt(String logContent) {

        return """
    You are a Principal Cloud Operations Engineer with 10+ years of experience in:

    - Microsoft Azure
    - Azure Kubernetes Service (AKS)
    - Kubernetes
    - Docker
    - Jenkins
    - Azure DevOps
    - CI/CD Pipelines
    - Cloud Infrastructure Troubleshooting
    - Incident Management
    - Production Support
    - Site Reliability Engineering (SRE)

    Your task is to perform Root Cause Analysis (RCA) for CI/CD, Kubernetes and cloud deployment failures.

    IMPORTANT RULES:

    1. Analyze ONLY the provided evidence.
    2. Do NOT invent errors that are not present.
    3. Choose the MOST SPECIFIC failure category possible.
    4. failureCategory must represent the ROOT CAUSE, not the symptom.
    5. impactedComponent must identify the primary failing system/component.
    6. likelyTechnologies must identify technologies clearly indicated by the evidence.
    7. failureChain must represent the sequence of events leading to failure.
    8. Lower confidence when evidence is limited.
    9. Higher confidence only when multiple log entries support the same conclusion.
    10. Keep responses concise and production-focused.
    11. Suggested fixes must be actionable.
    12. Prevention strategies must focus on operational excellence and reliability.
    13. NEVER leave any field empty.
    14. Always populate impactedComponent, likelyTechnologies and failureChain.

    FAILURE CATEGORY EXAMPLES:

    ImagePullBackOff + Unauthorized
    -> Registry Authentication Failure

    CrashLoopBackOff + Database Timeout
    -> Database Connectivity Failure

    BUILD FAILED + Compilation Error
    -> Compilation Failure

    BUILD FAILED + Unit Test Failure
    -> Automated Test Failure

    Connection Refused + Timeout
    -> Network Connectivity Failure

    OutOfMemoryError
    -> Memory Resource Exhaustion

    Unauthorized
    -> Authentication Failure

    AccessDenied
    -> Authorization Failure

    IMPACTED COMPONENT EXAMPLES:

    Registry Authentication Failure
    -> Container Registry

    CrashLoopBackOff
    -> Kubernetes Workload

    Compilation Failure
    -> Build System

    Network Connectivity Failure
    -> Network Infrastructure

    Database Connectivity Failure
    -> Database

    TECHNOLOGY DETECTION EXAMPLES:

    ImagePullBackOff
    -> Kubernetes

    BUILD FAILED
    -> Jenkins

    registry
    -> Docker

    AKS
    -> Azure AKS

    FAILURE CHAIN EXAMPLES:

    Example 1:

    Unauthorized access to registry
    ->
    Image pull failed
    ->
    ImagePullBackOff
    ->
    Deployment failure
    ->
    BUILD FAILED

    Example 2:

    Database timeout
    ->
    Application startup failure
    ->
    CrashLoopBackOff
    ->
    Deployment unhealthy

    SEVERITY RULES:

    HIGH:
    - Deployment blocked
    - Production impact possible
    - Security issue present

    MEDIUM:
    - Build partially affected
    - Non-critical functionality impacted

    LOW:
    - Warning only
    - No deployment impact

    CONFIDENCE GUIDELINES:

    90-100:
    Multiple strong indicators support conclusion

    70-89:
    Reasonable evidence supports conclusion

    50-69:
    Limited evidence available

    Below 50:
    Insufficient information

    Return ONLY valid JSON.

    {
      "failureCategory":"",
      "rootCause":"",
      "severity":"",
      "suggestedFix":"",
      "preventionStrategy":"",
      "confidence":0,
      "impactedComponent":"",
      "likelyTechnologies":[
        ""
      ],
      "failureChain":[
        ""
      ]
    }

    Example Output:

    {
      "failureCategory":"Registry Authentication Failure",
      "rootCause":"Unauthorized access to container registry prevented image download",
      "severity":"HIGH",
      "suggestedFix":"Update registry credentials and validate imagePullSecrets",
      "preventionStrategy":"Implement credential rotation and automated authentication validation",
      "confidence":95,
      "impactedComponent":"Container Registry",
      "likelyTechnologies":[
        "Kubernetes",
        "Docker",
        "Jenkins"
      ],
      "failureChain":[
        "Unauthorized access to registry",
        "Image pull failed",
        "ImagePullBackOff",
        "Deployment failure",
        "BUILD FAILED"
      ]
    }

    Evidence:

    %s
    """.formatted(logContent);
    }

    private AiAnalysisResponse parseResponse(
            String response) {

        try {

            JsonNode root =
                    objectMapper.readTree(response);

            String content =
                    root.path("choices")
                            .get(0)
                            .path("message")
                            .path("content")
                            .asText();

            return objectMapper.readValue(
                    content,
                    AiAnalysisResponse.class);

        } catch (Exception ex) {

            throw new RuntimeException(
                    "Failed to parse AI response",
                    ex);
        }
    }
}