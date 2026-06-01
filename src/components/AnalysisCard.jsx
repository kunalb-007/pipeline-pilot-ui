import React from "react";
import {
    Card,
    CardContent,
    Typography,
    Chip,
    Stack,
    Paper,
    Divider,
    Box
} from "@mui/material";

const AnalysisCard = ({ analysis }) => {
    if (!analysis) return null;

    const severity = analysis.severity?.toUpperCase();

    return (
        <Card sx={{ mt: 4, borderRadius: 3, boxShadow: 3 }}>

            <CardContent>

                {/* HEADER */}
                <Typography variant="h5" fontWeight="bold">
                    Analysis Result
                </Typography>

                <Typography variant="h6" color="primary" sx={{ mt: 1 }}>
                    {analysis.failureCategory}
                </Typography>

                <Stack direction="row" spacing={2} sx={{ mt: 2, mb: 3, flexWrap: "wrap" }}>
                    <Chip label={severity} color={severity === "HIGH" ? "error" : severity === "MEDIUM" ? "warning" : "success"} />
                    <Chip label={`Confidence: ${analysis.confidence}%`} color="success" />
                </Stack>

                <Divider sx={{ mb: 3 }} />

                {/* IMPACTED COMPONENT */}
                <Typography fontWeight="bold">Impacted Component</Typography>
                <Typography sx={{ mb: 2 }}>{analysis.impactedComponent || "Unknown"}</Typography>

                {/* TECHNOLOGIES */}
                {analysis.likelyTechnologies?.length > 0 && (
                    <>
                        <Typography fontWeight="bold">Technologies</Typography>
                        <Stack direction="row" spacing={1} sx={{ mb: 2 }}>
                            {analysis.likelyTechnologies.map((tech, i) => (
                                <Chip key={i} label={tech} variant="outlined" />
                            ))}
                        </Stack>
                    </>
                )}

                {/* FAILURE CHAIN */}
                {analysis.failureChain?.length > 0 && (
                    <>
                        <Typography fontWeight="bold" sx={{ mb: 2 }}>
                            Failure Chain
                        </Typography>

                        <Stack spacing={1} alignItems="center" sx={{ mb: 3 }}>
                            {analysis.failureChain.map((step, i) => (
                                <React.Fragment key={i}>
                                    <Paper sx={{ p: 1.5, minWidth: 250, textAlign: "center" }}>
                                        {step}
                                    </Paper>

                                    {i < analysis.failureChain.length - 1 && (
                                        <Typography variant="h6">↓</Typography>
                                    )}
                                </React.Fragment>
                            ))}
                        </Stack>
                    </>
                )}

                <Divider sx={{ mb: 2 }} />

                {/* ROOT CAUSE */}
                <Paper sx={{ p: 2, mb: 2 }}>
                    <Typography fontWeight="bold">Root Cause</Typography>
                    <Typography>{analysis.rootCause}</Typography>
                </Paper>

                {/* FIX */}
                <Paper sx={{ p: 2, mb: 2 }}>
                    <Typography fontWeight="bold">Suggested Fix</Typography>
                    <Typography>{analysis.suggestedFix}</Typography>
                </Paper>

                {/* PREVENTION */}
                <Paper sx={{ p: 2 }}>
                    <Typography fontWeight="bold">Prevention Strategy</Typography>
                    <Typography>{analysis.preventionStrategy}</Typography>
                </Paper>

            </CardContent>
        </Card>
    );
};

export default AnalysisCard;