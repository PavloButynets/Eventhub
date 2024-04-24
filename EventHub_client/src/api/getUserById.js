import axios from "./axios";

export const getUserById = async (userId) => {
  const response = await axios.get(`users/${userId}`);
  return response.data;
};
