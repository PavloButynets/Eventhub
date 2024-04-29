import axios from "./axios";

export const leaveEvent = async (participantId, eventId) => {
  const accessToken = localStorage.getItem("token");
  const authAxios = axios.create({
    headers: {
      Authorization: `Bearer ${accessToken}`,
      "Access-Control-Allow-Origin": "*",
      "Access-Control-Allow-Headers": "content-type",
      "Access-Control-Allow-Credentials": "true",
    },
  });
  const response = await authAxios.delete(
    `events/${eventId}/participants/${participantId}/leave`
  );
  return response.data;
};
