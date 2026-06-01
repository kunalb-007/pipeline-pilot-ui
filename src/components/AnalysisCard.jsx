import React from "react";
import {
    Card,
    CardContent,
    Typography,
    Chip,
    Stack
} from "@mui/material";

import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import Divider from "@mui/material/Divider";
import Box from "@mui/material/Box";
import Paper from "@mui/material/Paper";


const AnalysisCard = ({ analysis }) => {

    if (!analysis) return null;

    const severity = analysis.severity?.toUpperCase();

    return (
        <Card sx={{ mt: 4 }}>

            <CardContent>

               <Box>

                   <Typography
                       variant="h5"
                       fontWeight="bold"
                   >
                       Analysis Result
                   </Typography>

                   <Typography
                       variant="h6"
                       color="primary"
                       sx={{ mt: 1 }}
                   >
                       {analysis.failureCategory}
                   </Typography>

               </Box>

                <Divider sx={{ my: 2 }} />

                <Stack
                    direction="row"
                    spacing={2}
                    sx={{
                        mt: 2,
                        mb: 3,
                        flexWrap: "wrap"
                    }}
                >

                    <Chip
                        color={
                            severity === "HIGH"
                                ? "error"
                                : severity === "MEDIUM"
                                    ? "warning"
                                    : "success"
                        }
                        label={analysis.severity}
                    />

                    <Chip
                        color="success"
                        label={
                            analysis.confidence + "%"
                        }
                    />

                    <Divider sx={{ my: 2 }} />

                </Stack>

                <Typography sx={{ mt: 2 }}>
                                    <strong>Impacted Component:</strong>
                                </Typography>

                               <Typography mb={2}>
                                   {analysis.impactedComponent || "Unknown"}
                               </Typography>

                {analysis.likelyTechnologies?.length > 0 && (
                    <>
                        <Typography>
                            <strong>Technologies:</strong>
                        </Typography>

                        <Stack
                            direction="row"
                            spacing={1}
                            sx={{ mt: 1, mb: 2 }}
                        >
                            {analysis.likelyTechnologies.map(
                                (tech, index) => (
                                    <Chip
                                        key={index}
                                        label={tech}
                                    />
                                )
                            )}
                        </Stack>
                    </>
                )}

               {analysis.failureChain?.length > 0 && (

                   <>
                       <Typography
                           variant="subtitle1"
                           fontWeight="bold"
                           sx={{ mb: 2 }}
                       >
                           Failure Chain
                       </Typography>

                       <Stack
                           spacing={1}
                           alignItems="center"
                           sx={{ mb: 3 }}
                       >

                           {analysis.failureChain.map(
                               (step, index) => (

                                   <React.Fragment
                                       key={index}
                                   >

                                       <Chip
                                           label={step}
                                           color="primary"
                                           variant="outlined"
                                           sx={{
                                               minWidth: 250
                                           }}
                                       />

                                       {index <
                                           analysis.failureChain.length - 1 && (

                                           <Typography
                                               variant="h5"
                                           >
                                               ↓
                                           </Typography>

                                       )}

                                   </React.Fragment>

                               )
                           )}

                       </Stack>

                   </>

               )}

            <Divider sx={{ my: 2 }} />

               <Paper
                   elevation={1}
                   sx={{
                       p: 2,
                       mb: 2
                   }}
               >
                   <Typography
                       fontWeight="bold"
                   >
                       Root Cause
                   </Typography>

                   <Typography>
                       {analysis.rootCause}
                   </Typography>
               </Paper>

                <Paper
                    elevation={1}
                    sx={{
                        p: 2,
                        mb: 2
                    }}
                >
                    <Typography
                        fontWeight="bold"
                    >
                        Suggested Fix
                    </Typography>

                    <Typography>
                        {analysis.suggestedFix}
                    </Typography>
                </Paper>

               <Paper
                   elevation={1}
                   sx={{
                       p: 2
                   }}
               >
                   <Typography
                       fontWeight="bold"
                   >
                       Prevention Strategy
                   </Typography>

                   <Typography>
                       {analysis.preventionStrategy}
                   </Typography>
               </Paper>

            </CardContent>

        </Card>
    );
};

export default AnalysisCard;