import axios from "./axios";

export const getEventsData = async () => {
  const accessToken = localStorage.getItem("token");

  const authAxios = axios.create({
    headers: {
      "Access-Control-Allow-Origin": "*",
      "Access-Control-Allow-Headers": "content-type",
      "Access-Control-Allow-Credentials": "true",
      ...(accessToken && { Authorization: `Bearer ${accessToken}` }),
    },
  });

  try {
    const response = await authAxios.get(`/events/all-live-upcoming`);
    return response.data;
  } catch (error) {
    console.log("Error getting events data", error);
  }
};
