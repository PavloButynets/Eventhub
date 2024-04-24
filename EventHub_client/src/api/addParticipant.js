import axios from "./axios";

export const addParticipant = async (eventId, participantId) => {
  const accessToken = localStorage.getItem("token");
  const authAxios = axios.create({
    headers: {
      Authorization: `Bearer ${accessToken}`,
      "Access-Control-Allow-Origin": "*",
      "Access-Control-Allow-Headers": "content-type",
      "Access-Control-Allow-Credentials": "true",
    },
  });
  const response = await authAxios.post(
    `events/${eventId}/participants/add/${participantId}`
  );

  return response.data;
};
