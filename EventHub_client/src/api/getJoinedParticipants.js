import axios from "./axios";

export const getJoinedParticipants = async (eventId) => {
  const response = await axios.get(`events/${eventId}/participants/joined`);
  return response.data;
};
