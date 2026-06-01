import React from "react";
import { Box, Button, Typography, Paper } from "@mui/material";
import CloudUploadIcon from "@mui/icons-material/CloudUpload";

const FileUploader = ({ file, setFile, onAnalyze }) => {
    return (
        <Paper sx={{ p: 3, mt: 3, borderRadius: 3 }}>

            <Box display="flex" flexDirection="column" alignItems="center">

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
                        onChange={(e) => setFile(e.target.files[0])}
                    />
                </Button>

                {file && (
                    <Typography sx={{ mt: 2 }}>
                        📄 {file.name}
                    </Typography>
                )}

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