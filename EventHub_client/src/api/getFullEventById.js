import { message } from "antd";
import axios from "./axios";

export const getFullEventById = async (userId, eventId) => {
  try {
    const response = await axios.get(`/users/${userId}/events/${eventId}`);
    return response.data;
  } catch (error) {
    console.error(error);
  }
};
