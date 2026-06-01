import React from "react";
import {
    Card,
    CardContent,
    Typography
} from "@mui/material";

import Grid from "@mui/material/Grid";
import Chip from "@mui/material/Chip";
import Divider from "@mui/material/Divider";

const IncidentReportCard = ({
    report
}) => {

    if (!report) return null;

    return (
        <Card sx={{ mt: 4 }}>

           <CardContent>

               <Typography
                   variant="h5"
                   fontWeight="bold"
               >
                   Incident Report
               </Typography>

               <Divider
                   sx={{ my: 2 }}
               />

               <Grid
                   container
                   spacing={2}
               >

                   <Grid size={12}>
                       <Chip
                           color="primary"
                           label={
                               report.incidentId
                           }
                       />
                   </Grid>

                   <Grid size={12}>
                       <Typography
                           fontWeight="bold"
                       >
                           Impact Assessment
                       </Typography>

                       <Typography>
                           {report.impactAssessment}
                       </Typography>
                   </Grid>

                   <Grid size={12}>
                       <Typography
                           fontWeight="bold"
                       >
                           Generated At
                       </Typography>

                       <Typography>
                           {report.timestamp}
                       </Typography>
                   </Grid>

               </Grid>

           </CardContent>

        </Card>
    );
};

export default IncidentReportCard;