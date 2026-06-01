import axios from "axios";

const BASE_URL = "https://pipeline-pilot-ai.onrender.com/api/logs";

export const analyzeLog = async (file) => {
    const formData = new FormData();
    formData.append("file", file);

    const response = await axios.post(
        `${BASE_URL}/analyze`,
        formData,
        {
            headers: {
                "Content-Type": "multipart/form-data"
            }
        }
    );

    return response.data;
};