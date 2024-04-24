import axios from "./axios";

export const getUserParticipants = async (eventId) => {
  const response = await axios.get(`events/${eventId}/participants/users`);
  return response.data;
};
