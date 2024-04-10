import axios from "./axios";

export const getUserParticipants = async (eventId) => {
  try {
    const accessToken = localStorage.getItem("token");

    const authAxios = axios.create({
      headers: {
        Authorization: `Bearer ${accessToken}`,
        "Access-Control-Allow-Origin": "*",
        "Access-Control-Allow-Headers": "content-type",
        "Access-Control-Allow-Credentials": "true",
      },
    });
    const response = await authAxios.get(
      `events/${eventId}/participants/users`
    );
    return response.data;
  } catch (error) {
    console.log("Error getting participants with photos data:", error);
  }
};
