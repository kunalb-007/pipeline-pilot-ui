import React from "react";
import {
    Accordion,
    AccordionSummary,
    AccordionDetails,
    Typography,
    Chip,
    List,
    ListItem
} from "@mui/material";

import ExpandMoreIcon from "@mui/icons-material/ExpandMore";

const DetectedErrorsAccordion = ({ errors = [], categories = [] }) => {
    if (!errors.length) return null;

    return (
        <Accordion sx={{ mt: 3, borderRadius: 2 }}>

            <AccordionSummary expandIcon={<ExpandMoreIcon />}>
                <Typography fontWeight="bold">
                    Detected Errors
                </Typography>
            </AccordionSummary>

            <AccordionDetails>

                <Typography fontWeight="bold">Categories</Typography>

                {categories.map((cat, i) => (
                    <Chip key={i} label={cat} sx={{ mr: 1, mb: 1 }} />
                ))}

                <Typography fontWeight="bold" sx={{ mt: 2 }}>
                    Error Lines
                </Typography>

                <List>
                    {errors.map((err, i) => (
                        <ListItem key={i}>
                            {err}
                        </ListItem>
                    ))}
                </List>

            </AccordionDetails>

        </Accordion>
    );
};

export default DetectedErrorsAccordion;