import axios from "./axios";

export const getFullEventById = async (eventId) => {
  const response = await axios.get(`/users/events/${eventId}`);
  return response.data;
};
