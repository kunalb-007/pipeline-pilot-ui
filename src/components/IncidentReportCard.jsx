import React from "react";
import { Card, CardContent, Typography, Chip, Divider } from "@mui/material";

const IncidentReportCard = ({ report }) => {
    if (!report) return null;

    return (
        <Card sx={{ mt: 4, borderRadius: 3 }}>

            <CardContent>

                <Typography variant="h5" fontWeight="bold">
                    Incident Report
                </Typography>

                <Divider sx={{ my: 2 }} />

                <Chip label={report.incidentId} color="primary" sx={{ mb: 2 }} />

                <Typography fontWeight="bold">Impact Assessment</Typography>
                <Typography sx={{ mb: 2 }}>{report.impactAssessment}</Typography>

                <Typography fontWeight="bold">Timestamp</Typography>
                <Typography>{report.timestamp}</Typography>

            </CardContent>

        </Card>
    );
};

export default IncidentReportCard;