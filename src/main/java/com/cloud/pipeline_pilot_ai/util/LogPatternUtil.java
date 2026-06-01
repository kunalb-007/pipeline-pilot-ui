package com.cloud.pipeline_pilot_ai.util;

import java.util.Map;

public class LogPatternUtil {

    public static final Map<String, String> ERROR_PATTERNS = Map.ofEntries(

            Map.entry("BUILD FAILED", "Build Failure"),

            Map.entry("COMPILATION ERROR", "Compilation Failure"),

            Map.entry("TEST FAILED", "Unit Test Failure"),

            Map.entry("IMAGEPULLBACKOFF", "Kubernetes Deployment Failure"),

            Map.entry("CRASHLOOPBACKOFF", "Kubernetes Runtime Failure"),

            Map.entry("OUTOFMEMORYERROR", "Memory Issue"),

            Map.entry("ACCESSDENIED", "Permission Issue"),

            Map.entry("UNAUTHORIZED", "Authentication Failure"),

            Map.entry("CONNECTION REFUSED", "Network Issue"),

            Map.entry("TIMEOUT", "Network Timeout")
    );

}