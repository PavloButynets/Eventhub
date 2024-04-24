import axios from "./axios";

export const getUserByUsername = async (username) => {
  const authAxios = axios.create({
    headers: {
      "Access-Control-Allow-Origin": "*",
      "Access-Control-Allow-Headers": "content-type",
      "Access-Control-Allow-Credentials": "true",
    },
  });
  const response = await authAxios.get(`users/${username}/profile`);
  return response.data;
};
