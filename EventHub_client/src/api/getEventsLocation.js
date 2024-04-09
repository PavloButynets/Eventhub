import axios from "./axios";

export const getEventsData = async () => {
  const accessToken = localStorage.getItem("token");

  const authAxios = axios.create({
    headers: {
      "Access-Control-Allow-Origin": "*",
      "Access-Control-Allow-Headers": "content-type",
      "Access-Control-Allow-Credentials": "true",
    },
  });

  try {
    const response = await authAxios.get(`/users/events`);
    return response.data;
  } catch (error) {
    console.log("Error getting events data", error);
  }
};
