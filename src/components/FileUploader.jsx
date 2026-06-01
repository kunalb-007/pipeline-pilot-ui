import React, { useState } from "react";
import {
    Box,
    Button,
    Typography,
    Paper,
    Stack,
    Collapse
} from "@mui/material";

import CloudUploadIcon from "@mui/icons-material/CloudUpload";
import BugReportIcon from "@mui/icons-material/BugReport";

const SAMPLE_LOG = `Starting Jenkins Pipeline

Cloning repository...

Step 1/10 : FROM node:18

Unauthorized access to registry

ImagePullBackOff

Failed to pull image from container registry

Kubernetes deployment failed

BUILD FAILED

Pipeline terminated with errors`;

const FileUploader = ({
    file,
    setFile,
    onAnalyze
}) => {

    const [showSample, setShowSample] = useState(false);

    const handleUseSample = () => {
        const blob = new Blob([SAMPLE_LOG], { type: "text/plain" });

        const sampleFile = new File([blob], "sample-log.txt", {
            type: "text/plain"
        });

        setFile(sampleFile);
    };

    return (
        <Paper sx={{ p: 3, mt: 3, borderRadius: 3 }}>

            <Box display="flex" flexDirection="column" alignItems="center">

                <Stack spacing={2} alignItems="center">

                    {/* Upload */}
                    <Button
                        variant="outlined"
                        component="label"
                        startIcon={<CloudUploadIcon />}
                    >
                        Upload Log File

                        <input
                            hidden
                            type="file"
                            accept=".log,.txt"
                            onChange={(e) =>
                                setFile(e.target.files[0])
                            }
                        />
                    </Button>

                    {/* Sample Button */}
                    <Button
                        variant="contained"
                        color="secondary"
                        startIcon={<BugReportIcon />}
                        onClick={() => {
                            setShowSample(!showSample);
                        }}
                    >
                        Try Sample Log
                    </Button>

                </Stack>

                {/* Sample Preview */}
                <Collapse in={showSample}>
                    <Paper
                        sx={{
                            mt: 2,
                            p: 2,
                            maxHeight: 200,
                            overflow: "auto",
                            background: "#f5f5f5",
                            fontFamily: "monospace",
                            fontSize: "12px"
                        }}
                    >
                        {SAMPLE_LOG}
                    </Paper>

                    <Button
                        variant="contained"
                        sx={{ mt: 2 }}
                        onClick={handleUseSample}
                    >
                        Use This Sample
                    </Button>
                </Collapse>

                {/* Selected file */}
                {file && (
                    <Typography sx={{ mt: 2 }}>
                        📄 {file.name}
                    </Typography>
                )}

                {/* Analyze */}
                <Button
                    variant="contained"
                    size="large"
                    sx={{ mt: 3 }}
                    disabled={!file}
                    onClick={onAnalyze}
                >
                    Analyze Failure
                </Button>

            </Box>

        </Paper>
    );
};

export default FileUploader;