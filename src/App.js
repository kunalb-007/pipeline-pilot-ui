import React, { useState } from "react";

import {
    Container,
    Typography,
    Paper,
    CircularProgress
} from "@mui/material";

import FileUploader
    from "./components/FileUploader";

import AnalysisCard
    from "./components/AnalysisCard";

import IncidentReportCard
    from "./components/IncidentReportCard";

import DetectedErrorsAccordion
    from "./components/DetectedErrorsAccordion";

import {
    analyzeLog
} from "./service/apiService";

function App() {

    const [file, setFile] =
        useState(null);

    const [loading, setLoading] =
        useState(false);

    const [analysis, setAnalysis] =
        useState(null);

    const [report, setReport] =
        useState(null);

    const handleAnalyze =
        async () => {

            try {

                setLoading(true);

                const response =
                    await analyzeLog(file);

                setAnalysis(
                    response.analysis
                );

                setReport(
                    response.incidentReport
                );

            } catch (error) {

                console.error(error);

                alert(
                    "Analysis failed"
                );

            } finally {

                setLoading(false);
            }
        };

    return (

        <Container
            maxWidth="md"
            sx={{ mt: 5 }}
        >

            <Paper
                elevation={3}
                sx={{
                    p: 4
                }}
            >

                <Typography
                    variant="h4"
                    gutterBottom
                >
                    Pipeline Pilot AI
                </Typography>

                <Typography
                    variant="subtitle1"
                    mb={3}
                >
                    AI-Powered CI/CD
                    Failure Analysis
                </Typography>

                <FileUploader
                    file={file}
                    setFile={setFile}
                    onAnalyze={
                        handleAnalyze
                    }
                />

                {loading &&
                    <CircularProgress
                        sx={{ mt: 3 }}
                    />
                }

                <AnalysisCard
                    analysis={analysis}
                />

                <DetectedErrorsAccordion
                    errors={
                        analysis?.detectedErrors || []
                    }
                    categories={
                        analysis?.categories || []
                    }
                />

                <IncidentReportCard
                    report={report}
                />

            </Paper>

        </Container>
    );
}

export default App;