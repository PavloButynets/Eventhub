import axios from "./axios";

export const getFullEventById = async (eventId) => {
  const accessToken = localStorage.getItem("token");

  const authAxios = axios.create({
    headers: {
      ...(accessToken && { Authorization: `Bearer ${accessToken}` }),
      "Access-Control-Allow-Origin": "*",
      "Access-Control-Allow-Headers": "content-type",
      "Access-Control-Allow-Credentials": "true",
    },
  });

  const response = await authAxios.get(`/users/events/${eventId}`);
  return response.data;
};
