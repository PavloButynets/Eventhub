import axios from "./axios";

export const getFullEventById = async (userId, eventId) => {
  const response = await axios.get(`/users/${userId}/events/${eventId}`);
  return response.data;
};
