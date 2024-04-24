import axios from "./axios";

export const getUserParticipants = async (eventId) => {
  try {
    const response = await axios.get(`events/${eventId}/participants/users`);
    return response.data;
  } catch (error) {
    console.error(error);
  }
};
