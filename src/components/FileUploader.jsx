import React from "react";
import {
    Box,
    Button,
    Typography
} from "@mui/material";

import CloudUploadIcon
from "@mui/icons-material/CloudUpload";

const FileUploader = ({
    file,
    setFile,
    onAnalyze
}) => {

    return (
        <Box>

            <Button
                variant="outlined"
                component="label"
                startIcon={
                    <CloudUploadIcon />
                }
            >
                Upload Log

                <input
                    hidden
                    type="file"
                    accept=".log,.txt"
                    onChange={(e) =>
                        setFile(
                            e.target.files[0]
                        )
                    }
                />

            </Button>

            {file && (

                <Typography
                    sx={{
                        mt: 2
                    }}
                >
                    📄 {file.name}
                </Typography>

            )}

            <Button
                variant="contained"
                size="large"
                sx={{
                    mt: 3
                }}
                disabled={!file}
                onClick={onAnalyze}
            >
                Analyze Failure
            </Button>

        </Box>
    );
};

export default FileUploader;